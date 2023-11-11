package edu.austral.dissis.chess

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementExecutioner
import edu.austral.dissis.common.interfaces.SpecialMovement

class ChessMovementExecutioner(val specialMovements: List<SpecialMovement>) : MovementExecutioner {
    override fun getNewGame(movement: Movement, game: Game): Game {
        for (specialMovement in specialMovements){
            if (specialMovement.isSpecialMovement(movement,game)){
                return specialMovement.getNewGame(movement,game)
            }
        }
        val newBoards = game.getMovements().toList() + game.getBoard()
        return game.copy(board = game.getBoard().move(movement),movements = newBoards, currentPlayer = game.getTurnManager().getNewTurn(game,movement))
//        return Game(game.g etBoard().move(movement),newBoards,game.getValidators(),game.getRules(),game.getTurnManager().getNewTurn(game,movement),game
//            .getCheckMateValidators(),game.getMovementExecutioner(),game.getTurnManager() )
    }
}