package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class OrientationValidator(orientation: Boolean) : Validator {
    private val northOrientation: Boolean = orientation

    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val isMovementValid = if (northOrientation) {
            movement.getTo().row > movement.getFrom().row
        } else {
            movement.getFrom().row > movement.getTo().row
        }

        return if (isMovementValid) {
            ValidMovementResult()
        } else {
            InvalidMovementResult("Invalid movement")
        }
    }
}
