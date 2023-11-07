package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class MakeAMoveMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        return if (movement.getFrom() != movement.getTo()) {
            ValidMovementResult()
        } else InvalidMovementResult("You must select two different board squares to make a move")

    }
}