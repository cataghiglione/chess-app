package edu.austral.dissis.common.entities

import edu.austral.dissis.common.interfaces.ActionResult

class ClassicActionResult(private val actionPerformed: Boolean, private val game: Game ) : ActionResult {
    override fun wasActionPerformed(): Boolean {
        return actionPerformed
    }

    override fun getGameResult(): Game {
        return game
    }


}