package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class EmptySquareOnToValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val possiblePiece=game.getBoard().getSquareContent(movement.getTo())
        return if (possiblePiece == null) {
            ValidMovementResult()
        } else InvalidMovementResult("Invalid movement")
    }
}