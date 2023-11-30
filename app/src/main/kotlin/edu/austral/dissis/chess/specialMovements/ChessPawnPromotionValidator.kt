package edu.austral.dissis.chess.specialMovements

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class ChessPawnPromotionValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val piece = game.getBoard().getSquareContent(movement.getFrom()) ?: return InvalidMovementResult("No piece found in origin square")
        if (piece.getName() != ChessPieceName.PAWN) return InvalidMovementResult("Piece is not a pawn")
        val pieceOrientation = getOrientation(piece)
        return if (isAtTheEndOfTheBoard(pieceOrientation, movement)){
            ValidMovementResult()
        } else InvalidMovementResult("Pawn is not at the end of the board")

    }
    private fun getOrientation(piece: Piece): Boolean {
        return piece.getColor() == PieceColor.WHITE
    }
    private fun isAtTheEndOfTheBoard(orientation: Boolean, movement: Movement): Boolean {
        return if (orientation) {
            movement.getTo().row == 8
        } else {
            movement.getTo().row == 1
        }
    }
}