package edu.austral.dissis.common.gameResults

import edu.austral.dissis.common.interfaces.GameResult

class GameOverGameResult : GameResult {
    fun getMessage():String{
        return "Game over"
    }
}