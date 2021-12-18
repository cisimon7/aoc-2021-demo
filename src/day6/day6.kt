package day6

import readInput
import runWithTime
import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): Int {

        var fish: List<Int> = parse(input)

        repeat(80) {
            val birthFishCount = fish.count { days -> days == 0 }
            fish = fish.map { days -> if (days == 0) 6 else days - 1 }
            fish = fish + List(birthFishCount) { 8 }
        }

        return fish.count()
    }

    fun part2(input: List<String>): BigInteger {

        val days2Birth = parse(input)
        val countDays = 256

        /* Map<RemainingDays, FishCount> */
        var daysLeft2Count = (0..8).associateWith { day -> days2Birth.count { it == day }.toBigInteger() }
        val fishCountFromDaysLeft = { daysLeft: Int -> daysLeft2Count.getValue(daysLeft) }

        repeat(countDays) {
            daysLeft2Count = daysLeft2Count.mapValues { (dayLeft, _) ->
                val prevCount = fishCountFromDaysLeft((dayLeft + 1) % 9)
                when {
                    (dayLeft == 6) -> prevCount + fishCountFromDaysLeft(0)
                    else -> prevCount
                }
            }
        }

        return daysLeft2Count.values.reduce { acc, count -> acc + count }
    }

    val testInput = readInput("day6/day6_test.txt")
    println(part1(testInput))

    val input = readInput("day6/day6.txt")
    runWithTime { part1(input) }
    runWithTime { part2(input) }
}

fun parse(input: List<String>): List<Int> {
    return input.map {
        it.split(",").filter { it.isNotBlank() }.map { it.toInt() }
    }.flatten()
}