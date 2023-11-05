package edu.austral.dissis.common.entities

class Movement(private val from: Coordinate, private val to: Coordinate) {
    fun getFrom(): Coordinate {
        return from
    }

    fun getTo(): Coordinate {
        return to
    }

}