package edu.austral.dissis.common.gameResults

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.interfaces.GameResult

class ValidGameResult(private val game: Game) : GameResult {
    fun getGame(): Game{
        return game;
    }

}