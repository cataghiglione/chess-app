package edu.austral.dissis.chess.factory

import edu.austral.dissis.chess.ChessMovementExecutioner
import edu.austral.dissis.chess.ClassicChessTurnManager
import edu.austral.dissis.chess.actionValidators.ExactQuantityMoveValidator
import edu.austral.dissis.common.actionValidators.IsAtTheEndOfTheBoardValidator
import edu.austral.dissis.chess.actionValidators.IsChessPawnValidator
import edu.austral.dissis.chess.actionValidators.IsHorizontalLeftOrientationValidator
import edu.austral.dissis.chess.actionValidators.IsHorizontalRightOrientationValidator
import edu.austral.dissis.chess.actions.ChessLeftCastlingAction
import edu.austral.dissis.chess.actions.ChessPawnPromotionAction
import edu.austral.dissis.chess.actions.ChessRightCastlingAction
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.chess.gameValidators.DiagonalObstacleMoveValidator
import edu.austral.dissis.chess.gameValidators.EnemyOnToValidator
import edu.austral.dissis.chess.gameValidators.HorizontalObstacleMoveValidator
import edu.austral.dissis.chess.gameValidators.VerticalObstacleMoveValidator
import edu.austral.dissis.chess.orientationValidators.HorizontalMovementMoveValidator
import edu.austral.dissis.chess.orientationValidators.LMovementValidator
import edu.austral.dissis.chess.orientationValidators.VerticalMovementMoveValidator
import edu.austral.dissis.chess.quantityValidators.FirstMovementValidator
import edu.austral.dissis.chess.quantityValidators.LimitedQuantityMoveValidator
import edu.austral.dissis.common.actions.NormalMoveAction
import edu.austral.dissis.common.entities.*
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.interfaces.endGameValidator
import edu.austral.dissis.common.validators.*
import edu.austral.dissis.utils.and
import edu.austral.dissis.utils.or
import gameValidators.checkValidators.CheckEndGame
import gameValidators.checkValidators.CheckValidator

class ClassicChessGame {

    private fun chessRightCastlingValidator(): Validator{
        return or(
            and(
                IsHorizontalRightOrientationValidator(),
                FirstMovementValidator(),
                ExactQuantityMoveValidator(2),
                HorizontalObstacleMoveValidator(),
                HorizontalMovementMoveValidator()
            )
        )
    }

    private fun chessLeftCastlingValidator():Validator{
        return or(
            and(
                IsHorizontalLeftOrientationValidator(),
                FirstMovementValidator(),
                ExactQuantityMoveValidator(2),
                HorizontalObstacleMoveValidator(),
                HorizontalMovementMoveValidator()

            )
        )
    }
    private fun chessPromotionValidator(): Validator{
        return or(
            and(
                IsChessPawnValidator(),
                IsAtTheEndOfTheBoardValidator()
            )
        )
    }
    private fun chessPawnsRule(color: PieceColor): Validator {
        val orValidator = OrValidator(
            listOf(
                AndValidator(
                    listOf(
                        LimitedQuantityMoveValidator(2),
                        VerticalObstacleMoveValidator(),
                        VerticalMovementMoveValidator(),
                        FirstMovementValidator(),
                        EmptySquareOnToValidator(),
                        if (color == PieceColor.WHITE) {
                            OrientationValidator(true)
                        } else {
                            OrientationValidator(false)
                        }
                    )
                ),
                AndValidator(
                    listOf(
                        LimitedQuantityMoveValidator(1),
                        VerticalMovementMoveValidator(),
                        EmptySquareOnToValidator(),
                        if (color == PieceColor.WHITE) {
                            OrientationValidator(true)
                        } else {
                            OrientationValidator(false)
                        }
                    )
                ),
                AndValidator(
                    listOf(
                        LimitedQuantityMoveValidator(1),
                        DiagonalMovementMoveValidator(),
                        EnemyOnToValidator(),
                        if (color == PieceColor.WHITE) {
                            OrientationValidator(true)
                        } else {
                            OrientationValidator(false)
                        }
                    )
                )
            )
        )
        return orValidator

    }

