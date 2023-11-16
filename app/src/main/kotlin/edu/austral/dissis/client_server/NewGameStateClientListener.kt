package edu.austral.dissis.client_server

import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class NewGameStateClientListener(val client: Client) : MessageListener<NewGameState> {
    override fun handleMessage(message: Message<NewGameState>) {
        client.handleMoveResult(message.payload)
    }
}