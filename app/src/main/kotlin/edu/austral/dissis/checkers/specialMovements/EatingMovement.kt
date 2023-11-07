package edu.austral.dissis.checkers.specialMovements

import edu.austral.dissis.checkers.CheckersTurnManager
import edu.austral.dissis.checkers.validators.CanEatValidator
import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.SpecialMovement
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult
import edu.austral.dissis.common.validators.EmptySquareOnToValidator
import edu.austral.dissis.common.validators.InsideBoardMoveValidator

class EatingMovement : SpecialMovement {
    override fun isSpecialMovement(movement: Movement, game: Game): Boolean {
        val from = movement.getFrom()
        val to = movement.getTo()
        val xCoordinate = (from.xCoordinate + to.xCoordinate) / 2
        val yCoordinate = (from.yCoordinate + to.yCoordinate) / 2
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
            Coordinate((from.xCoordinate + to.xCoordinate) / 2, (from.yCoordinate + to.yCoordinate) / 2)
        val auxBoard = game.getBoard().board.toMutableMap()
        auxBoard.remove(pieceToEatCoordinate)
        val piece = game.getBoard().getSquareContent(from)
        auxBoard.remove(from)
        auxBoard[to] = piece!!
        val newBoard = Board(auxBoard, game.getBoard().getXDimension(), game.getBoard().getYDimension())
        val newBoards = game.getMovements().toList() + game.getBoard()
        val newTurnManager = CheckersTurnManager(possiblePieceId)
        if (possiblePieceId != -1) {
            return Game(
                newBoard,
                newBoards,
                game.getValidators(),
                game.getRules(),
                newTurnManager.getNewTurn(game, movement),
                game.getCheckMateValidators(),
                game.getMovementExecutioner(),
                newTurnManager
            )
        } else return Game(
            newBoard,
            newBoards,
            game.getValidators(),
            game.getRules(),
            newTurnManager.getNewTurn(game, movement),
            game.getCheckMateValidators(),
            game.getMovementExecutioner(),
            newTurnManager
        )

    }

    private fun checkIfCanEatAgain(movement: Movement, game: Game): Int {
        val to = movement.getTo()
        val newBoard = game.getBoard().move(movement)
        val newGame = Game(newBoard, game.getMovements(), game.getValidators(), game.getRules(), game.getCurrentPlayer(), game.getCheckMateValidators(), game.getMovementExecutioner(), game.getTurnManager())
        val array = intArrayOf(-2, 2)
        for (i in array) {
            for (j in array) {
                val xCoordinate = to.xCoordinate + i
                val yCoordinate = to.yCoordinate + j
                val possibleMovement = Movement(to, Coordinate(xCoordinate, yCoordinate))
                if (newGame.validateMovement(possibleMovement) is ValidMovementResult) {
                    return game.getBoard().getSquareContent(movement.getFrom())?.getId() ?: -1
                }
            }
        }
        return -1
    }

    private fun validatePossibleMovement(movement: Movement, game: Game): MovementResult {
        if (InsideBoardMoveValidator().validateMovement(movement, game) is ValidMovementResult) {
            if (CanEatValidator().validateMovement(movement, game) is ValidMovementResult) {
                return if (EmptySquareOnToValidator().validateMovement(movement, game) is ValidMovementResult) {
                    ValidMovementResult()
                } else InvalidMovementResult("You must eat a piece")
            }
            return InvalidMovementResult("You must eat a piece")
        }
        else return InvalidMovementResult("You must eat a piece")

    }

}