    fun queenRule(): Validator {
        val orValidator = OrValidator(
            listOf(
                AndValidator(
                    listOf(
                        DiagonalMovementMoveValidator(),
                        DiagonalObstacleMoveValidator()
                    )
                ),
                AndValidator(
                    listOf(
                        HorizontalObstacleMoveValidator(),
                        HorizontalMovementMoveValidator()
                    )
                ),
                AndValidator(
                    listOf(
                        VerticalObstacleMoveValidator(),
                        VerticalMovementMoveValidator()
                    )
                )

            )
        )
        return orValidator;
    }


    fun chessKingRule(): Validator {
        return or(
            and( LimitedQuantityMoveValidator(1),
                DiagonalMovementMoveValidator()
            ),
            and(
                LimitedQuantityMoveValidator(1),
                HorizontalMovementMoveValidator()
            ),
            and(
                LimitedQuantityMoveValidator(1),
                VerticalMovementMoveValidator()
            ),
            chessLeftCastlingValidator(),
            chessRightCastlingValidator()

        )
//        val orValidator = OrValidator(
//            listOf(
//                AndValidator(
//                    listOf(
//                        LimitedQuantityMoveValidator(1),
//                        DiagonalMovementMoveValidator()
//                    )
//                ),
//                AndValidator(
//                    listOf(
//                        LimitedQuantityMoveValidator(1),
//                        HorizontalMovementMoveValidator()
//                    )
//                ),
//                AndValidator(
//                    listOf(
//                        LimitedQuantityMoveValidator(1),
//                        VerticalMovementMoveValidator()
//                    )
//                )
//            )
//        )
//        return orValidator
    }

    fun bishopRule(): Validator {
        return or(
            and(
                DiagonalObstacleMoveValidator(),
                DiagonalMovementMoveValidator()
            )
        )
    }



    fun rookRule(): Validator {
        return or(
            and(
                HorizontalObstacleMoveValidator(),
                HorizontalMovementMoveValidator()
            ),
            and(
                VerticalObstacleMoveValidator(),
                VerticalMovementMoveValidator()
            )
        )
    }

    fun knightRule(): Validator {
        val orValidator = OrValidator(
            listOf(
                AndValidator(
                    listOf(
                        LMovementValidator()

                    )
                )
            )
        )
        return orValidator
    }

    fun chessGameValidators(gameValidator: ArrayList<Validator>): MutableList<Validator> {
        val insideBoardValidator = InsideBoardMoveValidator()
        val makeAMoveValidator = MakeAMoveMoveValidator()
        val turnValidator = TurnValidator()
        val checkValidator = CheckValidator()
        val ownObstacleOnToMoveValidator = OwnObstacleOnToMoveValidator()
        val emptySquareOnFromValidator = EmptySquareOnFromValidator()
        gameValidator.add(insideBoardValidator)
        gameValidator.add(emptySquareOnFromValidator)
        gameValidator.add(makeAMoveValidator)
        gameValidator.add(turnValidator)
        gameValidator.add(checkValidator)
        gameValidator.add(ownObstacleOnToMoveValidator)
        return gameValidator
    }

    fun chessCheckMateValidators(checkMateValidators: ArrayList<endGameValidator>): ArrayList<endGameValidator> {
        val checkMateValidator = CheckEndGame()
        checkMateValidators.add(checkMateValidator)
        return checkMateValidators
    }

    fun archbishopRule(): Validator {
        return or(
            and(
                DiagonalObstacleMoveValidator(),
                DiagonalMovementMoveValidator()
            ),
            and(LMovementValidator()
            )
        )
    }

    fun chancellorRule(): Validator {
        return or(
            and(
                HorizontalObstacleMoveValidator(),
                HorizontalMovementMoveValidator()
            ),
            and(
                VerticalObstacleMoveValidator(),
                VerticalMovementMoveValidator()
            ),
            and(LMovementValidator())
        )

    }

    fun createChancellorChessGame(): Game {
        val gameBoard: MutableMap<Coordinate, Piece?> = HashMap()
        val board: Board = Board(gameBoard, 8, 8)
        val gameMoveValidators: MutableList<Validator> = chessGameValidators(ArrayList<Validator>())
        val checkMateValidators = chessCheckMateValidators(ArrayList<endGameValidator>())
        val pieceRules: MutableMap<Piece, Validator> = HashMap()
        uploadChancellorChessRules(pieceRules)
        val actions = uploadActions(ArrayList<Action>())
        val chessMovementExecutioner = ChessMovementExecutioner(actions)
        val chessManager = ClassicChessTurnManager()
        val game =
            Game(
                board,
                ArrayList<Board>(),
                gameMoveValidators,
                pieceRules,
                PieceColor.WHITE,
                checkMateValidators,
                chessMovementExecutioner,
                chessManager

            )

        putChancellorChessPiecesIntoPlace(gameBoard)
        return game

    }

