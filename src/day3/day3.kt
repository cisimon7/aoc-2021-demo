package day3

import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun main() {

    fun part1(input: List<String>): Map<String, String> {

        val spliced = input.map { it.asSequence().toList() }

        val transposed = List(spliced.first().size) { idx ->
            spliced.map { it[idx] }
        }

        val gamma = transposed.toList().map { bits ->
            bits.groupingBy { bit -> bit == '1' }
                .eachCount()
                .mapKeys { entry -> if (entry.key) 1 else 0 }
                .maxByOrNull { it.value }
                ?.key ?: 0
        }

        val epsilon = gamma.map { if (it == 1) 0 else 1 }

        val strGamma = gamma.joinToString("")
        val strEps = epsilon.joinToString("")

        return mapOf(
            "Gamma" to strGamma,
            "Epsilon" to strEps,
            "Gamma in decimal" to strGamma.toInt(2).toString(),
            "Epsilon in decimal" to strEps.toInt(2).toString(),
            "Gamma x Epsilon" to (strGamma.toInt(2) * strEps.toInt(2)).toString()
        )
    }

    fun part2(input: List<String>): Int {
        val oRating = 0
        val cRating = 0
        return input.size
    }

    val testInput = readInput("day3/day3_test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day3/day3.txt")
    val (result1, time1) = measureTimedValue { part1(input) }
    println(mapOf("Time (ms)" to time1.toString()) + result1)

//    val (result2, time2) = measureTimedValue { part2(input) }
//    println(json.encodeToString(mapOf("Time (ms)" to time2.inWholeMilliseconds.toInt()) + result2))
}