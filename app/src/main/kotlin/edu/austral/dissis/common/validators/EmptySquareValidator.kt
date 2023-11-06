package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class EmptySquareValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        return if (game.getBoard().getSquareContent(movement.getTo()) == null) {
            ValidMovementResult()
        } else InvalidMovementResult()
    }
}