import edu.austral.dissis.chess.gameValidators.*
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.common.interfaces.CheckMateValidatorInterface
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.chess.orientationValidators.*
import edu.austral.dissis.chess.quantityValidators.LimitedQuantityMoveValidator
import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.chess.quantityValidators.FirstMovementValidator
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.validators.*
import gameValidators.checkValidators.CheckCheckMate
import gameValidators.checkValidators.CheckValidator

class Adapter : GameEngine {
    var game = createClassicChessGame()
    override fun applyMove(move: Move): MoveResult {
        try {
            val from = move.from
            val to = move.to
            val coordinateFrom = Coordinate(from.column, from.row)
            val coordinateTo = Coordinate(to.column, to.row)
            val newGame = game.move(coordinateFrom, coordinateTo)
            if (newGame.checkIfCheckMate()) {
                val color = newGame.oppositePlayer()
                return if (color == PieceColor.WHITE) {
                    GameOver(PlayerColor.WHITE)
                } else {
                    GameOver(PlayerColor.BLACK)
                }
            } else {
                game = newGame
                val playerColor = if (newGame.getCurrentPlayer() == PieceColor.WHITE) {
                    PlayerColor.WHITE
                } else {
                    PlayerColor.BLACK
                }
                return NewGameState(uiPieces(newGame.getBoard()), playerColor)
            }
        } catch (e: Exception) {
            return InvalidMove(e.message ?: "Invalid move")
        }


    }

    fun uiPieces(board: Board): List<ChessPiece> {
        val pieces = ArrayList<ChessPiece>()
        val boardContent = board.board.keys
        for (key in boardContent) {
            val piece = board.board[key]
            if (piece != null) {
                val pieceColor = if (piece.getColor() == PieceColor.WHITE) {
                    PlayerColor.WHITE
                } else {
                    PlayerColor.BLACK
                }
                val pieceChessPiece = piece.getName().getName();
                val piecePosition = key?.let { Position(it.yCoordinate, key.xCoordinate) }
                piecePosition?.let { ChessPiece(piece.getId().toString(), pieceColor, piecePosition, pieceChessPiece) }
                    ?.let { pieces.add(it) }
            }
        }
        return pieces
    }

    override fun init(): InitialState {
        val boardSize = BoardSize(8, 8)
        val currentPlayer = PlayerColor.WHITE
        return InitialState(boardSize, uiPieces(createClassicChessGame().getBoard()), currentPlayer)
    }

    fun pawnsRule(color: PieceColor): Validator {
        val orValidator = OrValidator(listOf(
            AndValidator(
            listOf(
                LimitedQuantityMoveValidator(2),
                VerticalObstacleMoveValidator(),
                VerticalMovementMoveValidator(),
                FirstMovementValidator(),
                EmptySquareValidator(),
                if (color==PieceColor.WHITE){
                    OrientationValidator(true)
                }
                else{
                    OrientationValidator(false)
                }
            )),
            AndValidator(listOf(
                LimitedQuantityMoveValidator(1),
                VerticalMovementMoveValidator(),
                EmptySquareValidator(),
                if (color==PieceColor.WHITE){
                    OrientationValidator(true)
                }
                else{
                    OrientationValidator(false)
                }
            )),
            AndValidator(listOf(
                LimitedQuantityMoveValidator(1),
                DiagonalMovementMoveValidator(),
                EnemyOnToValidator(),
                if (color==PieceColor.WHITE){
                    OrientationValidator(true)
                }
                else{
                    OrientationValidator(false)
                }
            ))
        ))
        return orValidator

    }


    fun queenRule(): Validator {
        val orValidator = OrValidator(listOf(
            AndValidator(listOf(
                DiagonalMovementMoveValidator(),
                DiagonalObstacleMoveValidator()
            )),
            AndValidator(listOf(
                HorizontalObstacleMoveValidator(),
                HorizontalMovementMoveValidator()
            )),
            AndValidator(listOf(
                VerticalObstacleMoveValidator(),
                VerticalMovementMoveValidator()
            ))

        ))
        return orValidator;
    }

    fun kingRule(): Validator {
        val orValidator = OrValidator(
            listOf(
                AndValidator(listOf(
                    LimitedQuantityMoveValidator(1),
                    DiagonalMovementMoveValidator()
                )),
                AndValidator(listOf(
                    LimitedQuantityMoveValidator(1),
                    HorizontalMovementMoveValidator()
                )),
                AndValidator(listOf(
                    LimitedQuantityMoveValidator(1),
                    VerticalMovementMoveValidator()
                ))
            )
        )
        return orValidator
    }

    fun bishopRule(): Validator {
        val orValidator = OrValidator(
            listOf(
                AndValidator(
                    listOf(
                        DiagonalMovementMoveValidator(),
                        DiagonalObstacleMoveValidator()
                    )
                )
            )
        )
        return orValidator
    }

    fun rookRule(): Validator {
        val orValidator = OrValidator(
            listOf(
                AndValidator(listOf(
                    HorizontalObstacleMoveValidator(),
                    HorizontalMovementMoveValidator()
                )),
                AndValidator(listOf(
                    VerticalObstacleMoveValidator(),
                    VerticalMovementMoveValidator()
                ))

        ))
        return orValidator
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


    fun gameValidators(gameValidator: ArrayList<Validator>): MutableList<Validator> {
        val insideBoardValidator = InsideBoardMoveValidator()
        val makeAMoveValidator = MakeAMoveMoveValidator()
        val turnValidator = TurnValidator()
        val checkValidator = CheckValidator()
        val ownObstacleOnToMoveValidator = OwnObstacleOnToMoveValidator()
        gameValidator.add(insideBoardValidator)
        gameValidator.add(makeAMoveValidator)
        gameValidator.add(turnValidator)
        gameValidator.add(checkValidator)
        gameValidator.add(ownObstacleOnToMoveValidator)
        return gameValidator
    }

    fun checkMateValidators(checkMateValidators: ArrayList<CheckMateValidatorInterface>): ArrayList<CheckMateValidatorInterface> {
        val checkMateValidator = CheckCheckMate()
        checkMateValidators.add(checkMateValidator)
        return checkMateValidators
    }

    fun createClassicChessGame(): Game {
        val gameBoard: MutableMap<Coordinate?, Piece?> = HashMap()
        val board: Board = Board(gameBoard, 8, 8)
        val gameMoveValidators: MutableList<Validator> = gameValidators(ArrayList<Validator>())
        val checkMateValidators = checkMateValidators(ArrayList<CheckMateValidatorInterface>())
        val pieceRules: MutableMap<Piece, Validator> = HashMap()
        pieceRules[Piece(ChessPieceName.KING, PieceColor.WHITE, 13)] = kingRule()
        pieceRules[Piece(ChessPieceName.KING, PieceColor.BLACK, 29)] = kingRule()
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

        for (i in 1..8) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.WHITE, i)] = pawnsRule(PieceColor.WHITE)
        }
        for (i in 17..24) {
            pieceRules[Piece(ChessPieceName.PAWN, PieceColor.BLACK, i)] = pawnsRule(PieceColor.BLACK)
        }
        val game =
            Game(board, ArrayList<Board>(), gameMoveValidators, pieceRules, PieceColor.WHITE, checkMateValidators)

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

        return game
    }

}