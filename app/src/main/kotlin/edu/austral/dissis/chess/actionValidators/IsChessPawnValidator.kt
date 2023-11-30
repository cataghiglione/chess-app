package edu.austral.dissis.chess.actionValidators

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class IsChessPawnValidator:Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        if (piece?.getName() == ChessPieceName.PAWN){
            return ValidMovementResult()
        }
        return InvalidMovementResult("Only pawns can be promoted")
    }
}