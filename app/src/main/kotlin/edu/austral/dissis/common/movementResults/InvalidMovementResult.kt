package edu.austral.dissis.common.movementResults

import edu.austral.dissis.common.interfaces.MovementResult

class InvalidMovementResult : MovementResult {
    override fun getMessage(): String {
        return "Invalid Movement"
    }
}