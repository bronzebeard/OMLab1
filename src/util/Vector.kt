package util

class Vector(coords:List<Double>) {
    var arity:Int=coords.size
    var coords:List<Double> = coords
    set(value) {
        arity=value.size
        field=value
    }
    operator fun plus(input:Vector):Vector {
        return Vector(coords.zip(input.coords,Double::plus))
    }
    operator fun times(input:Int):Vector {
        return Vector(coords.map{it * input})
    }
}