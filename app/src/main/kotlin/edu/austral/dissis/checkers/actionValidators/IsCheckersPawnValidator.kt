package edu.austral.dissis.checkers.actionValidators

import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class IsCheckersPawnValidator :Validator{
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        if (piece?.getName() == CheckersPieceName.PAWN){
            return ValidMovementResult()
        }
        return InvalidMovementResult("Only pawns can be promoted")
    }
}