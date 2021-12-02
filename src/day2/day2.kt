package day2

import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun main() {
    fun part1(input: List<String>): String {
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

        return "Horizontal: $horizontal; Depth: $depth; Product: ${horizontal * depth}"
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val testInput = readInput("day2/day2_test.txt")
    println(part1(testInput))
    part2(testInput)

    val input = readInput("day2/day2.txt")
    val (result, time) = measureTimedValue { part1(input) }
    println("{\n\tTime: $time\n\tResult: $result\n}")

    measureTimedValue { part2(input) }
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