package edu.austral.dissis.common.entities

import edu.austral.dissis.common.gameResults.GameOverGameResult
import edu.austral.dissis.common.gameResults.InvalidGameResult
import edu.austral.dissis.common.gameResults.ValidGameResult
import edu.austral.dissis.common.interfaces.*
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult



class Game(
    private val board: Board,
    private val movements: List<Board>,
    moveValidators: List<Validator>,
    private val rules: Map<Piece, Validator>,
    private var currentPlayer: PieceColor = PieceColor.WHITE,
    private val endGameValidators: List<endGameValidator>,
    private val movementExecutioner: MovementExecutioner,
    private val turnManager: TurnManager,
) {
    private val gameValidators: List<Validator> = moveValidators

    fun move(from : Coordinate, to: Coordinate): GameResult {
        if (board.getSquareContent(from) == null){
            return InvalidGameResult("A piece must be selected")
        }
        val movement = Movement(from, to)
        when(val movementResult = validateMovement(movement)){
            is InvalidMovementResult->{
                return InvalidGameResult(movementResult.getMessage())
            }
            else->{
                val newGame = movementExecutioner.getNewGame(movement,this)
                if (newGame.checkIfEndGame()){
                    return GameOverGameResult(currentPlayer)
                }
                return ValidGameResult(newGame)
            }
        }

    }
    fun validateMovement(movement: Movement):MovementResult{
        val gameValidatorResult = validateGameValidators(movement)
        val pieceValidatorResult = validatePieceRule(movement)
        return when {
            gameValidatorResult is InvalidMovementResult -> gameValidatorResult
            pieceValidatorResult is InvalidMovementResult -> pieceValidatorResult
            else -> ValidMovementResult()
        }
    }
    fun validateGameValidators(movement: Movement):MovementResult{
        for (validator in gameValidators){
            if (validator.validateMovement(movement, this) !is ValidMovementResult){
                return validator.validateMovement(movement, this)
            }
        }
        return ValidMovementResult()
    }
    fun validatePieceRule(movement: Movement):MovementResult{
        val piece = this.board.getSquareContent(movement.getFrom())
        if (piece==null){

        }
        val pieceRule = rules[piece]
        return pieceRule?.validateMovement(movement,this)!!

    }
    fun getBoard(): Board {
        return board
    }
    public fun oppositePlayer(): PieceColor {
        if (currentPlayer == PieceColor.WHITE){
            return PieceColor.BLACK
        }
        return PieceColor.WHITE
    }
    public fun checkIfEndGame():Boolean{
        for (checkMateValidator in endGameValidators){
            if (checkMateValidator.validateEndGame(this)){
                return true
            }
        }
        return false
    }
    fun getCurrentPlayer(): PieceColor {
        return currentPlayer
    }

    fun getMovements(): List<Board>{
        return this.movements
    }
    fun getValidators():List<Validator>{
        return this.gameValidators
    }
    fun getRules(): Map<Piece, Validator>{
        return this.rules
    }
    fun getCheckMateValidators(): List<endGameValidator>{
        return this.endGameValidators
    }
    fun getMovementExecutioner():MovementExecutioner{
        return this.movementExecutioner
    }
    fun getTurnManager():TurnManager{
        return this.turnManager
    }

    public fun copy(board: Board = this.board, movements: List<Board> = this.movements,
                    moveValidators: List<Validator> = this.gameValidators, rules: Map<Piece,
                Validator> = this.rules, currentPlayer: PieceColor = this.currentPlayer,
                    endGameValidators: List<endGameValidator> = this.endGameValidators,
                    movementExecutioner: MovementExecutioner = this.movementExecutioner, turnManager: TurnManager = this.turnManager): Game {
        return Game(board, movements, moveValidators, rules, currentPlayer, endGameValidators, movementExecutioner, turnManager)
    }

}
