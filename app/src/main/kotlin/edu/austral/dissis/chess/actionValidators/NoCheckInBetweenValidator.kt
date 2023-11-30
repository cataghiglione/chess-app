package edu.austral.dissis.chess.actionValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator

class NoCheckInBetweenValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        TODO("Not yet implemented")
    }

}