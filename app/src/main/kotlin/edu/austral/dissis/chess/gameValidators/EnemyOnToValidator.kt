package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class EnemyOnToValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val pieceInTo = game.getBoard().getSquareContent(movement.getTo())
        if (pieceInTo != null) {
            return if (pieceInTo.getColor() != game.getCurrentPlayer()) {
                return ValidMovementResult()
            } else InvalidMovementResult("Invalid movement")
        }
        return InvalidMovementResult("Invalid movement")
    }

}