package edu.austral.dissis.checkers.factory

import edu.austral.dissis.checkers.CheckersMovementExecutioner
import edu.austral.dissis.checkers.actionValidators.IsCheckersPawnValidator
import edu.austral.dissis.checkers.actions.CheckersEatingAction
import edu.austral.dissis.checkers.actions.CheckersNormalPromotionAction
import edu.austral.dissis.checkers.actions.CheckersPromotionAndEatingAction
import edu.austral.dissis.checkers.turnManagers.CheckersTurnManager
import edu.austral.dissis.common.endGames.NoPiecesLeftValidator
import edu.austral.dissis.checkers.entities.CheckersPieceName
import edu.austral.dissis.checkers.validators.CanEatValidator
import edu.austral.dissis.checkers.validators.MustEatPieceValidator
import edu.austral.dissis.chess.quantityValidators.LimitedQuantityMoveValidator
import edu.austral.dissis.common.actionValidators.IsAtTheEndOfTheBoardValidator
import edu.austral.dissis.common.actions.NormalMoveAction
import edu.austral.dissis.common.entities.*
import edu.austral.dissis.common.interfaces.Action
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.interfaces.endGameValidator
import edu.austral.dissis.common.validators.*
import edu.austral.dissis.utils.and
import edu.austral.dissis.utils.or

class ClassicCheckersGame {

