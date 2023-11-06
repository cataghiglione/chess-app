package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class TurnValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val pieceColor = game.getBoard().getSquareContent(movement.getFrom())!!.getColor()
        return if (pieceColor == game.getCurrentPlayer()){
            ValidMovementResult()
        } else {
            InvalidMovementResult()
        }
    }
}