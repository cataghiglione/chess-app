package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement

interface Action {
    fun executeAction(movement: Movement, game: Game):ActionResult
    fun validateAction(movement: Movement, game: Game):Boolean

}