package edu.austral.dissis.client_server

import edu.austral.dissis.chess.gui.GameOver
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class GameOverClientListener(private val client: Client) : MessageListener<GameOver> {
    override fun handleMessage(message: Message<GameOver>) {
        client.handleMoveResult(message.payload)
    }
}