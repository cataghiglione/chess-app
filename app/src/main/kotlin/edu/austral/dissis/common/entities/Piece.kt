package edu.austral.dissis.common.entities

import edu.austral.dissis.chess.entities.ChessPieceName
import java.util.*

data class Piece(private val chessPieceName: ChessPieceName, private val color: PieceColor, private var id: Int =0){

     fun getName(): ChessPieceName {
         return chessPieceName
     }
     fun getColor(): PieceColor {
         return color
     }
    fun getId(): Int {
        if (id == 0){
            id = hashCode()
        }
        return id
    }


     override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Piece) return false

            if (chessPieceName != other.chessPieceName) return false
            if (color != other.color) return false
            if (id != other.id) return false

            return true
     }
    override fun hashCode():Int{
        return Objects.hash(chessPieceName, color,id)
    }



 }
