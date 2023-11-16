package edu.austral.dissis.client_server

import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class InvalidMoveClientListener(private val client: Client):MessageListener<InvalidMove> {
    override fun handleMessage(message: Message<InvalidMove>) {
        return client.handleMoveResult(message.payload)
    }
}