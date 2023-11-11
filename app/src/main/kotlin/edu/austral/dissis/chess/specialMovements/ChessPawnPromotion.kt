package edu.austral.dissis.chess.specialMovements

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.entities.*
import edu.austral.dissis.common.interfaces.SpecialMovement
import edu.austral.dissis.common.interfaces.Validator

class ChessPawnPromotion : SpecialMovement {
    override fun isSpecialMovement(movement: Movement, game: Game): Boolean {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        if (piece != null) {
            if (piece.getName() == ChessPieceName.PAWN) {
                if (getOrientation(piece)) {
                    if (movement.getTo().yCoordinate == 8) {
                        return true
                    }
                } else {
                    if (movement.getTo().yCoordinate == 0) {
                        return true
                    }
                }
            }

            return false
        }
        return false
    }

    override fun getNewGame(movement: Movement, game: Game): Game {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        val pieceColor = piece!!.getColor()
        val pieceName = ChessPieceName.QUEEN
        val newQueen = Piece(pieceName, pieceColor, piece.getId())
        val queenValidator = findQueenRules(game)!!
        val newRules = game.getRules().toMutableMap()
        newRules.remove(piece)
        newRules[newQueen] = queenValidator
        val newBoard = game.getBoard().replacePiece(movement, newQueen)
        val newBoards = game.getMovements().toList() + game.getBoard()
        return game.copy(board = newBoard,movements=newBoards,rules = newRules, currentPlayer = game.getTurnManager().getNewTurn(game,movement))
//        return Game(
//            Board(newBoard, game.getBoard().getXDimension(), game.getBoard().getYDimension()),
//            newBoards,
//            game.getValidators(),
//            newRules,
//            game.oppositePlayer(),
//            game.getCheckMateValidators(),
//            game.getMovementExecutioner(),game.getTurnManager()
//        )
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
}