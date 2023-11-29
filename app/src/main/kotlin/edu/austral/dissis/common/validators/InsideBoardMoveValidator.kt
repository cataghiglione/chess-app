package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult


class InsideBoardMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        return movement.getTo().let {
            if (isWithinBoardBounds(it, game)) {
                ValidMovementResult()
            } else {
                InvalidMovementResult("Square out of bounds")
            }
        }
    }

    private fun isWithinBoardBounds(coordinate: Coordinate, game: Game): Boolean {
        return coordinate.row <= game.getBoard().getVerticalDimension() &&
                coordinate.column <= game.getBoard().getHorizontalDimension() &&
                coordinate.column >=1 &&
                coordinate.row >=1
    }
}
