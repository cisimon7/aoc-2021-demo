package day2

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun main() {

    val json = Json { prettyPrint = true }

    fun part1(input: List<String>): Map<String, Int> {
        var horizontal = 0
        var depth = 0

        input.asSequence()
            .map { it.split(" ") }
            .map { Command.parse(it[0], it[1]) }
            .forEach { command ->
                when (command) {
                    is Command.Forward -> { horizontal += command.units }
                    is Command.Down -> { depth += command.units }
                    is Command.Up -> { depth -= command.units }
                    is Command.None -> {  }
                }
            }

        return mapOf(
            "Horizontal" to horizontal,
            "Depth" to depth,
            "Product" to horizontal * depth
        )
    }

    fun part2(input: List<String>): Map<String, Int> {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.asSequence()
            .map { it.split(" ") }
            .map { Command.parse(it[0], it[1]) }
            .forEach { command ->
                when (command) {
                    is Command.Forward -> {
                        horizontal += command.units
                        depth += (aim * command.units)
                    }
                    is Command.Down -> { aim += command.units }
                    is Command.Up -> { aim -= command.units }
                    is Command.None -> {  }
                }
            }

        return mapOf(
            "Horizontal" to horizontal,
            "Depth" to depth,
            "Aim" to aim,
            "Product" to horizontal * depth
        )
    }

    val testInput = readInput("day2/day2_test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day2/day2.txt")
    val (result1, time1) = measureTimedValue { part1(input) }
    println(json.encodeToString(mapOf("Time (ms)" to time1.inWholeMilliseconds.toInt()) + result1))

    // TODO("Why is this taking way less time")
    val (result2, time2) = measureTimedValue { part2(input) }
    println(json.encodeToString(mapOf("Time (ms)" to time2.inWholeMilliseconds.toInt()) + result2))
}

sealed interface Command {
    @JvmInline
    value class Forward(val units: Int) : Command
    @JvmInline
    value class Down(val units: Int) : Command
    @JvmInline
    value class Up(val units: Int) : Command
    @JvmInline
    value class None(val units: Int = 0) : Command

    companion object {
        fun parse(type: String, units: String): Command {
            return when (type) {
                "forward" -> Forward(units.toInt())
                "down" -> Down(units.toInt())
                "up" -> Up(units.toInt())
                else -> None()
            }
        }
    }
}