package edu.austral.dissis.common.entities

import edu.austral.dissis.chess.entities.ChessPieceName
import edu.austral.dissis.common.EnumName
import java.util.*

data class Piece(private val chessPieceName: EnumName, private val color: PieceColor, private var id: Int = 0) {

    fun getName(): EnumName {
        return chessPieceName
    }

    fun getColor(): PieceColor {
        return color
    }

    fun getId(): Int {
        if (id == 0) {
            id = hashCode()
        }
        return id
    }
}
