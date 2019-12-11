import util.Vector
import kotlin.math.pow
import util.times

typealias VecFun = (Vector) -> Double
typealias Fun = (Double) -> Double

const val FI = 1.6180339887498948482
const val SQR5 = 2.23606797749978969

class Minimizers {
    companion object {
        //private val FI = 1.618033988749
        private fun dichotomyImpl(a: Vector, b: Vector, func: VecFun, dir: Vector, eps: Double, cnt: Int): Vector {
            val mid = (a + b) / 2.0
            val tmp = dir * eps
            val fLeft = func(mid - tmp)
            val fRight = func(mid + tmp)
            return when {
                (b - a).len < eps -> {
                    println("Dichtomy method iterations: $cnt")
                    mid
                }
                fLeft < fRight -> dichotomyImpl(a, mid, func, dir, eps, cnt + 1)
                else -> dichotomyImpl(mid, b, func, dir, eps, cnt + 1)
            }
        }

        private fun goldenImpl(a: Vector, b: Vector, func: VecFun, dir: Vector, eps: Double, cnt: Int): Vector {
            val tmp = (b - a) / FI
            val x1 = b - tmp
            val x2 = a + tmp
            val y1 = func(x1)
            val y2 = func(x2)
            return when {
                (b - a).len < eps -> {
                    println("Golden method iterations: $cnt")
                    (a + b) / 2.0
                }
                y1 >= y2 -> goldenImpl(x1, b, func, dir, eps, cnt + 1)
                else -> goldenImpl(a, x2, func, dir, eps, cnt + 1)
            }
        }

        private fun fibImpl(a: Vector, b: Vector, func: VecFun, dir: Vector, eps: Double, n: Int): Vector {

            var x1 = a + (b - a) * (getFib(n - 2) / getFib(n))
            var x2 = a + (b - a) * (getFib(n - 1) / getFib(n))
            var y1 = func(x1)
            var y2 = func(x2)
            return when {
                (b - a).len < eps -> (a + b) / 2.0
                y1 > y2 -> fibImpl(x1, b, func, dir, eps, n - 1)
                else -> fibImpl(a, x2, func, dir, eps, n - 1)
            }
        }

        fun golden(x1: Double, x2: Double, func: Fun, eps: Double): Double {
            val x1v = Vector(listOf(x1))
            val x2v = Vector(listOf(x2))
            val funcV = { vec: Vector -> func(vec.coords[0]) }
            return goldenImpl(x1v, x2v, funcV, Vector(listOf(1.0)), eps, 0).coords[0]
        }

        fun fib(x1: Double, x2: Double, func: Fun, eps: Double): Double {
            val x1v = Vector(listOf(x1))
            val x2v = Vector(listOf(x2))
            val funcV = { vec: Vector -> func(vec.coords[0]) }
            val n = getN(x1v, x2v, eps)
            println("Fibonacci method iterations: " + (n - 2))
            return fibImpl(x1v, x2v, funcV, Vector(listOf(1.0)), eps, n).coords[0]
        }

        fun dichotomy(x1: Double, x2: Double, func: Fun, eps: Double): Double {
            val x1v = Vector(listOf(x1))
            val x2v = Vector(listOf(x2))
            val funcV = { vec: Vector -> func(vec.coords[0]) }
            return dichotomyImpl(x1v, x2v, funcV, Vector(listOf(1.0)), eps, 0).coords[0]
        }

        private fun getFib(n: Int): Double {
            return 1.0 / SQR5 * (((1.0 + SQR5) / 2).pow(n) - ((1.0 - SQR5) / 2).pow(n))
        }

        fun vecMin(start: Vector, dir: Vector, func: VecFun, eps: Double): Vector {
            val step: Double = if (func(start) > func(start + eps * (dir))) {
                eps
            } else -eps
            var x = start
            while (func(x)>func(x+step*dir)) {
                x += step * dir
            }
            return x
        }

        fun lineMin(start: Double, func: Fun, eps: Double):Double {
            val startV = Vector(listOf(start))
            val dir = Vector(listOf(1.0))
            val funcV = {inp:Vector -> func(inp.coords[0])}
            return vecMin(startV,dir,funcV,eps).coords[0]
        }

        private fun getN(a: Vector, b: Vector, eps: Double): Int {
            var i = 0
            var len = (b - a).len / getFib(i)
            while (len >= eps) {
                i++
                len = (b - a).len / getFib(i)
            }
            return i
        }

    }
}