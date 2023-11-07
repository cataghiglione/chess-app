package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game

interface endGameValidator {
    fun validateEndGame(game: Game): Boolean
}
