package edu.austral.dissis.client_server

import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.ServerConnectionListener

class ServerConnectionListener(private val server: Server):ServerConnectionListener {
    override fun handleClientConnection(clientId: String) {
        server.getServer().broadcast(Message("connection",server.getGame()))
    }

    override fun handleClientConnectionClosed(clientId: String) {
        server.getServer().broadcast(Message("disconnection","disconnection"))
    }
}