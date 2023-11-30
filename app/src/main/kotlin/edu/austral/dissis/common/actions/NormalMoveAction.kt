package edu.austral.dissis.common.actions

import edu.austral.dissis.common.entities.ClassicActionResult
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.ActionResult

class NormalMoveAction :Action {
    override fun executeAction(movement: Movement, game: Game): ActionResult {
        val newBoard = game.getBoard().move(movement)
        val newGame= game.copy(board = newBoard, movements = game.getMovements().toList() + game.getBoard(),
            currentPlayer = game.getTurnManager().getNewTurn(game,movement))
        return ClassicActionResult(true, newGame)
    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return true
    }
}