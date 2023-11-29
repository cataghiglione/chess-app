package edu.austral.dissis.chess.gameValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult
import kotlin.math.abs

class DiagonalObstacleMoveValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val from = movement.getFrom()
        val to = movement.getTo()

        if(abs(from.row - to.row) != abs(from.column - to.column)){
            return ValidMovementResult()
        }

        var currentCoordinate = from

        while (currentCoordinate.row != to.row && currentCoordinate.column != to.column){
            currentCoordinate =
                if (currentCoordinate.row < to.row && currentCoordinate.column < to.column){
                    Coordinate( currentCoordinate.column + 1,currentCoordinate.row + 1)
                }else if (currentCoordinate.row < to.row){
                    Coordinate(currentCoordinate.column - 1,currentCoordinate.row + 1)
                }else if (currentCoordinate.column < to.column){
                    Coordinate( currentCoordinate.column + 1,currentCoordinate.row - 1)
                }else {
                    Coordinate( currentCoordinate.column - 1,currentCoordinate.row - 1)
                }
            if (currentCoordinate.row != to.row && currentCoordinate.column != to.column && game.getBoard().getSquareContent(currentCoordinate) != null){
                return InvalidMovementResult("Invalid movement")
            }
        }

        return ValidMovementResult()}

//        val (start, end) = if (movement.getFrom().column > movement.getTo().column) {
//            movement.getTo().column+ 1 to movement.getFrom().column
//        } else {
//            movement.getFrom().column + 1 to movement.getTo().column
//        }
//        val (startY, endY) = if (movement.getFrom().row > movement.getTo().row) {
//            movement.getTo().row+ 1 to movement.getFrom().row
//        } else {
//            movement.getFrom().row+ 1 to movement.getTo().row
//        }
//        for (i in start until end) {
//            for (j in startY until endY) {
//                val piece = game.getBoard().getSquareContent(Coordinate(i,j))
//                val xDistance = abs(i - movement.getTo().column)
//                val yDistance = abs(j - movement.getTo().row)
//                if ((piece != null) && xDistance==yDistance ) {
//                    return InvalidMovementResult("Invalid movement")
//                }
//            }
//        }
//        return ValidMovementResult()    }
}