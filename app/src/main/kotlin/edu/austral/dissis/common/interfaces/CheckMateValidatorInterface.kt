package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game

interface CheckMateValidatorInterface {
    fun validateCheckMate(game: Game): Boolean
}