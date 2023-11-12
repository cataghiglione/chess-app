package edu.austral.dissis.client_server

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.chess.gui.NewGameState
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

class Server( private val game: Game,
              private val builder: ServerBuilder=NettyServerBuilder.createDefault()) {
    private val server:Server
    init {
        server = buildServer()
        server.start()
    }
    private fun buildServer():Server{
        return builder
            .withPort(8080)
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
                server.broadcast(Message("new-game-state", NewGameState(uiPieces(result.getGame().getBoard()), getCurrentPlayerColor(result.getGame().getCurrentPlayer()))))
            }
            is InvalidGameResult->{
                server.broadcast(Message("invalid-move",InvalidMove(result.getMessage())))
            }
            is GameOverGameResult->{
                server.broadcast(Message("game-over",GameOver(result.getWinner())))
            }
        }
    }
    fun getGame():Game{
        TODO()
    }
    fun getServer():Server{
        return this.server
    }




}