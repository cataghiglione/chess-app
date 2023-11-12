package edu.austral.dissis.client_server

import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.common.entities.Movement
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class GameMoveListener(private val server: Server):MessageListener<Move> {

    override fun handleMessage(message: Message<Move>) {
        server.handleMovement(message.payload)
    }
}