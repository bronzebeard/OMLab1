import Minimizers.Companion.dichotomy
import Minimizers.Companion.fib
import Minimizers.Companion.golden
import Minimizers.Companion.lineMin
import Minimizers.Companion.minimizeNDim
import util.Vector
import kotlin.math.pow
const val A = 0.0
const val B = 1.0
const val EPS = 0.0000001
fun main() {
    val testF = { x: Double -> x.pow(3) - x }
    /*println(dichotomy(A, B, testF, EPS))
    println(golden(A, B, testF, EPS))
    println(fib(A, B, testF, EPS))*/
    //println(lineMin(0.0,testF,0.01))
    val vec = Vector(listOf(1.0,5.0))
    val vecF = {x:Vector -> 100*(x.coords[1]-x.coords[0].pow(2)).pow(2)+(1-x.coords[0]).pow(2)}
    for (i in 1..7) {
        println(minimizeNDim(vec,vecF,10.0.pow(-i)))
        println("-----------")
    }
    //println(minimizeNDim(vec,vecF,0.00001))

}