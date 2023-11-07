package edu.austral.dissis.checkers

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementExecutioner
import edu.austral.dissis.common.interfaces.SpecialMovement

class CheckersMovementExecutioner(val specialMovements : List<SpecialMovement>): MovementExecutioner {
    override fun getNewGame(movement: Movement, game: Game): Game {
        for (specialMovement in specialMovements){
            if (specialMovement.isSpecialMovement(movement,game)){
                return specialMovement.getNewGame(movement,game)
            }
        }
        val newBoards = game.getMovements().toList() + game.getBoard()
        return Game(game.getBoard().move(movement),newBoards,game.getValidators(),game.getRules(),game.getTurnManager().getNewTurn(game,movement),game
            .getCheckMateValidators(),game.getMovementExecutioner(),game.getTurnManager() )

    }
}