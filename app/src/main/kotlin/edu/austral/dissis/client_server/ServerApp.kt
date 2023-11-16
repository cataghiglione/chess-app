package edu.austral.dissis.client_server

import edu.austral.dissis.checkers.factory.ClassicCheckersGame
import edu.austral.dissis.chess.factory.ClassicChessGame

fun main(){
    val classicChessGame = ClassicChessGame()
    val classicCheckersGame = ClassicCheckersGame()
//    Server(classicChessGame.createClassicChessGame())
    Server(classicCheckersGame.createClassicCheckersGame())
}