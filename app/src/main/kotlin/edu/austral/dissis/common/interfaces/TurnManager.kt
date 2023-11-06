package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement

interface TurnManager {
    fun validateMovement(game: Game, movement: Movement)
    fun returnTurnManager():TurnManager
}