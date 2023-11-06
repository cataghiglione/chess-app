package edu.austral.dissis.chess.orientationValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult
import kotlin.math.abs

class HorizontalMovementMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val fromXCoordinate = movement.getFrom().xCoordinate;
        val fromYCoordinate = movement.getFrom().yCoordinate;
        val toXCoordinate = movement.getTo().xCoordinate;
        val toYCoordinate = movement.getTo().yCoordinate;
        val xDistance = abs(fromXCoordinate - toXCoordinate)
        val yDistance = abs(fromYCoordinate - toYCoordinate)
        return if (xDistance != 0 && yDistance == 0){
            ValidMovementResult()
        } else {
            InvalidMovementResult()
        }
    }
}