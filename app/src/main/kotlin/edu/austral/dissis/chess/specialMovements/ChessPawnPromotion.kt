package edu.austral.dissis.chess.specialMovements

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.entities.*
import edu.austral.dissis.common.interfaces.SpecialMovement
import edu.austral.dissis.common.interfaces.Validator

class ChessPawnPromotion : SpecialMovement {
    override fun isSpecialMovement(movement: Movement, game: Game): Boolean {
        val piece = game.getBoard().getSquareContent(movement.getFrom()) ?: return false
        if (piece.getName() != ChessPieceName.PAWN) return false
        val pieceOrientation = getOrientation(piece)
        return isAtTheEndOfTheBoard(pieceOrientation, movement)
    }
    private fun isAtTheEndOfTheBoard(orientation: Boolean, movement: Movement): Boolean {
        return if (orientation) {
            movement.getTo().row == 8
        } else {
            movement.getTo().row == 1
        }
    }

    override fun getNewGame(movement: Movement, game: Game): Game {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        val newQueen = createQueen(piece!!)
        val queenValidator = findQueenRules(game)!!
        val newRules = game.getRules().toMutableMap()
        newRules.remove(piece)
        newRules[newQueen] = queenValidator
        val newBoard = game.getBoard().replacePiece(movement, newQueen)
        val newBoards = game.getMovements().toList() + game.getBoard()
        return game.copy(
            board = newBoard,
            movements = newBoards,
            rules = newRules,
            currentPlayer = game.getTurnManager().getNewTurn(game, movement)
        )
    }

    private fun getOrientation(piece: Piece): Boolean {
        return piece.getColor() == PieceColor.WHITE
    }

    private fun findQueenRules(game: Game): Validator? {
        val mapKeys = game.getRules().keys
        for (key in mapKeys) {
            if (key.getName() == ChessPieceName.QUEEN) {
                return game.getRules()[key]!!
            }
        }
        return null
    }

    private fun createQueen(piece: Piece): Piece {
        val pieceColor = piece.getColor()
        val pieceName = ChessPieceName.QUEEN
        return Piece(pieceName, pieceColor, piece.getId())
    }
}