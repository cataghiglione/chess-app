package edu.austral.dissis.chess

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.MovementExecutioner

class ChessMovementExecutioner(val actions: List<Action>) : MovementExecutioner {
    override fun getNewGame(movement: Movement, game: Game): Game {
        val specialMove = lookupSuitableMovement(movement, game)
        return if (specialMove != null) {
            specialMove.executeAction(movement, game)
        } else {
            normalMovementGetNewGame(game, movement)
        }

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
//        return Game(game.g etBoard().move(movement),newBoards,game.getValidators(),game.getRules(),game.getTurnManager().getNewTurn(game,movement),game
//            .getCheckMateValidators(),game.getMovementExecutioner(),game.getTurnManager() )