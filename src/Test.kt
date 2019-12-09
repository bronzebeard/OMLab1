import Minimizers.Companion.dichotomy
import Minimizers.Companion.fib
import Minimizers.Companion.golden
import kotlin.math.pow

fun main(){
    var testF = {x:Double -> x.pow(3)-x}
    println(dichotomy(0.0,200.0,testF,0.00001))
    println(golden(0.0,200.0,testF,0.0000001))
    println(fib(0.0,200.0,testF,0.0000001))
}