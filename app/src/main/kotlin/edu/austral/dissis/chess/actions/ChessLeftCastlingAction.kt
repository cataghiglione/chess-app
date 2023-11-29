package edu.austral.dissis.chess.actions

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.ValidMovementResult

class ChessLeftCastlingAction (private val validator: Validator) : Action {
    override fun executeAction(movement: Movement, game: Game): Game {
        val king = game.getBoard().getSquareContent(movement.getFrom())!!
        val rook =getRook(movement, game)
        val newRookCoordinate = Coordinate(movement.getTo().column + 1, movement.getFrom().row)
        val rookPosition = getRookPosition(rook, game)

        val rookMovement = Movement(rookPosition, newRookCoordinate)
        val kingMovement = Movement(movement.getFrom(), movement.getTo())

        val newBoard = game.getBoard().replacePiece(kingMovement, king)
            .replacePiece(rookMovement, rook)

        val newBoards = game.getMovements().toList() + game.getBoard()
        return game.copy(
            board = newBoard,
            movements = newBoards,
            currentPlayer = game.getTurnManager().getNewTurn(game, movement)
        )



    }

    private fun getRook(
        movement: Movement,
        game: Game
    ): Piece {
        val rookColumn = movement.getTo().column - 2
        val rookCoordinate = Coordinate(rookColumn, movement.getFrom().row)
        val rook = game.getBoard().getSquareContent(rookCoordinate)!!
        return rook
    }
    private fun getRookPosition(
        rook: Piece,
        game: Game
    ): Coordinate {
        val invertedBoard = game.getBoard().getInvertedBoard()
        val rookCoordinate = invertedBoard.get(rook)!!
        return rookCoordinate
    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return validator.validateMovement(movement,game) is ValidMovementResult
    }
}