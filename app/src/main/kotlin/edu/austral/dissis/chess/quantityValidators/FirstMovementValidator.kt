package edu.austral.dissis.chess.quantityValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class FirstMovementValidator: Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val boardHistory = game.getMovements()
        if(boardHistory.isNotEmpty()) {
            for (board in boardHistory) {
                if (board.getSquareContent(movement.getFrom()) != game.getBoard()
                        .getSquareContent(movement.getFrom())
                ) {
                    return InvalidMovementResult("Invalid movement")
                }
            }
        }
        return ValidMovementResult()
    }
}