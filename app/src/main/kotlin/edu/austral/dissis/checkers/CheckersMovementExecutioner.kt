package edu.austral.dissis.checkers

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.MovementExecutioner
import edu.austral.dissis.common.interfaces.SpecialMovement

class CheckersMovementExecutioner(private val actions : List<Action>): MovementExecutioner {

    override fun getNewGame(movement: Movement, game: Game): Game {
        val action = lookupSuitableMovement(movement, game)
        return action?.executeAction(movement, game) ?: normalMovementGetNewGame(game, movement)

    }
    private fun lookupSuitableMovement(
        movement: Movement,
        game: Game
    ) = actions.firstOrNull { it.validateAction(movement,game) }
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