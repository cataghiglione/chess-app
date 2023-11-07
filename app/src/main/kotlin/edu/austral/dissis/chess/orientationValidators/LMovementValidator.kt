package edu.austral.dissis.chess.orientationValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult
import kotlin.math.abs

class LMovementValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val xDistance = abs(movement.getFrom().xCoordinate - movement.getTo().xCoordinate)
        val yDistance = abs(movement.getFrom().yCoordinate - movement.getTo().yCoordinate)
        return if((xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1)){
            ValidMovementResult()
        } else {
            InvalidMovementResult("Invalid movement")
        }
    }
}