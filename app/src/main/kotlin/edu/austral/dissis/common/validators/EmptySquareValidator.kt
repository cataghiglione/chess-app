package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class EmptySquareValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        return game.getBoard().getSquareContent(movement.getTo()) == null
    }
}