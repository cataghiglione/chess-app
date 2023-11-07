package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class EmptySquareOnFromValidator: Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        if (game.getBoard().getSquareContent(movement.getFrom())==null){
            return InvalidMovementResult("Invalid movement")
        }
        return ValidMovementResult()
    }
}