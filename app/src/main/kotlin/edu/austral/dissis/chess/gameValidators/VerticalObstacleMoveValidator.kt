package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class VerticalObstacleMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val from = movement.getFrom()
        val to = movement.getTo()
        if (from.xCoordinate == to.xCoordinate) {
            if (from.yCoordinate > to.yCoordinate) {
                for (i in (to.yCoordinate+1 until from.yCoordinate)) {
                    if (game.getBoard().getSquareContent(Coordinate(from.xCoordinate, i)) != null) {
                        return InvalidMovementResult()
                    }
                }
            } else {
                for (i in (from.yCoordinate+1 until to.yCoordinate)) {
                    if (game.getBoard().getSquareContent(Coordinate(from.xCoordinate, i)) != null) {
                        return InvalidMovementResult()
                    }
                }
            }
        }
        return ValidMovementResult()
    }

}