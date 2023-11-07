package edu.austral.dissis.common.movementResults

import edu.austral.dissis.common.interfaces.MovementResult

class InvalidMovementResult(private val message: String) : MovementResult {
    override fun getMessage(): String {
        return message
    }
}