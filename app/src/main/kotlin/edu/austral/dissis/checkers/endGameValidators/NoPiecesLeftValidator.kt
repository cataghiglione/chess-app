package edu.austral.dissis.checkers.endGameValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.interfaces.endGameValidator

class NoPiecesLeftValidator : endGameValidator {
    override fun validateEndGame(game: Game): Boolean {
        val currentPlayer = game.getCurrentPlayer()
        val boardKeys = game.getBoard().board.keys
        for (key in boardKeys){
            if (game.getBoard().board[key]?.getColor() == currentPlayer){
                return false
            }
        }
        return true
    }
}