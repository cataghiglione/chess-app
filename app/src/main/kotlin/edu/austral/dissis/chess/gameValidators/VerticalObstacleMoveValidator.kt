package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class VerticalObstacleMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {

        val from = movement.getFrom()
        val to = movement.getTo()
        if (from.column != to.column){
            return ValidMovementResult()
        }
        var currentCoordinate = if (from.row < to.row){
            Coordinate(from.column,from.row + 1 )
        }else {
            Coordinate( from.column,from.row - 1,)
        }
        while (currentCoordinate.row != to.row){
            if (game.getBoard().getSquareContent(currentCoordinate) != null){
                return InvalidMovementResult("Invalid movement")
            }
            currentCoordinate =
                if (currentCoordinate.row < to.row){
                    Coordinate( currentCoordinate.column,currentCoordinate.row + 1)
                }else {
                    Coordinate( currentCoordinate.column,currentCoordinate.row - 1)
                }
        }
        return ValidMovementResult()
//
//        val start = if (from.row > to.row) from.row + 1 else to.row + 1
//        val end = if (from.row > to.row) to.row else from.row
//
//        for (i in start until end) {
//            if (game.getBoard().getSquareContent(Coordinate(from.column, i)) != null) {
//                return InvalidMovementResult("Invalid movement")
//            }
//        }
//
//        return ValidMovementResult()
    }


}