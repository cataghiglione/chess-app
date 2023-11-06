package edu.austral.dissis.common.entities

import edu.austral.dissis.common.gameResults.GameOverGameResult
import edu.austral.dissis.common.gameResults.InvalidGameResult
import edu.austral.dissis.common.gameResults.ValidGameResult
import edu.austral.dissis.common.interfaces.CheckMateValidatorInterface
import edu.austral.dissis.common.interfaces.GameResult
import edu.austral.dissis.common.interfaces.MovementResult
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.movementResults.InvalidMovementResult
import edu.austral.dissis.common.movementResults.ValidMovementResult


class Game(
    private val board: Board,
    private val movements: List<Board>,
    moveValidators: List<Validator>,
    private val rules: Map<Piece, Validator>,
    private var currentPlayer: PieceColor = PieceColor.WHITE,
    private val checkMateValidators: List<CheckMateValidatorInterface>
) {
    private val gameValidators: List<Validator> = moveValidators

    fun move(from : Coordinate, to: Coordinate): GameResult {
        val movement = Movement(from, to)
        when(val movementResult = validateMovement(movement)){
            is InvalidMovementResult->{
                return InvalidGameResult(movementResult.getMessage())
            }
            else->{
                val newBoards = movements.toList() + board
                val newGame = Game(
                    board.move(movement),
                    newBoards,
                    gameValidators,
                    rules,
                    currentPlayer = oppositePlayer(),
                    checkMateValidators
                )
                if (newGame.checkIfCheckMate()){
                    return GameOverGameResult()
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
    public fun checkIfCheckMate():Boolean{
        for (checkMateValidator in checkMateValidators){
            if (checkMateValidator.validateCheckMate(this)){
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
    fun getCheckMateValidators(): List<CheckMateValidatorInterface>{
        return this.checkMateValidators
    }

}
