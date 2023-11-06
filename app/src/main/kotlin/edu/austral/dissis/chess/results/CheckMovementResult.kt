package edu.austral.dissis.chess.results

import edu.austral.dissis.common.interfaces.MovementResult

class CheckMovementResult : MovementResult {
    override fun getMessage(): String {
        return "You are in check"
    }
}
