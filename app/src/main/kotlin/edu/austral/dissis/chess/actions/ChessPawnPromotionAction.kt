package edu.austral.dissis.chess.actions

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.ValidMovementResult
import edu.austral.dissis.utils.updateRules

class ChessPawnPromotionAction (private val validator: Validator) : Action  {
    override fun executeAction(movement: Movement, game: Game): Game {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        val queenRules = findQueenRules(game)
        val queen = createQueen(piece!!)
        val newBoard = game.getBoard().replacePiece(movement, queen)
        val newRules = updateRules(game, piece, queen,queenRules!!)
        return game.copy(board = newBoard, movements = game.getMovements().toList() + game.getBoard(), rules = newRules,
            currentPlayer = game.getTurnManager().getNewTurn(game,movement))
    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return validator.validateMovement(movement,game) is ValidMovementResult
    }
    private fun createQueen(piece: Piece): Piece {
        val pieceColor = piece.getColor()
        val pieceName = ChessPieceName.QUEEN
        return Piece(pieceName, pieceColor, piece.getId())
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