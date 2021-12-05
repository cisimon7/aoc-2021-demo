package day5

import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "(x=$x, y=$y)"
    }
}

private const val MARK = "⋇"

@OptIn(ExperimentalTime::class)
fun main() {
    fun part1(input: List<String>): Int {

        val vents = parse(input)
        val diagram = MutableList(1_000) { MutableList(1_000) { 0 } }
        vents.filter { (pt1, pt2) -> pt1.x == pt2.x || pt1.y == pt2.y }
            .forEach { (pt1, pt2) ->
                val xRange = if (pt1.x > pt2.x) pt1.x downTo pt2.x else pt2.x downTo pt1.x
                val yRange = if (pt1.y > pt2.y) pt1.y downTo pt2.y else pt2.y downTo pt1.y
                val link = xRange.map { x -> yRange.map { y -> Point(x, y) } }.flatten()
                link.forEach { (x, y) -> diagram[y][x]+=1 }
            }

//         diagram.forEach { println(it) }
        return diagram.flatten().count { num -> num > 1 }
    }

    fun part2(input: List<String>): Int {

        val vents = parse(input)
        val diagram = MutableList(1_000) { MutableList(1_000) { 0 } }
        vents.forEach { (pt1, pt2) ->
            var link = mutableListOf<Point>()
            if (pt1.x == pt2.x || pt1.y == pt2.y) {
                val xRange = if (pt1.x > pt2.x) pt1.x downTo pt2.x else pt2.x downTo pt1.x
                val yRange = if (pt1.y > pt2.y) pt1.y downTo pt2.y else pt2.y downTo pt1.y
                xRange.forEach { x -> yRange.forEach { y -> link.add(Point(x, y)) } }
            } else {
                val xRange = if (pt1.x > pt2.x) pt1.x downTo pt2.x else pt1.x until pt2.x+1
                val yRange = if (pt1.y > pt2.y) pt1.y downTo pt2.y else pt1.y until pt2.y+1
                xRange.zip(yRange).forEach { (x, y) -> link.add(Point(x,y)) }
            }
            link.forEach { (x, y) -> diagram[y][x]+=1 }
        }

        // diagram.forEach { println(it) }
        return diagram.flatten().count { num -> num > 1 }
    }

    val testInput = readInput("day5/day5_test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day5/day5.txt")
    val (result1, time1) = measureTimedValue { part1(input) }
    println(mapOf("Time (ms)" to time1.toString()) + mapOf("Count" to result1))

    val (result2, time2) = measureTimedValue { part2(input) }
    println(mapOf("Time (ms)" to time2.toString()) + mapOf("Count" to result2))
}

fun parse(input: List<String>): List<Pair<Point, Point>> {
    return input.map { line ->
        line.split(" -> ")
            .map { it.split(",").filter { it.isNotBlank() }.map { it.toInt() } }
            .map { Point(it[0], it[1]) }
    }.map { Pair(it[0], it[1]) }
}