package util

import kotlin.math.pow
import kotlin.math.sqrt

data class Vector(val coords: List<Double>) {

    /*var arity: Int = coords.size
    var coords: List<Double> = coords
        set(value) {
            arity = value.size
            field = value
        }*/
    val arity: Int
        get() = coords.size
    val len: Double
        get() = sqrt(coords.fold(0.0) { sum, element -> sum + element.pow(2) })

    fun project(i:Int):Vector {
        val tmp=ArrayList<Double>()
        coords.forEachIndexed{idx,it -> if (idx==i) tmp.add(it) else tmp.add(0.0)}
        return Vector(tmp)
    }

    operator fun plus(input: Vector) = Vector(coords.zip(input.coords, Double::plus))

    operator fun times(input: Double) = Vector(coords.map { it * input })

    operator fun div(input: Double) = times(1 / input)

    operator fun minus(input: Vector) = this + (input * -1.0)


}

operator fun Double.times(input: Vector) = input * this