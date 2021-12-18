package day7

import readInput
import runWithTime
import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>): Int {

        val max = input.maxOf { it }
        val min = input.minOf { it }

        var minCost = Int.MAX_VALUE

        (min..max).forEach { position ->
            input.sumOf { abs(it - position) }.let { cost ->
                if (cost < minCost) { minCost = cost }
            }
        }

        return minCost
    }

    fun part2(input: List<Int>): Int {

        val max = input.maxOf { it }
        val min = input.minOf { it }

        var minCost = Int.MAX_VALUE

        (min..max).forEach { position ->
            input.sumOf { (0..abs(it - position)).sum() }.let { cost ->
                if (cost < minCost) { minCost = cost }
            }
        }

        return minCost
    }

    val testInput = parse(readInput("day7/input_test.txt"))
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = parse(readInput("day7/input.txt"))
    runWithTime { part1(input) }
    runWithTime { part2(input) }
}

fun parse(input: List<String>): List<Int> {
    return input.map { str ->
        str.split(",").filter { it.isNotBlank() }.map { it.toInt() }
    }.flatten()
}