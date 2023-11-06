package edu.austral.dissis.common.gameResults

import edu.austral.dissis.common.interfaces.GameResult

class InvalidGameResult(private val message: String): GameResult {
    fun getMessage():String{
        return message
    }

}