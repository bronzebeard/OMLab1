import util.Vector
import kotlin.math.pow
import util.times
import kotlin.math.abs

typealias VecFun = (Vector) -> Double
typealias Fun = (Double) -> Double

const val FI = 1.6180339887498948482
const val SQR5 = 2.23606797749978969

class Minimizers {
    companion object {
        //private val FI = 1.618033988749
        private fun dichotomyImpl(a: Vector, b: Vector, func: VecFun, eps: Double, cnt: Int): Vector {
            val mid = (a + b) / 2.0
            val dir = (b - a) / (b - a).len
            val tmp = dir * eps
            val fLeft = func(mid - tmp)
            val fRight = func(mid + tmp)
            return when {
                (b - a).len < eps -> {
                    //println("Dichtomy method iterations: $cnt")
                    mid
                }
                fLeft < fRight -> dichotomyImpl(a, mid, func, eps, cnt + 1)
                else -> dichotomyImpl(mid, b, func, eps, cnt + 1)
            }
        }

        private fun goldenImpl(a: Vector, b: Vector, func: VecFun, eps: Double, cnt: Int): Vector {
            val tmp = (b - a) / FI
            val x1 = b - tmp
            val x2 = a + tmp
            val y1 = func(x1)
            val y2 = func(x2)
            return when {
                (b - a).len < eps -> {
                    // println("Golden method iterations: $cnt")
                    (a + b) / 2.0
                }
                y1 >= y2 -> goldenImpl(x1, b, func, eps, cnt + 1)
                else -> goldenImpl(a, x2, func, eps, cnt + 1)
            }
        }

        private fun fibImpl(a: Vector, b: Vector, func: VecFun, eps: Double, n: Int): Vector {

            val x1 = a + (b - a) * (getFib(n - 2) / getFib(n))
            val x2 = a + (b - a) * (getFib(n - 1) / getFib(n))
            val y1 = func(x1)
            val y2 = func(x2)
            return when {
                (b - a).len < eps -> (a + b) / 2.0
                y1 > y2 -> fibImpl(x1, b, func, eps, n - 1)
                else -> fibImpl(a, x2, func, eps, n - 1)
            }
        }

        fun golden(x1: Double, x2: Double, func: Fun, eps: Double): Double {
            val x1v = Vector(listOf(x1))
            val x2v = Vector(listOf(x2))
            val funcV = { vec: Vector -> func(vec.coords[0]) }
            return goldenImpl(x1v, x2v, funcV, eps, 0).coords[0]
        }

        fun fib(x1: Double, x2: Double, func: Fun, eps: Double): Double {
            val x1v = Vector(listOf(x1))
            val x2v = Vector(listOf(x2))
            val funcV = { vec: Vector -> func(vec.coords[0]) }
            val n = getN(x1v, x2v, eps)
//            println("Fibonacci method iterations: " + (n - 2))
            return fibImpl(x1v, x2v, funcV, eps, n).coords[0]
        }

        fun dichotomy(x1: Double, x2: Double, func: Fun, eps: Double): Double {
            val x1v = Vector(listOf(x1))
            val x2v = Vector(listOf(x2))
            val funcV = { vec: Vector -> func(vec.coords[0]) }
            return dichotomyImpl(x1v, x2v, funcV, eps, 0).coords[0]
        }

        private fun getFib(n: Int): Double {
            return 1.0 / SQR5 * (((1.0 + SQR5) / 2).pow(n) - ((1.0 - SQR5) / 2).pow(n))
        }

        fun vecMin(start: Vector, dir: Vector, func: VecFun, eps: Double): Vector {
            val step: Double = if (func(start) > func(start + eps * (dir))) {
                eps
            } else -eps
            var x = start
            do {
                val f1 = func(x)
                val f2 = func(x + step * dir)
                x += step * dir
            } while (f1 > f2)
            /*while (func(x) > func(x + step * dir)) {
                x += step * dir
            }*/
            return x
        }

        fun lineMin(start: Double, func: Fun, eps: Double): Double {
            val startV = Vector(listOf(start))
            val dir = Vector(listOf(1.0))
            val funcV = { inp: Vector -> func(inp.coords[0]) }
            return vecMin(startV, dir, funcV, eps).coords[0]
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

        fun minimizeNDim(start: Vector, func: VecFun, eps: Double): Vector {
            var xNew = start
            var xCur = xNew
            var i = 0
            do {
                xCur = xNew
                val grad = getGrad(xCur, func, eps)
                val tmp1 = vecMin(xCur, grad, func, eps)
                val tmp = dichotomyImpl(xCur, tmp1, func, eps, 0)
                val lambda = abs((tmp - xCur).coords[0] / grad.coords[0])
                xNew = xCur - lambda * grad
                val diff = func(xNew) - func(xCur)
                i++
            } while (abs(diff) >= eps)
            /*println(func(xNew))
            println(func(xCur))
            println("Iterations: $i")*/
            return xNew
        }

        fun getGrad(start: Vector, func: VecFun, delta: Double): Vector {
            var grad = ArrayList<Double>(start.arity)

            for (i in 0 until start.arity) {
                val dx = delta * start.project(i)
                val diff = (func(start + dx) - func(start))
                grad.add(diff / (dx.coords[i]))
            }
            var tmp = Vector(grad)
            tmp /= tmp.len
            return tmp
        }
    }
}