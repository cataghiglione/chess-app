package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class HorizontalObstacleMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val from = movement.getFrom()
        val to = movement.getTo()

        if(from.row != to.row){
            return ValidMovementResult()
        }
        var currentCoordinate =
            if (from.column < to.column){
                Coordinate( from.column + 1,from.row)
            }else {
                Coordinate(from.column - 1,from.row)
            }

        while (currentCoordinate.column != to.column){
            if (game.getBoard().getSquareContent(currentCoordinate) != null){
                return InvalidMovementResult("Invalid movement")
            }

            currentCoordinate =
                if (currentCoordinate.column < to.column){
                    Coordinate( currentCoordinate.column + 1,currentCoordinate.row)
                }else {
                    Coordinate(currentCoordinate.column - 1,currentCoordinate.row)
                }
        }

        return ValidMovementResult()



//        if (from.row == to.row) {
//            val (start, end) = if (from.column > to.column) to.column + 1 to from.column else from.column + 1 to to.column
//
//            for (i in start until end) {
//                if (game.getBoard().getSquareContent(Coordinate(i, from.row)) != null) {
//                    return InvalidMovementResult("Invalid movement")
//                }
//            }
//        }
//
//        return ValidMovementResult()
    }
}
