package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement

interface Validator {
    fun validateMovement(movement: Movement, game: Game): Boolean
}