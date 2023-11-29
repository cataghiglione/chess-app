package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult
import kotlin.math.abs

class DiagonalMovementMoveValidator: Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val fromXCoordinate = movement.getFrom().column;
        val fromYCoordinate = movement.getFrom().row;
        val toXCoordinate = movement.getTo().column;
        val toYCoordinate = movement.getTo().row;
        val xDistance = abs(fromXCoordinate - toXCoordinate)
        val yDistance = abs(fromYCoordinate - toYCoordinate)
        return if ( xDistance == yDistance) {
            ValidMovementResult()
        } else InvalidMovementResult("Invalid movement")
    }
}