package edu.austral.dissis.checkers.specialMovements

import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.SpecialMovement
import edu.austral.dissis.common.interfaces.Validator

class CheckersPawnPromotion : SpecialMovement {
    override fun isSpecialMovement(movement: Movement, game: Game): Boolean {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        if (piece != null) {
            if (piece.getName() == CheckersPieceName.PAWN) {
                if (getOrientation(piece)) {
                    if (movement.getTo().yCoordinate == 8) {
                        return true
                    }
                } else {
                    if (movement.getTo().yCoordinate == 1) {
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
        val newKing = createKing(piece!!)
        val kingValidator = findKingRules(game)!!
        val newRules = game.getRules().toMutableMap()
        newRules.remove(piece)
        newRules[newKing] = kingValidator
        val newBoard = game.getBoard().replacePiece(movement, newKing)
        val newBoards = game.getMovements().toList() + game.getBoard()
        return game.copy(board = newBoard,movements=newBoards,rules = newRules, currentPlayer = game.getTurnManager().getNewTurn(game,movement))
    }

    private fun getOrientation(piece: Piece): Boolean {
        return piece.getColor() == PieceColor.WHITE
    }
    private fun findKingRules(game: Game): Validator? {
        val mapKeys = game.getRules().keys
        for (key in mapKeys) {
            if (key.getName() == CheckersPieceName.KING) {
                return game.getRules()[key]!!
            }
        }
        return null
    }

    private fun createKing(piece: Piece): Piece {
        return Piece(CheckersPieceName.KING, piece.getColor(), piece.getId())
    }
}