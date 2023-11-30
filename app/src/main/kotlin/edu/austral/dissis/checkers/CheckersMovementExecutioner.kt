package edu.austral.dissis.checkers

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.ActionResult
import edu.austral.dissis.common.interfaces.MovementExecutioner
import edu.austral.dissis.common.interfaces.SpecialMovement

class CheckersMovementExecutioner(private val actions: List<Action>) : MovementExecutioner {

    override fun getNewGame(movement: Movement, game: Game): Game {
        val actionResult = lookupSuitableMovement(movement, game)
        return actionResult?.getGameResult() ?: normalMovementGetNewGame(game, movement)
//        return action?.executeAction(movement, game) ?: normalMovementGetNewGame(game, movement)

    }

    private fun lookupSuitableMovement(
        movement: Movement,
        game: Game
    ): ActionResult? {
        for ( action in actions){
            val actionResult = action.executeAction(movement, game)
            if (actionResult.wasActionPerformed()){
                return actionResult
            }
        }
        return null
    }

    private fun normalMovementGetNewGame(
        game: Game,
        movement: Movement
    ): Game {
        val newBoards = game.getMovements().toList() + game.getBoard()
        return game.copy(
            board = game.getBoard().move(movement),
            movements = newBoards,
            currentPlayer = game.getTurnManager().getNewTurn(game, movement)
        )
    }
}