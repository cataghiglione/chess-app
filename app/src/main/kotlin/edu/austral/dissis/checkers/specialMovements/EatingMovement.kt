package edu.austral.dissis.checkers.specialMovements

import edu.austral.dissis.checkers.turnManagers.CheckersTurnManager
import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.SpecialMovement
import edu.austral.dissis.common.movementResults.ValidMovementResult

class EatingMovement : SpecialMovement {
    override fun isSpecialMovement(movement: Movement, game: Game): Boolean {
        val from = movement.getFrom()
        val to = movement.getTo()
        val xCoordinate = (from.column + to.column) / 2
        val yCoordinate = (from.row + to.row) / 2
        val squareContent = game.getBoard().getSquareContent(Coordinate(xCoordinate, yCoordinate))
        if (squareContent != null) {
            return squareContent.getColor() != game.getCurrentPlayer()
        }
        return false
    }

    override fun getNewGame(movement: Movement, game: Game): Game {
        val possiblePieceId = checkIfCanEatAgain(movement, game)
        val from = movement.getFrom()
        val to = movement.getTo()
        val pieceToEatCoordinate =
            Coordinate((from.column + to.column) / 2, (from.row + to.row) / 2)
        val removedPieceBoard = game.getBoard().removePiece(pieceToEatCoordinate)
        val auxBoard = removedPieceBoard.move(movement)
        val newBoards = game.getMovements().toList() + game.getBoard()
        val newTurnManager = CheckersTurnManager(possiblePieceId)
        return  game.copy(board = auxBoard, movements = newBoards, currentPlayer = newTurnManager.getNewTurn(game, movement), turnManager = newTurnManager)
//        return Game(
//            auxBoard,
//            newBoards,
//            game.getValidators(),
//            game.getRules(),
//            newTurnManager.getNewTurn(game, movement),
//            game.getCheckMateValidators(),
//            game.getMovementExecutioner(),
//            newTurnManager
//        )

    }

    private fun checkIfCanEatAgain(movement: Movement, game: Game): Int {
        val to = movement.getTo()
        val newBoard = game.getBoard().move(movement)
        val newGame = game.copy(board = newBoard)
        val array = intArrayOf(-2, 2)
        for (i in array) {
            for (j in array) {
                val xCoordinate = to.column + i
                val yCoordinate = to.row + j
                val possibleMovement = Movement(to, Coordinate(xCoordinate, yCoordinate))
                if (newGame.validateMovement(possibleMovement) is ValidMovementResult) {
                    return game.getBoard().getSquareContent(movement.getFrom())?.getId() ?: -1
                }
            }
        }
        return -1
    }



}