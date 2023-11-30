package edu.austral.dissis.common.entities

import java.util.*


class Coordinate(column: Int, row: Int) {
    val column: Int = column
    val row: Int = row

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val position = o as Coordinate
        return column == position.column && row == position.row
    }

    override fun hashCode(): Int {
        return Objects.hash(column, row)
    }
}
