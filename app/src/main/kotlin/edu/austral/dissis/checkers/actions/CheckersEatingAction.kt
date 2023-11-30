package edu.austral.dissis.checkers.actions

import edu.austral.dissis.checkers.turnManagers.CheckersTurnManager
import edu.austral.dissis.common.entities.ClassicActionResult
import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.ActionResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.ValidMovementResult

class CheckersEatingAction(private val validator: Validator) : Action {
    override fun executeAction(movement: Movement, game: Game): ActionResult {
        if (!validateAction(movement, game)) {
            return ClassicActionResult(false, game)
        }

        val from = movement.getFrom()
        val to = movement.getTo()
        val pieceToEatCoordinate =
            Coordinate((from.column + to.column) / 2, (from.row + to.row) / 2)
        val removedPieceBoard = game.getBoard().removePiece(pieceToEatCoordinate)
        val auxBoard = removedPieceBoard.move(movement)
        val newBoards = game.getMovements().toList() + game.getBoard()

        val gameAfterEating = game.copy(board = auxBoard, movements = newBoards)
        val possiblePieceId = checkIfCanEatAgain(movement, gameAfterEating)
        val newTurnManager = CheckersTurnManager(possiblePieceId)
        val newGame= game.copy(
            board = auxBoard,
            movements = newBoards,
            currentPlayer = newTurnManager.getNewTurn(game, movement),
            turnManager = newTurnManager
        )
        return ClassicActionResult(true, newGame)
    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return validator.validateMovement(movement, game) is ValidMovementResult
    }

    private fun checkIfCanEatAgain(movement: Movement, game: Game): Int {
        val to = movement.getTo()
        val newBoard = game.getBoard().copy()
        val newGame = game.copy(board = newBoard)
        val array = intArrayOf(-2, 2)
        for (i in array) {
            for (j in array) {
                val xCoordinate = to.column + i
                val yCoordinate = to.row + j
                val possibleMovement = Movement(to, Coordinate(xCoordinate, yCoordinate))
                if (newGame.validateMovement(possibleMovement) is ValidMovementResult) {
                    return game.getBoard().getSquareContent(to)?.getId() ?: -1
                }
            }
        }
        return -1
    }

}