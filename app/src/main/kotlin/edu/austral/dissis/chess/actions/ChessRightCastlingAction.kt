package edu.austral.dissis.chess.actions

import edu.austral.dissis.common.entities.*
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.ActionResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.ValidMovementResult

class ChessRightCastlingAction(private val validator: Validator) :Action {
    override fun executeAction(movement: Movement, game: Game): ActionResult {
        if (!validateAction(movement, game)) return ClassicActionResult(false, game)

        val king = game.getBoard().getSquareContent(movement.getFrom())!!
        val rook = getRook(movement, game)
        val newRookCoordinate = Coordinate(movement.getTo().column - 1, movement.getFrom().row)

        val rookPosition = getRookPosition(rook, game)
        val rookMovement = Movement(rookPosition, newRookCoordinate)

        val newBoard = game.getBoard().replacePiece(movement, king)
            .replacePiece(rookMovement, rook)
        val newBoards = game.getMovements().toList() + game.getBoard()
        val newGame= game.copy(
            board = newBoard,
            movements = newBoards,
            currentPlayer = game.getTurnManager().getNewTurn(game, movement)
        )
        return ClassicActionResult(true, newGame)



    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return validator.validateMovement(movement,game) is ValidMovementResult
    }

    private fun getRook(
        movement: Movement,
        game: Game
    ): Piece {
        val rookColumn = movement.getTo().column + 1
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
}