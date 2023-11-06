package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class OwnObstacleOnToMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val pieceColor = game.getBoard().getSquareContent(movement.getFrom())!!.getColor()
        val toContent = game.getBoard().getSquareContent(movement.getTo())
        return if (!(toContent != null && toContent.getColor() == pieceColor)){
            ValidMovementResult()
        } else {
            InvalidMovementResult()
        }
    }
}