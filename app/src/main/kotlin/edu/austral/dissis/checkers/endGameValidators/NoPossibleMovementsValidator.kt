package edu.austral.dissis.checkers.endGameValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.interfaces.endGameValidator
import edu.austral.dissis.common.movementResults.ValidMovementResult

class NoPossibleMovementsValidator: endGameValidator {
    override fun validateEndGame(game: Game): Boolean {
        val invertedBoard = game.getBoard().getInvertedBoard()
        val ownPieces = getOwnPieces(invertedBoard, game)
        for (piece in ownPieces) {
            for (i in 1 until game.getBoard().getHorizontalDimension()) {
                for (j in 1 until game.getBoard().getVerticalDimension()) {
                    val possibleMovement = Movement(invertedBoard[piece]!!, Coordinate(i, j))
                    if (game.validateMovement(possibleMovement) is ValidMovementResult) {
                        return false
                    }
                }
            }
        }
        return true
    }
    private fun getOwnPieces(invertedBoard: Map<Piece, Coordinate>, game: Game): List<Piece> {
        val ownPieces = ArrayList<Piece>()
        for (key in invertedBoard.keys) {
            if (key.getColor() == game.getCurrentPlayer()) {
                ownPieces.add(key)
            }
        }
        return ownPieces

    }
}