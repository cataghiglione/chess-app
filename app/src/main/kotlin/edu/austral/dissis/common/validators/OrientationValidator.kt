package edu.austral.dissis.common.validators

import edu.austral.dissis.common.entities.Movement
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.interfaces.Validator

class OrientationValidator(orientation: Boolean) : Validator {
    private val northOrientation: Boolean = orientation

    override fun validateMovement(movement: Movement, game: Game): Boolean {
        return if (northOrientation) {
            movement.getTo().yCoordinate > movement.getFrom().yCoordinate;

        } else
            movement.getFrom().yCoordinate > movement.getTo().yCoordinate;
    }
}