import Minimizers.Companion.dichotomy
import kotlin.math.pow

fun main(){
    var testF = {x:Double -> x.pow(3)-x}
    println(dichotomy(-200.0,200.0,testF,0.00001))
}