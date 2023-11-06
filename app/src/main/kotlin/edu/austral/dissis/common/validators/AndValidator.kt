package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class AndValidator (private val andRules: List<Validator>): Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        for (rule in andRules){
             if (rule.validateMovement(movement,game) is InvalidMovementResult){
                return InvalidMovementResult()
            }
        }
        return ValidMovementResult()
    }

}