package edu.austral.dissis.client_server

import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move

class GameMoveEventListener(val client: Client) : GameEventListener {
    override fun handleMove(move: Move) {
        client.sendMove(move)
    }
}