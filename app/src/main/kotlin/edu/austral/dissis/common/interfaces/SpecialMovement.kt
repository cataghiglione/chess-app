package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement

interface SpecialMovement {
    fun isSpecialMovement(movement: Movement):Boolean
    fun getNewBoard(movement: Movement, game: Game):Board
}