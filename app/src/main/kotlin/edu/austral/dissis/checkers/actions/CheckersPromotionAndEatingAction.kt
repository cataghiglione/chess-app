package edu.austral.dissis.checkers.actions

import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.checkers.turnManagers.CheckersTurnManager
import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.ValidMovementResult
import edu.austral.dissis.utils.updateRules

class CheckersPromotionAndEatingAction (private val validator: Validator):Action {
    override fun executeAction(movement: Movement, game: Game): Game {
        val to = movement.getTo()
        val from = movement.getFrom()
        val newBoards = game.getMovements().toList() + game.getBoard()

        //eating process
        val pieceToEatCoordinate = Coordinate((from.column + to.column) / 2, (from.row + to.row) / 2)
        val removedPieceBoard = game.getBoard().removePiece(pieceToEatCoordinate)


        //promotion process
        val pawn = game.getBoard().getSquareContent(from)
        val newKing = createKing(pawn!!)
        val finalPromotionBoard = removedPieceBoard.replacePiece(movement, newKing)
        val kingValidator = findKingRules(game)!!
        val newRules = updateRules(game, pawn, newKing, kingValidator)

        //check if the king can eat again, I have to put these lines after the promotion process because
        //now the piece is a king and not a pawn
        val gameAfterEatingAndPromotion = game.copy(board = finalPromotionBoard, movements = newBoards, rules = newRules)
        val possiblePieceId = checkIfCanEatAgainAfterPromotion(movement, gameAfterEatingAndPromotion)
        val newTurnManager = CheckersTurnManager(possiblePieceId)

        return game.copy(board = finalPromotionBoard, movements = newBoards, rules = newRules,
            currentPlayer = newTurnManager.getNewTurn(game, movement), turnManager = newTurnManager)





    }

    override fun validateAction(movement: Movement, game: Game): Boolean {
        return validator.validateMovement(movement, game) is ValidMovementResult
    }
    private fun createKing(piece: Piece): Piece {
        val pieceColor = piece.getColor()
        val pieceName = CheckersPieceName.KING
        return Piece(pieceName, pieceColor, piece.getId())
    }
    private fun findKingRules(game: Game): Validator? {
        val mapKeys = game.getRules().keys
        for (key in mapKeys) {
            if (key.getName() == CheckersPieceName.KING) {
                return game.getRules()[key]!!
            }
        }
        return null
    }
    private fun checkIfCanEatAgainAfterPromotion(movement: Movement, game: Game): Int {
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
                    return game.getBoard().getSquareContent(movement.getFrom())?.getId() ?: -1
                }
            }
        }
        return -1
    }
}