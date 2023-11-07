package edu.austral.dissis.checkers.validators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class CanEatValidator: Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val from = movement.getFrom()
        val to = movement.getTo()
        val xCoordinate = (from.xCoordinate + to.xCoordinate)/2
        val yCoordinate = (from.yCoordinate + to.yCoordinate)/2
        val squareContent = game.getBoard().getSquareContent(Coordinate(xCoordinate,yCoordinate))
        if (squareContent!=null ){
            return if (squareContent.getColor() == game.getCurrentPlayer()){
                InvalidMovementResult("You must eat the opposite team's pieces")
            } else ValidMovementResult()
        }
        return InvalidMovementResult("Cannot eat an empty square")
    }
}