package edu.austral.dissis.common.actions

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Action

class NormalMoveAction :Action {
    override fun executeAction(movement: Movement, game: Game): Game {
        val newBoard = game.getBoard().move(movement)
        return game.copy(board = newBoard, movements = game.getMovements().toList() + game.getBoard(),
            currentPlayer = game.getTurnManager().getNewTurn(game,movement))
    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return true
    }
}