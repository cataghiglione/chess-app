package edu.austral.dissis.common.actions

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.ActionResult

class ReplacePieceAction (private val piece: Piece) : Action {
    override fun executeAction(movement: Movement, game: Game): ActionResult {
        TODO()
        val newBoard = game.getBoard().move(movement)
    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return true
    }
}