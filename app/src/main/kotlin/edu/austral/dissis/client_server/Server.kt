package edu.austral.dissis.client_server

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.common.entities.Coordinate
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.gameResults.GameOverGameResult
import edu.austral.dissis.common.gameResults.InvalidGameResult
import edu.austral.dissis.common.gameResults.ValidGameResult
import edu.austral.dissis.utils.getCurrentPlayerColor
import edu.austral.dissis.utils.uiPieces
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.ServerBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

class Server( private var game: Game,
              private val builder: ServerBuilder=NettyServerBuilder.createDefault()) {
    private val server:Server
    init {
        server = buildServer()
        server.start()
    }
    private fun buildServer():Server{
        return builder
            .withPort(8080)
            .withConnectionListener(ServerConnectionListener(this))
            .addMessageListener("move",
                object : TypeReference<Message<Move>> (){},
                GameMoveListener(this))
            .build()

    }
    fun handleMovement(movement: Move){
        val from = Coordinate(movement.from.column,movement.from.row)
        val to = Coordinate(movement.to.column,movement.to.row)

        when(val result = game.move(from,to)){
            is ValidGameResult->{
                val newGame = result.getGame()
                game = newGame
                server.broadcast(Message("new-game-state", NewGameState(uiPieces(newGame.getBoard()), getCurrentPlayerColor(newGame.getCurrentPlayer()))))
            }
            is InvalidGameResult->{
                server.broadcast(Message("invalid-move",InvalidMove(result.getMessage())))
            }
            is GameOverGameResult->{
                server.broadcast(Message("game-over",GameOver(getCurrentPlayerColor( result.getGameWinner()))))
            }
        }
    }
    fun getGame():Game{
        return this.game
    }
    fun getServer():Server{
        return this.server
    }

    fun handleClientConnection(clientId: String) {
        server.sendMessage(clientId,Message("initial-state",getInitialState()))
    }

    private fun getInitialState():InitialState{
        val boardSize = BoardSize(game.getBoard().getXDimension(),game.getBoard().getYDimension())
        return InitialState(boardSize, uiPieces(game.getBoard()), getCurrentPlayerColor(game.getCurrentPlayer()))
    }


}