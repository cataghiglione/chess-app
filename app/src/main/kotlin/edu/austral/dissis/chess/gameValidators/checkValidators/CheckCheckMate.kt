package gameValidators.checkValidators

import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.interfaces.CheckMateValidatorInterface
import edu.austral.dissis.common.movementResults.ValidMovementResult


class CheckCheckMate : CheckMateValidatorInterface {
    override fun validateCheckMate(game: Game): Boolean {
        //el game que me pasan es con el nuevo move, o sea chequeo si el currentPlayer esta en jaque mate
        //o sea si primero movieron las blancas, yo ahora chequeo si las negras pueden moverse o si estan en jaque mate
        val invertedBoard = game.getBoard().getInvertedBoard();
        val invertedBoardKeys = invertedBoard.keys
        for (key in invertedBoardKeys) {
            if (key.getColor() == game.getCurrentPlayer() && key.getName() == ChessPieceName.KING) {
                val ownPieces = getOwnPieces(invertedBoard, game)
                for (piece in ownPieces) {
                    for (i in 1 until game.getBoard().getXDimension()) {
                        for (j in 1 until game.getBoard().getYDimension()) {
                            val possibleMovement = Movement(invertedBoard[piece]!!, Coordinate(i, j))
                            if (game.validateMovement(possibleMovement) is ValidMovementResult) {
                                return false
                            }
                        }
                    }
                }
                return true
            }

        }
        return false
    }
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
//    override fun validateCheckMate(game: Game): Boolean {
//        val invertedBoard = game.getBoard().getInvertedBoard()
//        val invertedBoardKeys = invertedBoard.keys
//        for (key in invertedBoardKeys) {
//            if (key.getName() == Name.KING && key.getColor() == game.getCurrentPlayer()) {
//                val kingPosition = invertedBoard[key]
//                val enemyPieces = getEnemyPieces(invertedBoard, game)
//                val checkMateMovements = ArrayList<Movement>()
//                generateEnemyMovements(enemyPieces, invertedBoard, kingPosition, game, checkMateMovements)
//                val teamPieces = getTeamPieces(invertedBoard, game)
//                if (checkIfNoCheckMate(teamPieces, game, invertedBoard, checkMateMovements)) return false
//                return true
//            }
//        }
//        return false
//
//    }
//
//    private fun checkIfNoCheckMate(
//        teamPieces: List<Piece>,
//        game: Game,
//        invertedBoard: Map<Piece, Coordinate>,
//        checkMateMovements: ArrayList<Movement>
//    ): Boolean {
//        for (piece in teamPieces) {
//            for (i in 1..game.getBoard().getXDimension()) {
//                outer@ for (j in 1..game.getBoard().getYDimension()) {
//                    val move = Movement(invertedBoard[piece]!!, Coordinate(i, j))
//
//                    if (game.validateMovement(move)) {
//                        val newBoard = game.getBoard().move(move)
//                        val newGameState = Game(
//                            newBoard,
//                            game.getMovements(),
//                            game.getValidators(),
//                            game.getRules(),
//                            game.oppositePlayer(),
//                            game.getCheckMateValidators()
//                        )
//                        if (checkMateMovements.isNotEmpty()) {
//                            for (checkMateMove in checkMateMovements) {
//                                if (newGameState.validateMovement(checkMateMove)) {
//                                    break@outer
//                                }
//                            }
//                            return true
//
//                        }
//
//                    }
//
//
//                }
//            }
//        }
//        return false
//    }
//
//    private fun generateEnemyMovements(
//        enemyPieces: List<Piece>,
//        invertedBoard: Map<Piece, Coordinate>,
//        kingPosition: Coordinate?,
//        game: Game,
//        checkMateMovements: ArrayList<Movement>
//    ) {
//        for (piece in enemyPieces) {
//            val movement = Movement(invertedBoard[piece]!!, kingPosition!!)
//            val newGameState = Game(
//                game.getBoard(),
//                game.getMovements(),
//                game.getValidators(),
//                game.getRules(),
//                game.oppositePlayer(),
//                game.getCheckMateValidators()
//            )
//            if (newGameState.validateMovement(movement)) {
//                checkMateMovements.add(movement)
//            }
//        }
//    }
//
//    private fun getTeamPieces(
//        invertedBoard: Map<Piece, Coordinate>,
//        game: Game
//    ): List<Piece> {
//        val teamPieces = invertedBoard.keys.filter { it.getColor() == game.getCurrentPlayer() }
//        return teamPieces
//    }
//
//    private fun getEnemyPieces(
//        invertedBoard: Map<Piece, Coordinate>,
//        game: Game
//    ): List<Piece> {
//        val enemyPieces = invertedBoard.keys.filter { it.getColor() != game.getCurrentPlayer() }
//        return enemyPieces
//    }






