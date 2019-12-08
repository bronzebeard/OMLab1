import util.Vector

typealias VecFun = (Vector) -> Double
typealias Fun = (Double) -> Double

class Minimizers {
    companion object {
        private fun dichotomyImpl(x1: Vector, x2: Vector, func: VecFun, dir: Vector, eps: Double): Vector {
            val mid = (x1 + x2) / 2.0
            if ((x2 - x1).len() < eps) return mid
            val fLeft = func(mid - dir * eps)
            val fRight = func(mid + dir * eps)
            return if (fLeft < fRight) dichotomyImpl(x1, mid, func, dir, eps)
            else dichotomyImpl(mid, x2, func, dir, eps)
        }

        fun dichotomy(x1: Double, x2: Double, func: Fun, eps: Double): Double {
            val x1v = Vector(listOf(x1))
            val x2v = Vector(listOf(x2))
            val funcV = { vec: Vector -> func(vec.coords[0]) }
            return dichotomyImpl(x1v, x2v, funcV, Vector(listOf(1.0)), eps).coords[0]
        }
    }
}