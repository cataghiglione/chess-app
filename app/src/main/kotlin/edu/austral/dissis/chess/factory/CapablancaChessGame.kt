package edu.austral.dissis.chess.factory

import edu.austral.dissis.chess.ChessMovementExecutioner
import edu.austral.dissis.chess.ClassicChessTurnManager
import edu.austral.dissis.chess.actionValidators.ExactQuantityMoveValidator
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
import edu.austral.dissis.common.actionValidators.IsAtTheEndOfTheBoardValidator
import edu.austral.dissis.common.actions.NormalMoveAction
import edu.austral.dissis.common.endGames.NoPiecesLeftValidator
import edu.austral.dissis.common.entities.*
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.interfaces.endGameValidator
import edu.austral.dissis.common.validators.*
import edu.austral.dissis.utils.and
import edu.austral.dissis.utils.or
import gameValidators.checkValidators.CheckEndGame
import gameValidators.checkValidators.CheckValidator

class CapablancaChessGame {
    private fun chessRightCastlingValidator(): Validator {
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

    private fun chessLeftCastlingValidator(): Validator {
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
    private fun chessPromotionValidator(): Validator {
        return or(
            and(
                IsChessPawnValidator(),
                IsAtTheEndOfTheBoardValidator()
            )
        )
    }
    private fun chessPawnsRule(color: PieceColor): Validator {
        return or(
            and(
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

            ),
            and(
                LimitedQuantityMoveValidator(1),
                VerticalMovementMoveValidator(),
                EmptySquareOnToValidator(),
                if (color == PieceColor.WHITE) {
                    OrientationValidator(true)
                } else {
                    OrientationValidator(false)
                }
            ),
            and(
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
//        val orValidator = OrValidator(
//            listOf(
//                AndValidator(
//                    listOf(
//                        LimitedQuantityMoveValidator(2),
//                        VerticalObstacleMoveValidator(),
//                        VerticalMovementMoveValidator(),
//                        FirstMovementValidator(),
//                        EmptySquareOnToValidator(),
//                        if (color == PieceColor.WHITE) {
//                            OrientationValidator(true)
//                        } else {
//                            OrientationValidator(false)
//                        }
//                    )
//                ),
//                AndValidator(
//                    listOf(
//                        LimitedQuantityMoveValidator(1),
//                        VerticalMovementMoveValidator(),
//                        EmptySquareOnToValidator(),
//                        if (color == PieceColor.WHITE) {
//                            OrientationValidator(true)
//                        } else {
//                            OrientationValidator(false)
//                        }
//                    )
//                ),
//                AndValidator(
//                    listOf(
//                        LimitedQuantityMoveValidator(1),
//                        DiagonalMovementMoveValidator(),
//                        EnemyOnToValidator(),
//                        if (color == PieceColor.WHITE) {
//                            OrientationValidator(true)
//                        } else {
//                            OrientationValidator(false)
//                        }
//                    )
//                )
//            )
//        )
//        return orValidator

    }

    private fun queenRule(): Validator {
        return or(
            and(
                DiagonalMovementMoveValidator(),
                DiagonalObstacleMoveValidator()

            ),
            and(
                HorizontalObstacleMoveValidator(),
                HorizontalMovementMoveValidator()

            ),
            and(
                VerticalObstacleMoveValidator(),
                VerticalMovementMoveValidator()
            )

        )
//        val orValidator = OrValidator(
//            listOf(
//                AndValidator(
//                    listOf(
//                        DiagonalMovementMoveValidator(),
//                        DiagonalObstacleMoveValidator()
//                    )
//                ),
//                AndValidator(
//                    listOf(
//                        HorizontalObstacleMoveValidator(),
//                        HorizontalMovementMoveValidator()
//                    )
//                ),
//                AndValidator(
//                    listOf(
//                        VerticalObstacleMoveValidator(),
//                        VerticalMovementMoveValidator()
//                    )
//                )
//
//            )
//        )
//        return orValidator;
    }


    private fun chessKingRule(): Validator {
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

    private fun bishopRule(): Validator {
        return or(
            and(
                DiagonalObstacleMoveValidator(),
                DiagonalMovementMoveValidator()
            )
        )
    }



    private fun rookRule(): Validator {
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

    private fun knightRule(): Validator {
        return or(
            and(
                LMovementValidator()
            )
        )

//        val orValidator = OrValidator(
//            listOf(
//                AndValidator(
//                    listOf(
//                        LMovementValidator()
//
//                    )
//                )
//            )
//        )
//        return orValidator
    }

    private fun archbishopRule(): Validator {
        return or(
            and(
                DiagonalObstacleMoveValidator(),
                DiagonalMovementMoveValidator()
            ),
            and(
                LMovementValidator()
            )
        )
    }

    private fun chancellorRule(): Validator {
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

    private fun chessGameValidators(gameValidator: ArrayList<Validator>): MutableList<Validator> {
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

    private fun chessCheckMateValidators(checkMateValidators: ArrayList<endGameValidator>): ArrayList<endGameValidator> {
        val checkMateValidator = CheckEndGame()
        checkMateValidators.add(checkMateValidator)
        val noPiecesLeftValidator = NoPiecesLeftValidator()
        checkMateValidators.add(noPiecesLeftValidator)
        return checkMateValidators
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
    private fun putCapablancaChessPiecesIntoPlace(gameBoard: MutableMap<Coordinate, Piece?>) {
        for (i in 1..10) {
            gameBoard[Coordinate(i, 2)] = Piece(ChessPieceName.PAWN, PieceColor.WHITE, i)
            gameBoard[Coordinate(i, 7)] = Piece(ChessPieceName.PAWN, PieceColor.BLACK, i + 16)
        }
        gameBoard[Coordinate(5, 1)] = Piece(ChessPieceName.QUEEN, PieceColor.WHITE, 11)
        gameBoard[Coordinate(6, 1)] = Piece(ChessPieceName.KING, PieceColor.WHITE, 12)
        gameBoard[Coordinate(5, 8)] = Piece(ChessPieceName.QUEEN, PieceColor.BLACK, 13)
        gameBoard[Coordinate(6, 8)] = Piece(ChessPieceName.KING, PieceColor.BLACK, 14)

        gameBoard[Coordinate(1, 1)] = Piece(ChessPieceName.ROOK, PieceColor.WHITE, 15)
        gameBoard[Coordinate(10, 1)] = Piece(ChessPieceName.ROOK, PieceColor.WHITE, 16)
        gameBoard[Coordinate(1, 8)] = Piece(ChessPieceName.ROOK, PieceColor.BLACK, 27)
        gameBoard[Coordinate(10, 8)] = Piece(ChessPieceName.ROOK, PieceColor.BLACK, 28)

        gameBoard[Coordinate(4, 1)] = Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 29)
        gameBoard[Coordinate(7, 1)] = Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 30)
        gameBoard[Coordinate(4, 8)] = Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 31)
        gameBoard[Coordinate(8, 8)] = Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 32)

        gameBoard[Coordinate(2, 1)] = Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 33)
        gameBoard[Coordinate(9, 1)] = Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 34)
        gameBoard[Coordinate(2, 8)] = Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 35)
        gameBoard[Coordinate(9, 8)] = Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 36)

        gameBoard[Coordinate(3,1)] = Piece(ChessPieceName.ARCHBISHOP, PieceColor.WHITE, 37)
        gameBoard[Coordinate(3,8)] = Piece(ChessPieceName.ARCHBISHOP, PieceColor.BLACK, 39)
        gameBoard[Coordinate(8,1)] = Piece(ChessPieceName.CHANCELLOR, PieceColor.WHITE, 38)
        gameBoard[Coordinate(7,8)] = Piece(ChessPieceName.CHANCELLOR, PieceColor.BLACK, 40)

    }
    private fun uploadCapablancaChessRules(pieceRules: MutableMap<Piece, Validator>) {
        for (i in 1..10) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.WHITE, i)] = chessPawnsRule(PieceColor.WHITE)
        }
        for (i in 17..26) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.BLACK, i)] = chessPawnsRule(PieceColor.BLACK)
        }
        pieceRules[Piece(ChessPieceName.KING, PieceColor.WHITE, 12)] = chessKingRule()
        pieceRules[Piece(ChessPieceName.KING, PieceColor.BLACK, 14)] = chessKingRule()

        pieceRules[Piece(ChessPieceName.QUEEN, PieceColor.WHITE, 11)] = queenRule()
        pieceRules[Piece(ChessPieceName.QUEEN, PieceColor.BLACK, 13)] = queenRule()

        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 29)] = bishopRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.WHITE, 30)] = bishopRule()

        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 33)] = knightRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.WHITE, 34)] = knightRule()

        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.WHITE, 15)] = rookRule()
        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.WHITE, 16)] = rookRule()

        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 31)] = bishopRule()
        pieceRules[Piece(ChessPieceName.BISHOP, PieceColor.BLACK, 32)] = bishopRule()

        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 35)] = knightRule()
        pieceRules[Piece(ChessPieceName.KNIGHT, PieceColor.BLACK, 36)] = knightRule()

        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.BLACK, 27)] = rookRule()
        pieceRules[Piece(ChessPieceName.ROOK, PieceColor.BLACK, 28)] = rookRule()

        pieceRules[Piece(ChessPieceName.ARCHBISHOP, PieceColor.WHITE, 37)] = archbishopRule()
        pieceRules[Piece(ChessPieceName.ARCHBISHOP, PieceColor.BLACK, 39)] = archbishopRule()

        pieceRules[Piece(ChessPieceName.CHANCELLOR, PieceColor.WHITE, 38)] = chancellorRule()
        pieceRules[Piece(ChessPieceName.CHANCELLOR, PieceColor.BLACK, 40)] = chancellorRule()
    }
    fun createCapablancaChessGame(): Game {
        val gameBoard: MutableMap<Coordinate, Piece?> = HashMap()
        val board: Board = Board(gameBoard, 10, 8)
        val gameMoveValidators: MutableList<Validator> = chessGameValidators(ArrayList<Validator>())
        val checkMateValidators = chessCheckMateValidators(ArrayList<endGameValidator>())
        val pieceRules: MutableMap<Piece, Validator> = HashMap()
        uploadCapablancaChessRules(pieceRules)
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

        putCapablancaChessPiecesIntoPlace(gameBoard)
        return game

    }




}