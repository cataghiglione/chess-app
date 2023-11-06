package edu.austral.dissis.common.movementResults

import edu.austral.dissis.common.interfaces.MovementResult

class ValidMovementResult : MovementResult {
    override fun getMessage(): String {
        return "Valid Movement"
    }

}