package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.Movement

interface MovementExecutioner {
    fun getNewBoard(movement: Movement): Board
}