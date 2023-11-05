package edu.austral.dissis.chess.quantityValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class FirstMovementValidator: Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        val boardHistory = game.getMovements()
        if(boardHistory.isNotEmpty()) {
            for (board in boardHistory) {
                if (board.getSquareContent(movement.getFrom()) != game.getBoard()
                        .getSquareContent(movement.getFrom())
                ) {
                    return false
                }
            }
        }
        return true
    }
}