    fun checkersPawnPromotionValidator() : Validator{
        return or(
            and(
                IsCheckersPawnValidator(),
                IsAtTheEndOfTheBoardValidator()
            )
        )
    }
    fun checkersPromotionAndEatingValidator() : Validator{
        return or(
            and(
                IsCheckersPawnValidator(),
                IsAtTheEndOfTheBoardValidator(),
                CanEatValidator()
            )
        )
    }
    fun checkersPawnsRule(color: PieceColor): Validator {
        val noEatingMovement = AndValidator(listOf(
        LimitedQuantityMoveValidator(1),
        DiagonalMovementMoveValidator(),
        EmptySquareOnToValidator(),
        MustEatPieceValidator(),
        if (color == PieceColor.WHITE) {
            OrientationValidator(true)
        } else {
            OrientationValidator(false)
        }
        ))

        val eatingMovement = AndValidator(listOf(
            LimitedQuantityMoveValidator(2),
            DiagonalMovementMoveValidator(),
            EmptySquareOnToValidator(),
            MustEatPieceValidator(),
            CanEatValidator(),
            if (color == PieceColor.WHITE) {
                OrientationValidator(true)
            } else {
                OrientationValidator(false)
            }
        )
        )
        val orValidator = OrValidator(
            listOf(
                noEatingMovement,
                eatingMovement
            )
        )
        return orValidator

    }
    fun checkersGameValidators(gameValidator: ArrayList<Validator>):MutableList<Validator>{
        val insideBoardValidator = InsideBoardMoveValidator()
        val makeAMoveValidator = MakeAMoveMoveValidator()
        val turnValidator = TurnValidator()
        val ownObstacleOnToMoveValidator = OwnObstacleOnToMoveValidator()
        val emptySquareOnFromValidator = EmptySquareOnFromValidator()
        gameValidator.add(insideBoardValidator)
        gameValidator.add(emptySquareOnFromValidator)
        gameValidator.add(makeAMoveValidator)
        gameValidator.add(turnValidator)
        gameValidator.add(ownObstacleOnToMoveValidator)
        return gameValidator

    }
    fun checkersEndGameValidators(endGameValidators: ArrayList<endGameValidator>):ArrayList<endGameValidator>{
        val noPiecesLeftValidator = NoPiecesLeftValidator()
        endGameValidators.add(noPiecesLeftValidator)
        return endGameValidators
    }
    fun checkersKingRule(): Validator {
        return OrValidator(
            listOf(
                AndValidator(
                    listOf(
                        LimitedQuantityMoveValidator(1),
                        DiagonalMovementMoveValidator(),
                        EmptySquareOnToValidator(),
                        MustEatPieceValidator()
                    )
                ),
                AndValidator(
                    listOf(
                        LimitedQuantityMoveValidator(2),
                        DiagonalMovementMoveValidator(),
                        EmptySquareOnToValidator(),
                        MustEatPieceValidator(),
                        CanEatValidator()
                    )
                )
            )
        )
    }
    fun createClassicCheckersGame(): Game {
        val gameBoard: MutableMap<Coordinate, Piece?> = HashMap()
        val board: Board = Board(gameBoard, 8, 8)
        val gameMoveValidators: MutableList<Validator> = checkersGameValidators(ArrayList())
        val endGameValidators = checkersEndGameValidators(ArrayList())
        val pieceRules: MutableMap<Piece, Validator> = HashMap()
        val checkersManager = CheckersTurnManager(-1)
        for (i in 1..12) {
            pieceRules[Piece(CheckersPieceName.PAWN, PieceColor.WHITE, i)] = checkersPawnsRule(PieceColor.WHITE)
        }
        for (i in 13..24) {
            pieceRules[Piece(CheckersPieceName.PAWN, PieceColor.BLACK, i)] = checkersPawnsRule(PieceColor.BLACK)
        }
        pieceRules[Piece(CheckersPieceName.KING, PieceColor.WHITE, -1)] = checkersKingRule()
        pieceRules[Piece(CheckersPieceName.KING, PieceColor.BLACK, -2)] = checkersKingRule()
        gameBoard[Coordinate(1,8)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,13)
        gameBoard[Coordinate(3,8)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,14)
        gameBoard[Coordinate(5,8)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,15)
        gameBoard[Coordinate(7,8)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,16)

        gameBoard[Coordinate(2,7)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,17)
        gameBoard[Coordinate(4,7)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,18)
        gameBoard[Coordinate(6,7)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,19)
        gameBoard[Coordinate(8,7)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,20)

        gameBoard[Coordinate(1,6)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,21)
        gameBoard[Coordinate(3,6)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,22)
        gameBoard[Coordinate(5,6)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,23)
        gameBoard[Coordinate(7,6)]= Piece(CheckersPieceName.PAWN,PieceColor.BLACK,24)

        gameBoard[Coordinate(2,1)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,1)
        gameBoard[Coordinate(4,1)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,2)
        gameBoard[Coordinate(6,1)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,3)
        gameBoard[Coordinate(8,1)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,4)

        gameBoard[Coordinate(1,2)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,5)
        gameBoard[Coordinate(3,2)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,6)
        gameBoard[Coordinate(5,2)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,7)
        gameBoard[Coordinate(7,2)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,8)

        gameBoard[Coordinate(2,3)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,9)
        gameBoard[Coordinate(4,3)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,10)
        gameBoard[Coordinate(6,3)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,11)
        gameBoard[Coordinate(8,3)]= Piece(CheckersPieceName.PAWN,PieceColor.WHITE,12)

        val actions = uploadActions(ArrayList())
        val checkersExecutioner = CheckersMovementExecutioner(actions)
        val game = Game(board, ArrayList<Board>(),gameMoveValidators,pieceRules,PieceColor.WHITE,endGameValidators,checkersExecutioner,checkersManager)
        return game
    }
    private fun uploadActions(actions: MutableList<Action>):List<Action>{
        val checkersPromotionAndEatingAction = CheckersPromotionAndEatingAction(checkersPromotionAndEatingValidator())
        val checkersPawnPromotionAction = CheckersNormalPromotionAction(checkersPawnPromotionValidator())
        val checkersEatingAction = CheckersEatingAction(CanEatValidator())

        actions.add(checkersPromotionAndEatingAction)
        actions.add(checkersPawnPromotionAction)
        actions.add(checkersEatingAction)
        actions.add(NormalMoveAction())
        return actions

    }
}