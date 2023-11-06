package edu.austral.dissis.checkers

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.TurnManager

class CheckersTurnManager : TurnManager {
    override fun validateMovement(game: Game, movement: Movement) {
        TODO("Not yet implemented")
    }

    override fun returnTurnManager(): TurnManager {
        return this
    }
}