package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.PieceColor

interface TurnManager {
    fun returnTurnManager():TurnManager
    fun getNewTurn(game:Game, movement: Movement):PieceColor
}