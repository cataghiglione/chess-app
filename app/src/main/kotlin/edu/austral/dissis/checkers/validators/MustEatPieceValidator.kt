package edu.austral.dissis.checkers.validators

import edu.austral.dissis.checkers.CheckersTurnManager
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class MustEatPieceValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        if (game.getTurnManager() is CheckersTurnManager){
            val turnManager = game.getTurnManager() as CheckersTurnManager
            if (turnManager.pieceId == -1){
                return ValidMovementResult()
            }
            if (turnManager.pieceId != game.getBoard().getSquareContent(movement.getFrom())?.getId()!!){
                return InvalidMovementResult("You must move the piece that has to eat")
            }
            return CanEatValidator().validateMovement(movement, game)

        }
        else return ValidMovementResult()
    }
}