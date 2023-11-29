package edu.austral.dissis.client_server

import edu.austral.dissis.checkers.factory.ClassicCheckersGame
import edu.austral.dissis.chess.factory.ClassicChessGame

fun main(){
    val chessGame = ClassicChessGame()
    val classicCheckersGame = ClassicCheckersGame()
    Server(chessGame.createClassicChessGame())
//    Server(classicCheckersGame.createClassicCheckersGame())
}