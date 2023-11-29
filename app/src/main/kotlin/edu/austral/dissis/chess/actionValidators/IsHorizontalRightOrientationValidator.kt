package edu.austral.dissis.chess.actionValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class IsHorizontalRightOrientationValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val fromHorizontal = movement.getFrom().column
        val toHorizontal = movement.getTo().column

        return if (fromHorizontal < toHorizontal) {
            ValidMovementResult()
        } else {
           InvalidMovementResult("Invalid movement")
        }
    }

}