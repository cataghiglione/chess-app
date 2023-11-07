package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class OrValidator (private val andValidators : List<Validator>):Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        for (andValidator in andValidators){
            if (andValidator.validateMovement(movement,game) is ValidMovementResult){
                return ValidMovementResult()
            }
        }
        return InvalidMovementResult("Invalid movement")
    }

}