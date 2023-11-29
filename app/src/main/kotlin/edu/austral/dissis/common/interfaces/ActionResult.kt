package edu.austral.dissis.common.interfaces

import edu.austral.dissis.common.entities.Game

interface ActionResult {
    fun wasActionPerformed(): Boolean
    fun getGameResult(): Game
}