package edu.austral.dissis.client_server

import edu.austral.dissis.chess.gui.InitialState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class ClientInitialStateListener(private val client: Client) :MessageListener<InitialState> {
    override fun handleMessage(message: Message<InitialState>) {
        println("Received initial state")
        return client.handleInitialState(message)
    }
}