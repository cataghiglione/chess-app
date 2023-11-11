package edu.austral.dissis.common.entities


class Board(val board: Map<Coordinate?, Piece?>, private val xDimension: Int, private val yDimension: Int) {

    fun move(movement: Movement): Board {
        val auxBoard: MutableMap<Coordinate?, Piece?> = HashMap(board)
        val piece = auxBoard[movement.getFrom()]
        auxBoard.remove(movement.getFrom())
        auxBoard[movement.getTo()] = piece!!.copy(piece.getName(),piece.getColor(),piece.getId())
        val newBoard: HashMap<Coordinate?, Piece?> = HashMap(auxBoard)
        return Board(newBoard, xDimension, yDimension)
    }
    fun getXDimension(): Int {
        return xDimension
    }
    fun getYDimension(): Int {
        return yDimension
    }
    fun getSquareContent(square: Coordinate): Piece? {
        return this.board[square]
    }
     fun getInvertedBoard() : Map<Piece, Coordinate>{
            val invertedBoard: MutableMap<Piece, Coordinate> = HashMap()
            for (entry in board.entries) {
                invertedBoard[entry.value!!] = entry.key!!
            }
            return invertedBoard
     }
    fun replacePiece(movement: Movement, piece: Piece): Board {
        val auxBoard: MutableMap<Coordinate?, Piece?> = HashMap(board)
        auxBoard.remove(movement.getFrom())
        auxBoard[movement.getTo()] = piece
        val newBoard: HashMap<Coordinate?, Piece?> = HashMap(auxBoard)
        return Board(newBoard, xDimension, yDimension)
    }
    fun removePiece(coordinate: Coordinate):Board{
        val auxBoard: MutableMap<Coordinate?, Piece?> = HashMap(board)
        auxBoard.remove(coordinate)
        val newBoard: HashMap<Coordinate?, Piece?> = HashMap(auxBoard)
        return Board(newBoard, xDimension, yDimension)
    }
}

