package edu.austral.dissis.checkers

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.TurnManager

class CheckersTurnManager (val pieceId: Int): TurnManager {


    override fun returnTurnManager(): TurnManager {
        return this
    }

    override fun getNewTurn(game: Game, movement: Movement): PieceColor {
        if (pieceId!=-1){
            return game.getCurrentPlayer()
        }
        return game.oppositePlayer()
    }
}