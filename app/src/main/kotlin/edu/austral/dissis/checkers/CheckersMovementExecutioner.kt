package edu.austral.dissis.checkers

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementExecutioner
import edu.austral.dissis.common.interfaces.SpecialMovement

class CheckersMovementExecutioner(private val specialMovements : List<SpecialMovement>): MovementExecutioner {

    override fun getNewGame(movement: Movement, game: Game): Game {
        var newGame  = game
        var isSpecialMovement = false
        for (specialMovement in specialMovements){
            if (specialMovement.isSpecialMovement(movement,newGame)){
                isSpecialMovement=true
                newGame = specialMovement.getNewGame(movement,newGame)
            }
        }
        if (!isSpecialMovement){
            val newBoards = game.getMovements().toList() + game.getBoard()
            return newGame.copy(board = newGame.getBoard().move(movement), movements = newBoards, currentPlayer = newGame.getTurnManager().getNewTurn(newGame,movement))
        }
        return newGame

//        return Game(game.getBoard().move(movement),newBoards,game.getValidators(),game.getRules(),game.getTurnManager().getNewTurn(game,movement),game
//            .getCheckMateValidators(),game.getMovementExecutioner(),game.getTurnManager() )
    }
}