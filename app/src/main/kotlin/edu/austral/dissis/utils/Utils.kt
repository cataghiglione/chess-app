package edu.austral.dissis.utils

import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position
import edu.austral.dissis.common.entities.Board
import edu.austral.dissis.common.entities.Game
import edu.austral.dissis.common.entities.Piece
import edu.austral.dissis.common.entities.PieceColor
import edu.austral.dissis.common.interfaces.Validator
import edu.austral.dissis.common.validators.AndValidator
import edu.austral.dissis.common.validators.OrValidator

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
            val piecePosition = key?.let { Position(it.row, key.column) }
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

fun updateRules(game: Game, oldPiece: Piece, newPiece: Piece, newValidator: Validator): Map<Piece, Validator> {
    val newRules = game.getRules().toMutableMap()
    newRules.remove(oldPiece)
    newRules[newPiece] = newValidator
    return newRules
}
fun or(vararg validators: Validator): Validator {
    return OrValidator(validators.toList())
}

fun and(vararg validators: Validator): Validator {
    return AndValidator(validators.toList())
}
