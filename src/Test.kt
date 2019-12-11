import Minimizers.Companion.dichotomy
import Minimizers.Companion.fib
import Minimizers.Companion.golden
import Minimizers.Companion.lineMin
import kotlin.math.pow

fun main() {
    var testF = { x: Double -> x.pow(3) - x }
    println(dichotomy(0.0, 1.0, testF, 0.0000001))
    println(golden(0.0, 1.0, testF, 0.0000001))
    println(fib(0.0, 1.0, testF, 0.0000001))
    println(lineMin(0.0,testF,0.0000001))
}