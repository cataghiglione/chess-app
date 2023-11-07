package edu.austral.dissis.chess

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.TurnManager

class ClassicChessTurnManager:TurnManager {


    override fun returnTurnManager(): TurnManager {
        return this
    }

    override fun getNewTurn(game: Game, movement: Movement) :PieceColor{
        return game.oppositePlayer()
    }

}