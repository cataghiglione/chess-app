package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator

class HorizontalObstacleMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        val from = movement.getFrom()
        val to = movement.getTo()
        if (from.yCoordinate == to.yCoordinate) {
            if (from.xCoordinate > to.xCoordinate) {
                for (i in (to.xCoordinate+1 until from.xCoordinate)) {
                    if (game.getBoard().getSquareContent(Coordinate(i, from.yCoordinate)) != null) {
                        return false
                    }
                }
            } else {
                for (i in (from.xCoordinate+1 until to.xCoordinate)) {
                    if (game.getBoard().getSquareContent(Coordinate(i, from.yCoordinate)) != null) {
                        return false
                    }
                }
            }
        }
        return true
    }

}