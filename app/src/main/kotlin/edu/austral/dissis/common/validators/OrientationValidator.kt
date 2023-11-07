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
        if (northOrientation){
            return if (movement.getTo().yCoordinate > movement.getFrom().yCoordinate){
                ValidMovementResult()
            }
            else return InvalidMovementResult("Invalid movement")
        }
        else{
            return if (movement.getFrom().yCoordinate > movement.getTo().yCoordinate){
                ValidMovementResult()
            }
            else return InvalidMovementResult("Invalid movement")
        }
    }
}