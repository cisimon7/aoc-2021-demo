package day8

import readInput
import runWithTime

typealias LString = List<String>

fun main() {
    fun part1(input: List<Pair<LString, LString>>): Int {

        val uniqueSegmentCounts = listOf(2, 4, 3, 7)

        val uniqueOutputs = input.map { (_, output) ->
            output.count { it.count() in uniqueSegmentCounts }
        }

        return uniqueOutputs.sum()
    }

    fun part2(input: List<Pair<LString, LString>>): Int {

        val uniqueSegmentCounts = listOf(2, 4, 3, 7)

        val mappers = input.map { (signals, _) ->
            val uniqueSegmentsMap = signals.filter { it.count() in uniqueSegmentCounts }.associateBy { it.count() }
            val (one, four, seven, eight) = with(uniqueSegmentsMap) {
                listOf(getValue(2), getValue(4), getValue(3), getValue(7))
            }
            val left = signals - uniqueSegmentsMap.values
            val twoThreeFive = left.filter { it.count() == 5 }
            val zeroSixNine = left.filter { it.count() == 6 }

            val six = zeroSixNine.first { num -> !seven.all { it in num } }
            val three = twoThreeFive.first { num -> one.all { it in num } }
            val nine = zeroSixNine.first { num -> four.all { it in num } }
            val zero = (zeroSixNine - nine - six).first()

            val c = eight.first { it !in six }
            val two = (twoThreeFive - three).first { c in it }
            val five = (twoThreeFive - three - two).first()

            patternMapper(zero, one, two, three, four, five, six, seven, eight, nine)
        }

        return input.map { (_, output) -> output }.zip(mappers)
            .sumOf { (output, mapper) ->
                output.map { it.alphaSort() }
                    .map { mapper.getValue(it) }
                    .joinToString("")
                    .toInt()
            }
    }

    val testInput = parse(readInput("day8/input_test.txt"))
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = parse(readInput("day8/input.txt"))
    runWithTime { part1(input) }
    runWithTime { part2(input) }
}


fun patternMapper(
    zero: String,
    one: String,
    two: String,
    three: String,
    four: String,
    five: String,
    six: String,
    seven: String,
    eight: String,
    nine: String
): Map<String, Int> {
    return mapOf(
        zero.alphaSort() to 0,
        one.alphaSort() to 1,
        two.alphaSort() to 2,
        three.alphaSort() to 3,
        four.alphaSort() to 4,
        five.alphaSort() to 5,
        six.alphaSort() to 6,
        seven.alphaSort() to 7,
        eight.alphaSort() to 8,
        nine.alphaSort() to 9
    )
}

fun String.alphaSort(): String {
    return this.toCharArray().sorted().joinToString("")
}

fun parse(input: List<String>): List<Pair<LString, LString>> {
    return input.map { line ->
        line.split("|")
            .map { signal ->
                signal.split(" ").filter { it.isNotBlank() }
            }
    }.map { Pair(it[0], it[1]) }
}