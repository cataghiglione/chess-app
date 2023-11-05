package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class AndValidator (private val andRules: List<Validator>): Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        for (rule in andRules){
            if (!rule.validateMovement(movement,game)){
                return false
            }
        }
        return true
    }

}