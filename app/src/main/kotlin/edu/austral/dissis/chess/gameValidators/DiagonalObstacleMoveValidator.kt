package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult
import kotlin.math.abs

class DiagonalObstacleMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val (start, end) = if (movement.getFrom().xCoordinate > movement.getTo().xCoordinate) {
            movement.getTo().xCoordinate+ 1 to movement.getFrom().xCoordinate
        } else {
            movement.getFrom().xCoordinate + 1 to movement.getTo().xCoordinate
        }
        val (startY, endY) = if (movement.getFrom().yCoordinate > movement.getTo().yCoordinate) {
            movement.getTo().yCoordinate+ 1 to movement.getFrom().yCoordinate
        } else {
            movement.getFrom().yCoordinate+ 1 to movement.getTo().yCoordinate
        }
        for (i in start until end) {
            for (j in startY until endY) {
                if ((game.getBoard().getSquareContent(Coordinate(i,j)) != null) && abs(i - movement.getTo().xCoordinate) == abs(j - movement.getTo().yCoordinate) ) {
                    return InvalidMovementResult()
                }
            }
        }
        return ValidMovementResult()    }
}