package com.example.vinylsmobile

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun sum_isCorrect() {
        val computation: Computation = Computation()
        assertEquals(4, computation.Sum(2, 2))
    }

    @Test
    fun multiply_isCorrect() {
        val computation: Computation = Computation()
        assertEquals(4, computation.Multiply(2, 2))
    }

    class Computation {
        fun Sum(a: Int, b: Int): Int {
            return a + b
        }

        fun Multiply(a: Int, b: Int): Int {
            return a * b
        }
    }
}