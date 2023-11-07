package gameValidators.checkValidators

import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.chess.results.CheckMovementResult
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult

class CheckValidator : Validator {

    fun getKingPosition(movement: Movement, game: Game): Coordinate? {
        val newBoard = game.getBoard().move(movement)
        val invertedBoard = newBoard.getInvertedBoard()
        val invertedBoardKeys = newBoard.getInvertedBoard().keys
        for (key in invertedBoardKeys) {
            if (key.getName() == ChessPieceName.KING && key.getColor() == game.getCurrentPlayer()) {
                return invertedBoard[key]
            }
        }
        return null
    }

    override fun validateMovement(movement: Movement, game: Game): MovementResult {
        val newBoard = game.getBoard().move(movement)
        val invertedBoard = newBoard.getInvertedBoard()
        val kingPosition = getKingPosition(movement, game) ?: return InvalidMovementResult("Invalid movement")
        val enemyPieces = getEnemyPieces(invertedBoard, game)
        val newMoves = newMoves(game, newBoard)
        val newGameState = Game(
            newBoard,
            newMoves,
            removeCheckValidators(game.getValidators()),
            game.getRules(),
            game.oppositePlayer(),
            game.getCheckMateValidators(),
            game.getMovementExecutioner(),
            game.getTurnManager()
        )
        for (piece in enemyPieces) {
            if (newGameState.validateMovement(Movement(invertedBoard[piece]!!, kingPosition)) is ValidMovementResult) {
                return InvalidMovementResult("You are in check")
            }
        }
        return ValidMovementResult()
    }


}

private fun newMoves(
    game: Game, newBoard: Board
): ArrayList<Board> {
    val auxMoves = ArrayList<Board>()
    auxMoves.addAll(game.getMovements())
    auxMoves.add(newBoard)
    val newMoves = ArrayList<Board>()
    newMoves.addAll(auxMoves)
    return newMoves
}

private fun getEnemyPieces(
    invertedBoard: Map<Piece, Coordinate>, game: Game
): List<Piece> {
    val enemyPieces = invertedBoard.keys.filter { it.getColor() != game.getCurrentPlayer() }
    return enemyPieces
}

private fun removeCheckValidators(validators: List<Validator>): List<Validator> {
    return validators.filter { it !is CheckValidator }
}

