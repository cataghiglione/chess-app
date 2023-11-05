package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class MakeAMoveMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        return movement.getFrom() != movement.getTo()
    }
}