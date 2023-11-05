package edu.austral.dissis.checkers.entities

import edu.austral.dissis.common.EnumName

enum class CheckersPieceName:EnumName {
    PAWN{
        override fun getName(): String {
            return "pawn"
        }
    },
    KING{
        override fun getName(): String {
            return "king"
        }
    }
}