package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class OrValidator (private val andValidators : List<Validator>):Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        for (andValidator in andValidators){
            if (andValidator.validateMovement(movement,game)){
                return true
            }
        }
        return false
    }

}