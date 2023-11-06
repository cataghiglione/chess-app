package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult


class InsideBoardMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        return if ( movement.getTo().yCoordinate <= game.getBoard().getYDimension() && movement.getTo().xCoordinate <= game.getBoard().getXDimension()) {
            ValidMovementResult()
        } else InvalidMovementResult()
    }

}
