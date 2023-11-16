package edu.austral.dissis.client_server

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.*
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.ClientBuilder
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import java.net.InetSocketAddress

class Client(
    private val clientBuilder: ClientBuilder = NettyClientBuilder.createDefault()
) {
    private lateinit var client: Client
    private lateinit var view: GameView

    fun start(gameView: GameView) {
        client = buildClient(gameView)
        client.connect()
    }
    fun buildClient(gameView: GameView): Client {
        this.view = gameView
        view.addListener(GameMoveEventListener(this))
        return clientBuilder
            .withAddress(InetSocketAddress(8080))
            .addMessageListener(
                "new-game-state",
                object : TypeReference<Message<NewGameState>>() {},
                NewGameStateClientListener(this)
            )
            .addMessageListener(
                "invalid-move",
                object : TypeReference<Message<InvalidMove>>() {},
                InvalidMoveClientListener(this)
            )
            .addMessageListener(
                "game-over",
                object : TypeReference<Message<GameOver>>() {},
                GameOverClientListener(this)
            )
            .addMessageListener(
                "initial-state",
                object : TypeReference<Message<InitialState>>() {},
                ClientInitialStateListener(this)
            )

            .build()
    }

    fun handleMoveResult(moveResult: MoveResult) {
        view.handleMoveResult(moveResult)
    }

    fun sendMove(move: Move) {
        client.send(Message("move", move))
    }

    fun handleInitialState(message: Message<InitialState>) {
        view.handleInitialState(message.payload)
    }
}