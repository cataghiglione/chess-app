package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement

interface MovementExecutioner {
    fun getNewGame(movement: Movement, game: Game): Game
}