package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class OwnObstacleOnToMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val pieceColor = game.getBoard().getSquareContent(movement.getFrom())!!.getColor()
        val toContent = game.getBoard().getSquareContent(movement.getTo()) ?: return ValidMovementResult()
        return if (isOwnObstacle(toContent, pieceColor)){
            ValidMovementResult()
        } else {
            InvalidMovementResult("Invalid movement")
        }
    }

    private fun isOwnObstacle(
        toContent: Piece?,
        pieceColor: PieceColor
    ) = !(toContent != null && toContent.getColor() == pieceColor)
}