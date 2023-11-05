package edu.austral.dissis.chess.orientationValidators

import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.interfaces.Validator
import kotlin.math.abs

class LMovementValidator : Validator {
    override fun validateMovement(movement: Movement, game: Game): Boolean {
        val xDistance = abs(movement.getFrom().xCoordinate - movement.getTo().xCoordinate)
        val yDistance = abs(movement.getFrom().yCoordinate - movement.getTo().yCoordinate)
        return (xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1)
    }
}