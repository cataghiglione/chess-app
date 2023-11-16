package edu.austral.dissis.common.gameResults

import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.GameResult

class GameOverGameResult(val winner: PieceColor) : GameResult {
    fun getMessage():String{
        return "Game over"
    }
    fun getGameWinner():PieceColor{
        return winner
    }
}