    fun createClassicChessGame(): Game {
        val gameBoard = putClassicChessPiecesIntoPlace(HashMap())
        val board: Board = Board(gameBoard, 8, 8)

        val gameMoveValidators: MutableList<Validator> = chessGameValidators(ArrayList<Validator>())

        val checkMateValidators = chessCheckMateValidators(ArrayList<endGameValidator>())

        val pieceRules: MutableMap<Piece, Validator> = HashMap()
        uploadClassicChessRules(pieceRules)

        val actions = uploadActions(ArrayList<Action>())

        val chessMovementExecutioner = ChessMovementExecutioner(actions)
        val chessManager = ClassicChessTurnManager()
        val game =
            Game(
                board,
                ArrayList<Board>(),
                gameMoveValidators,
                pieceRules,
                PieceColor.WHITE,
                checkMateValidators,
                chessMovementExecutioner,
                chessManager,
            )

        return game
    }

    private fun putClassicChessPiecesIntoPlace(gameBoard: MutableMap<Coordinate, Piece>): Map<Coordinate, Piece> {
        for (i in 1..8) {
            gameBoard[Coordinate(i, 2)] = Piece(ChessPieceName.PAWN, PieceColor.WHITE, i)
            gameBoard[Coordinate(i, 7)] = Piece(ChessPieceName.PAWN, PieceColor.BLACK, i + 16)
        }
        gameBoard[Coordinate(4, 1)] = Piece(ChessPieceName.QUEEN, PieceColor.WHITE, 12)
        gameBoard[Coordinate(5, 1)] = Piece(ChessPieceName.KING, PieceColor.WHITE, 13)
        gameBoard[Coordinate(4, 8)] = Piece(ChessPieceName.QUEEN, PieceColor.BLACK, 28)
        gameBoard[Coordinate(5, 8)] = Piece(ChessPieceName.KING, PieceColor.BLACK, 29)

        gameBoard[Coordinate(1, 1)] = Piece(ChessPieceName.ROOK, PieceColor.WHITE, 9)
        gameBoard[Coordinate(8, 1)] = Piece(ChessPieceName.ROOK, PieceColor.WHITE, 16)
        gameBoard[Coordinate(1, 8)] = Piece(ChessPieceName.ROOK, PieceColor.BLACK, 25)
        gameBoard[Coordinate(8, 8)] = Piece(ChessPieceName.ROOK, PieceColor.BLACK, 32)

        gameBoard[Coordinate(2, 1)] = Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 10)
        gameBoard[Coordinate(7, 1)] = Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 15)
        gameBoard[Coordinate(2, 8)] = Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 26)
        gameBoard[Coordinate(7, 8)] = Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 31)

        gameBoard[Coordinate(3, 1)] = Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 11)
        gameBoard[Coordinate(6, 1)] = Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 14)
        gameBoard[Coordinate(3, 8)] = Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 27)
        gameBoard[Coordinate(6, 8)] = Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 30)
        return gameBoard
    }

    private fun uploadClassicChessRules(pieceRules: MutableMap<Piece, Validator>) {
        for (i in 1..8) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.WHITE, i)] = chessPawnsRule(PieceColor.WHITE)
        }
        for (i in 17..24) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.BLACK, i)] = chessPawnsRule(PieceColor.BLACK)
        }
        pieceRules[Piece(ChessPieceName.KING, PieceColor.WHITE, 13)] = chessKingRule()
        pieceRules[Piece(ChessPieceName.KING, PieceColor.BLACK, 29)] = chessKingRule()
        pieceRules[Piece(ChessPieceName.QUEEN, PieceColor.WHITE, 12)] = queenRule()
        pieceRules[Piece(ChessPieceName.QUEEN, PieceColor.BLACK, 28)] = queenRule()
        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.WHITE, 9)] = rookRule()
        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.WHITE, 16)] = rookRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 10)] = knightRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 15)] = knightRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 11)] = bishopRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 14)] = bishopRule()
        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.BLACK, 25)] = rookRule()
        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.BLACK, 32)] = rookRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 26)] = knightRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 31)] = knightRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 27)] = bishopRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 30)] = bishopRule()
    }

    private fun uploadChancellorChessRules(pieceRules: MutableMap<Piece, Validator>) {
        for (i in 1..8) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.WHITE, i)] = chessPawnsRule(PieceColor.WHITE)
        }
        for (i in 17..24) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.BLACK, i)] = chessPawnsRule(PieceColor.BLACK)
        }
        pieceRules[Piece(ChessPieceName.KING, PieceColor.WHITE, 13)] = chessKingRule()
        pieceRules[Piece(ChessPieceName.KING, PieceColor.BLACK, 29)] = chessKingRule()

        pieceRules[Piece(ChessPieceName.QUEEN, PieceColor.WHITE, 12)] = queenRule()
        pieceRules[Piece(ChessPieceName.QUEEN, PieceColor.BLACK, 28)] = queenRule()

        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 11)] = bishopRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 14)] = bishopRule()

        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 10)] = knightRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 15)] = knightRule()

        pieceRules[Piece(ChessPieceName.CHANCELLOR, PieceColor.WHITE, 9)] = chancellorRule()
        pieceRules[Piece(ChessPieceName.CHANCELLOR, PieceColor.WHITE, 16)] = chancellorRule()

        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 27)] = bishopRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 30)] = bishopRule()

        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 26)] = knightRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 31)] = knightRule()

        pieceRules[Piece(ChessPieceName.CHANCELLOR, PieceColor.BLACK, 25)] = chancellorRule()
        pieceRules[Piece(ChessPieceName.CHANCELLOR, PieceColor.BLACK, 32)] = chancellorRule()
    }

    private fun putChancellorChessPiecesIntoPlace(gameBoard: MutableMap<Coordinate, Piece?>) {
        for (i in 1..8) {
            gameBoard[Coordinate(i, 2)] = Piece(ChessPieceName.PAWN, PieceColor.WHITE, i)
            gameBoard[Coordinate(i, 7)] = Piece(ChessPieceName.PAWN, PieceColor.BLACK, i + 16)
        }
        gameBoard[Coordinate(4, 1)] = Piece(ChessPieceName.QUEEN, PieceColor.WHITE, 12)
        gameBoard[Coordinate(5, 1)] = Piece(ChessPieceName.KING, PieceColor.WHITE, 13)
        gameBoard[Coordinate(4, 8)] = Piece(ChessPieceName.QUEEN, PieceColor.BLACK, 28)
        gameBoard[Coordinate(5, 8)] = Piece(ChessPieceName.KING, PieceColor.BLACK, 29)

        gameBoard[Coordinate(1, 1)] = Piece(ChessPieceName.CHANCELLOR, PieceColor.WHITE, 9)
        gameBoard[Coordinate(8, 1)] = Piece(ChessPieceName.CHANCELLOR, PieceColor.WHITE, 16)
        gameBoard[Coordinate(1, 8)] = Piece(ChessPieceName.CHANCELLOR, PieceColor.BLACK, 25)
        gameBoard[Coordinate(8, 8)] = Piece(ChessPieceName.CHANCELLOR, PieceColor.BLACK, 32)

        gameBoard[Coordinate(3, 1)] = Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 11)
        gameBoard[Coordinate(6, 1)] = Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 14)
        gameBoard[Coordinate(3, 8)] = Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 27)
        gameBoard[Coordinate(6, 8)] = Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 30)

        gameBoard[Coordinate(2, 1)] = Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 10)
        gameBoard[Coordinate(7, 1)] = Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 15)
        gameBoard[Coordinate(2, 8)] = Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 26)
        gameBoard[Coordinate(7, 8)] = Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 31)

    }
    private fun uploadActions(actions: MutableList<Action>):List<Action>{
        val chessPawnPromotionAction = ChessPawnPromotionAction(chessPromotionValidator())
        actions.add(chessPawnPromotionAction)
        val chessRightCastlingAction = ChessRightCastlingAction(chessRightCastlingValidator())
        actions.add(chessRightCastlingAction)
        val chessLeftCastlingAction = ChessLeftCastlingAction(chessLeftCastlingValidator())
        actions.add(chessLeftCastlingAction)

        actions.add(NormalMoveAction())
        return actions

    }


}