package edu.austral.dissis.chess.quantityValidators

import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class FirstMovementValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val boardHistory = game.getMovements()

        return if (boardHistory.isNotEmpty() && hasDifferentSquareContentInHistory(boardHistory, movement, game)) {
            InvalidMovementResult("Invalid movement")
        } else {
            ValidMovementResult()
        }
    }

    private fun hasDifferentSquareContentInHistory(boardHistory: List<Board>, movement: Movement, game: Game): Boolean {
        return boardHistory.any { it.getSquareContent(movement.getFrom()) != game.getBoard().getSquareContent(movement.getFrom()) }
    }
}
