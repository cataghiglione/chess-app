package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class EnemyOnToValidator: Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        val pieceInTo = game.getBoard().getSquareContent(movement.getTo())
        return if(pieceInTo != null) {
            pieceInTo.getColor() != game.getCurrentPlayer()
        } else false
    }

}