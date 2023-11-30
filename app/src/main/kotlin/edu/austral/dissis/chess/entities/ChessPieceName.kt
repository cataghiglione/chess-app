package edu.austral.dissis.chess.entities

import edu.austral.dissis.common.EnumName


enum class ChessPieceName : EnumName {
    PAWN{
        override fun getName(): String {
            return "pawn"
        }
    },
    ROOK{
        override fun getName(): String {
            return "rook"
        }
    },
    KNIGHT{
        override fun getName(): String {
            return "knight"
        }
    },
    BISHOP{
        override fun getName(): String {
            return "bishop"
        }
    },
    QUEEN{
        override fun getName(): String {
            return "queen"
        }
    },
    KING{
        override fun getName(): String {
            return "king"
        }
    },
    ARCHBISHOP{
        override fun getName(): String {
            return "archbishop"
        }
    },
    CHANCELLOR{
        override fun getName(): String {
            return "chancellor"
        }
    }
}