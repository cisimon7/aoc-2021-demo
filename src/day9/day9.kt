package day9

import readInput
import runWithTime

typealias Board = List<List<Int>>
typealias Point = Pair<Int, Int>

fun main() {
    fun part1(board: Board): Int {

        val lowPointMap = board.indices.map { i ->
            board.first().indices.map { j ->
                val point = board[i][j]
                val neighborsValues = board.neighbors(i to j).map { (i, j) -> board[i][j] }
                if (isLowPoint(point, neighborsValues)) point else -1
            }
        }

        return lowPointMap.flatten().filter { it >= 0 }.sumOf { it + 1 }
    }

    fun part2(board: Board): Int {

        val lowPoints = mutableListOf<Pair<Int, Int>>()
        board.indices.forEach { i ->
            board.first().indices.forEach { j ->
                val point = board[i][j]
                val neighborsValues = board.neighbors(i to j).map { (i, j) -> board[i][j] }
                if (isLowPoint(point, neighborsValues)) lowPoints.add(i to j)
            }
        }

        return lowPoints.map { lowPoint -> 1 + board.basinSize(lowPoint).count() }
            .sortedDescending()
            .subList(0, 3)
            .reduce { acc, size -> acc * size }
    }

    val testInput = parse(readInput("day9/input_test.txt"))
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = parse(readInput("day9/input.txt"))
    runWithTime { part1(input) }
    runWithTime { part2(input) }
}

fun parse(input: List<String>): Board {
    return input.map { row -> row.map { it.toString().toInt() } }
}

fun Board.neighbors(point: Point): List<Point> {
    val board = this
    val (x, y) = point
    return listOf(
        x + 1 to y,
        x - 1 to y,
        x to y + 1,
        x to y - 1
    ).filter { (i, j) -> i >= 0 && i < board.size && j >= 0 && j < board.first().size }

}

fun isLowPoint(value: Int, neighbors: List<Int>): Boolean {
    return neighbors.all { nei -> value < nei }
}

fun Board.basinSize(low: Point): Set<Point> {
    val board = this

    var basinPoints = setOf<Point>()

    board.neighbors(low).forEach {
        if (board.isRising(low, it)) {
            basinPoints = basinPoints + it + board.basinSize(it)
        }
    }

    return basinPoints
}

fun Board.isRising(fromPoint: Point, toPoint: Point): Boolean {
    return (this[fromPoint] < this[toPoint]) && (this[toPoint] != 9)
}

operator fun Board.get(point: Point): Int {
    val (x, y) = point
    return this[x][y]
}