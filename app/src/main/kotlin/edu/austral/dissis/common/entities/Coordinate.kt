package edu.austral.dissis.common.entities

import java.util.*


class Coordinate(column: Int, row: Int) {
    val xCoordinate: Int = column
    val yCoordinate: Int = row

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val position = o as Coordinate
        return xCoordinate == position.xCoordinate && yCoordinate == position.yCoordinate
    }

    override fun hashCode(): Int {
        return Objects.hash(xCoordinate, yCoordinate)
    }
}
