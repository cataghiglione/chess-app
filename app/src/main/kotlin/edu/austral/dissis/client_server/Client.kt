package edu.austral.dissis.client_server

import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.ClientBuilder
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder

class Client(private val clientBuilder: ClientBuilder = NettyClientBuilder.createDefault()) {
    private val client:Client
    init {
        client = buildClient()
    }
    private fun buildClient():Client{
        TODO()
        return clientBuilder.build()
    }
}