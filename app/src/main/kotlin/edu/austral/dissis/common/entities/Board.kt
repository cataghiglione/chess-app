package edu.austral.dissis.common.entities


data class Board(val board: Map<Coordinate, Piece?>, private val horizontalDimension: Int, private val verticalDimension: Int) {

    fun move(movement: Movement): Board {
        return this.removePiece(movement.getFrom())
            .addPiece(movement.getTo(), this.getSquareContent(movement.getFrom())!!)
    }
    fun getHorizontalDimension(): Int {
        return horizontalDimension
    }
    fun getVerticalDimension(): Int {
        return verticalDimension
    }
    fun getSquareContent(square: Coordinate): Piece? {
        return this.board[square]
    }
     fun getInvertedBoard() : Map<Piece, Coordinate>{
            val invertedBoard: MutableMap<Piece, Coordinate> = HashMap()
            for (entry in board.
            entries) {
                invertedBoard[entry.value!!] = entry.key!!
            }
            return invertedBoard
     }

    fun addPiece(coordinate: Coordinate, piece: Piece): Board {
        return copy(board = board + (coordinate to piece))
    }
    fun replacePiece(movement: Movement, piece: Piece): Board {
        return this.removePiece(movement.getFrom())
            .addPiece(movement.getTo(), piece)
    }
    fun removePiece(coordinate: Coordinate):Board{
        return copy(board = board - coordinate)
    }
}

