package edu.austral.dissis.common.entities

import edu.austral.dissis.common.interfaces.CheckMateValidatorInterface
import edu.austral.dissis.common.interfaces.Validator


class Game(
    private val board: Board,
    private val movements: List<Board>,
    moveValidators: List<Validator>,
    private val rules: Map<Piece, Validator>,
    private var currentPlayer: PieceColor = PieceColor.WHITE,
    private val checkMateValidators: List<CheckMateValidatorInterface>
) {
    private val gameValidators: List<Validator> = moveValidators

    fun move(from : Coordinate, to: Coordinate): Game {
        val movement = Movement(from, to)
        if (validateMovement(movement)){
            val newBoard = this.board.move(movement)
            val auxMovements :List<Board> = movements.toList()
            val newBoards :List<Board> = auxMovements + board

            return Game(newBoard,newBoards,this.gameValidators,this.rules, oppositePlayer(), this.checkMateValidators)
        }
        throw Exception("Invalid move")
    }
    fun validateMovement(movement: Movement):Boolean{
        return validateGameValidators(movement) && validatePieceRule(movement)
    }
    fun validateGameValidators(movement: Movement):Boolean{
        for (validator in gameValidators){
            if (!validator.validateMovement(movement, this)){
                return false
            }
        }
        return true
    }
    fun validatePieceRule(movement: Movement):Boolean{
        val piece = this.board.getSquareContent(movement.getFrom())
        val pieceRule = rules[piece]
        if (pieceRule!=null){
            return pieceRule.validateMovement(movement, this)
        }
        return false
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
