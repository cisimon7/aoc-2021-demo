package day1

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        return input.map { it.toInt() }
            .asSequence()
            .windowed(2)
            .sumOf { pair -> if (pair[1] > pair[0]) 1L else 0L }
    }

    fun part2(input: List<String>): Long {
        return input.map { it.toInt() }
            .asSequence()
            .windowed(3)
            .map { it.sum() }
            .windowed(2)
            .sumOf { pair -> if (pair[1] > pair[0]) 1L else 0L }
    }

    val input = readInput("day1/day1.txt")
    println(part1(input))
    println(part2(input))
}
