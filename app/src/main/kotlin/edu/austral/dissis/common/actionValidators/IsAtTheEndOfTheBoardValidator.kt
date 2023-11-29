package edu.austral.dissis.common.actionValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class IsAtTheEndOfTheBoardValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val piece = game.getBoard().getSquareContent(movement.getFrom())
        val pieceOrientation = getOrientation(piece!!)
        if (isAtTheEndOfTheBoard(pieceOrientation, movement)){
            return ValidMovementResult()
        }
        return InvalidMovementResult("Piece is not at the end of the board")

    }
    private fun isAtTheEndOfTheBoard(orientation: Boolean, movement: Movement): Boolean {
        return if (orientation) {
            movement.getTo().row == 8
        } else {
            movement.getTo().row == 1
        }
    }
    private fun getOrientation(piece: Piece): Boolean {
        return piece.getColor() == PieceColor.WHITE
    }
}