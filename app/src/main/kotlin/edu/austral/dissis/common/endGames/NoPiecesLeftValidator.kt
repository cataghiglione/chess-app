package edu.austral.dissis.common.endGames

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.interfaces.endGameValidator

class NoPiecesLeftValidator : endGameValidator {
    override fun validateEndGame(game: Game): Boolean {
        val currentPlayer = game.getCurrentPlayer()
        val boardKeys = game.getBoard().getInvertedBoard().keys
        val piecesList=boardKeys.filter { it.getColor() == currentPlayer}
        if (piecesList.isEmpty()) {
            return true
        }
        else return false
    }
}