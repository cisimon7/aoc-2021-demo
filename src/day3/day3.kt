package day3

import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

typealias CharSeq = List<Char>
typealias MapGroup = Map.Entry<Boolean, Int>

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

    fun part2(input: List<String>): Map<String, String> {
        val spliced: List<List<Char>> = input.map { it.asSequence().toList() }

        val oxygenSeq = buildSeq(spliced) { acc, entry ->
            when {
                acc.value > entry.value -> acc
                acc.value < entry.value -> entry
                else -> if (acc.key) acc else entry
            }
        }

        val carbonSeq = buildSeq(spliced) { acc, entry ->
            when {
                acc.value > entry.value -> entry
                acc.value < entry.value -> acc
                else -> if (acc.key) entry else acc
            }
        }

        val oRating = oxygenSeq.takeWhile { !it.second }.last().third.first()
        val cRating = carbonSeq.takeWhile { !it.second }.last().third.first()

        val oDec = oRating.joinToString("").toInt(2)
        val cDec = cRating.joinToString("").toInt(2)

        return mapOf(
            "Oxygen Rating" to oRating.joinToString(""),
            "Carbon Rating" to cRating.joinToString(""),
            "Oxygen Rating (dec)" to oDec.toString(),
            "Carbon Rating (dec)" to cDec.toString(),
            "Product" to (oDec * cDec).toString()
        )
    }

    val testInput = readInput("day3/day3_test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day3/day3.txt")
    val (result1, time1) = measureTimedValue { part1(input) }
    println(mapOf("Time (ms)" to time1.toString()) + result1)

    val (result2, time2) = measureTimedValue { part2(input) }
    println(mapOf("Time (ms)" to time2.toString()) + result2)
}

fun buildSeq(
    list: List<CharSeq>,
    func: (MapGroup, MapGroup) -> MapGroup
): Sequence<Triple<Int, Boolean, List<CharSeq>>> {
    val size = list.first().size

    return generateSequence(Triple(0, false, list)) { (step, end, prevList) ->
        val bits = List(size) { idx ->
            prevList.map { it[idx] }
        }[step % size]

        val mode = bits
            .groupingBy { bit -> bit == '1' }
            .eachCount()
            .entries
            .reduce { acc, entry -> func(acc, entry) }
            .let { if (it.key) '1' else '0' }

        val next = prevList.zip(bits).filter { (_, snd) -> snd == mode }.map { it.first }

        Triple(step + 1, next.size == 1 && prevList == next, next)
    }
}