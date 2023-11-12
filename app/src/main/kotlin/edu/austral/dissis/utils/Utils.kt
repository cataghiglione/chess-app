package edu.austral.dissis.utils

import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position
import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.PieceColor

    fun uiPieces(board: Board): List<ChessPiece> {
        val pieces = ArrayList<ChessPiece>()
        val boardContent = board.board.keys
        for (key in boardContent) {
            val piece = board.board[key]
            if (piece != null) {
                val pieceColor = if (piece.getColor() == PieceColor.WHITE) {
                    PlayerColor.WHITE
                } else {
                    PlayerColor.BLACK
                }
                val pieceChessPiece = piece.getName().getName();
                val piecePosition = key?.let { Position(it.yCoordinate, key.xCoordinate) }
                piecePosition?.let { ChessPiece(piece.getId().toString(), pieceColor, piecePosition, pieceChessPiece) }
                    ?.let { pieces.add(it) }
            }
        }
        return pieces
    }
     fun getCurrentPlayerColor(color: PieceColor): PlayerColor {
         return if (PieceColor.WHITE == color) {
             PlayerColor.WHITE
         } else PlayerColor.BLACK
    }
