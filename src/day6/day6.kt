package day6

import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class, kotlin.ExperimentalStdlibApi::class)
fun main() {
    fun part1(input: List<String>): Int {

        var fishes: List<Int> = parse(input)

        repeat(80) { _ ->
            val birthFishesCount = fishes.count { days -> days == 0 }
            fishes = fishes.map { days -> if (days == 0) 6 else days - 1 }
            fishes = fishes + List(birthFishesCount) { 8 }
        }

        return fishes.count()
    }

    fun part2(input: List<String>): Long {

        val fish = parse(input)
        val countDays = 150
        val firstCycle = 8 + 1
        val otherCycle = 6 + 1

        fun growthModel(newGen: Long, oldGen: Long, countDays: Int): Long {

            return when {
                countDays <= otherCycle -> (newGen + oldGen)
                else -> {
                    (if (countDays <= firstCycle) newGen else growthModel(newGen, newGen, countDays - firstCycle)) +
                            growthModel(oldGen, oldGen, countDays - otherCycle)
                }
            }
        }

        val beforeStartFish = fish.filter { day -> countDays > day }

        return beforeStartFish.sumOf { day ->
            if (countDays - day > firstCycle)
                growthModel(1, 0, countDays - day)
            else
                1
        } +
                fish.sumOf { day ->
                    if (countDays - day > otherCycle)
                        growthModel(0, 1, countDays - day)
                    else 1
                }
    }

    fun part3(input: List<String>): Long {

        val fish = parse(input)
        val countDays = 256
        val firstCycle = 8 + 1
        val otherCycle = 6 + 1

        val fishGrowth = DeepRecursiveFunction<Triple<Long, Long, Int>, Long> { (newGen, oldGen, countDays) ->
            when {
                countDays <= otherCycle -> (newGen + oldGen)
                else -> {
                    (if (countDays <= firstCycle) newGen else callRecursive(
                        Triple(
                            newGen,
                            newGen,
                            countDays - firstCycle
                        )
                    )) +
                            callRecursive(Triple(oldGen, oldGen, countDays - otherCycle))
                }
            }
        }

        val beforeStartFish = fish.filter { day -> countDays > day }

        return beforeStartFish.sumOf { day ->
            if (countDays - day > firstCycle)
                fishGrowth(Triple(1, 0, countDays - day))
            else
                1
        } +
                fish.sumOf { day ->
                    if (countDays - day > otherCycle)
                        fishGrowth(Triple(0, 1, countDays - day))
                    else 1
                }
    }

    val testInput = readInput("day6/day6_test.txt")
    println(part1(testInput))
//    println(part2(testInput))

    val input = readInput("day6/day6.txt")
    val (result1, time1) = measureTimedValue { part1(input) }
    println(mapOf("Time (ms)" to time1.toString()) + mapOf("Count" to result1))

//    val (result3, time3) = measureTimedValue { part3(input) }
//    println(mapOf("Time (ms)" to time3.toString()) + mapOf("Count" to result3))

    val (result2, time2) = measureTimedValue { part3(input) }
    println(mapOf("Time (ms)" to time2.toString()) + mapOf("Count" to result2))
}

fun parse(input: List<String>): List<Int> {
    return input.map {
        it.split(",").filter { it.isNotBlank() }.map { it.toInt() }
    }.flatten()
}

sealed interface FishStage {
    object Born : FishStage
    object FirstBirth : FishStage
    object NormalBirth : FishStage
}