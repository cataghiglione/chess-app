package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class OwnObstacleOnToMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        val pieceColor = game.getBoard().getSquareContent(movement.getFrom())!!.getColor()
        val toContent = game.getBoard().getSquareContent(movement.getTo())
        return !(toContent != null && toContent.getColor() == pieceColor)
    }
}