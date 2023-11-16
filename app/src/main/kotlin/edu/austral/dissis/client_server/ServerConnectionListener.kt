package edu.austral.dissis.client_server

import edu.austral.ingsis.clientserver.ServerConnectionListener

class ServerConnectionListener(private val server: Server):ServerConnectionListener {
    override fun handleClientConnection(clientId: String) {
        println("Client connected: $clientId")
        server.handleClientConnection(clientId)
    }

    override fun handleClientConnectionClosed(clientId: String) {
        server.getServer().stop()
    }
}