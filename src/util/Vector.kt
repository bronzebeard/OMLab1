package util

import kotlin.math.pow
import kotlin.math.sqrt

class Vector(coords: List<Double>) {
    var arity: Int = coords.size
    var coords: List<Double> = coords
        set(value) {
            arity = value.size
            field = value
        }

    operator fun plus(input: Vector): Vector {
        return Vector(coords.zip(input.coords, Double::plus))
    }

    operator fun times(input: Double): Vector {
        return Vector(coords.map { it * input })
    }

    operator fun div(input: Double) = times(1 / input)
    fun len(): Double {
        return sqrt(coords.fold(0.0) { sum, element -> sum + element.pow(2) })
    }

    operator fun minus(input: Vector) = this + (input * -1.0)
}