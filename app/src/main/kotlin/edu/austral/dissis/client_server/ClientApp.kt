package edu.austral.dissis.client_server

import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

fun main() {
    Application.launch(GameUi::class.java)
}

class GameUi : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val ChessTitle = "Chess"
        const val CheckersTitle = "Checkers"
        val client = Client()
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = ChessTitle

        val root = GameView(imageResolver)
        primaryStage.scene = Scene(root)
        client.start(root)
        primaryStage.show()
    }
}