package day4

import readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

typealias Board = List<List<String>>
typealias Boards = MutableList<Board>

private const val MARK = "⋇"

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    fun part1(input: List<String>): Int? {

        val (order, boards) = parseInput(input)
        var product: Int? = null
        var winningBoard: Board? = null

        loop@ for (num in order) {
            for ((i, board) in (boards.indices.zip(boards))) {
                val markBoard = board.mark(num)
                if (markBoard.check()) {
                    val sumUnMarked = markBoard.sumUnmarked()
                    winningBoard = markBoard
                    product = num.toInt() * sumUnMarked
                    break@loop
                }
                boards[i] = markBoard
            }
        }

        winningBoard?.let { println(it) }
        return product
    }

    fun part2(input: List<String>) {

    }

    val testInput = readInput("day4/day4_test.txt")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day4/day4.txt")
    val (result1, time1) = measureTimedValue { part1(input) }
    println(mapOf("Time (ms)" to time1.toString()) + mapOf("Product" to result1))

//    val (result2, time2) = measureTimedValue { part2(input) }
//    println(mapOf("Time (ms)" to time2.toString()) + result2)
}

private fun Board.sumUnmarked(): Int {
    val board = this
    return board
        .map { row -> row.filter { value -> !value.startsWith(MARK) } }
        .flatten()
        .sumOf { it.toInt() }
}

private fun parseInput(input: List<String>): Pair<List<String>, Boards> {
    val inputSpliced = input.joinToString("\n").split("\n\n")

    val order = inputSpliced.first().split(",").filter { it.isNotBlank() }

    val boards: MutableList<Board> = inputSpliced.drop(1)
        .map { board ->
            board.split("\n")
                .map { row ->
                    row.split(" ")
                        .filter { point -> point.isNotBlank() }
                }
        } as MutableList<Board>

    return Pair(order, boards)
}

private fun Board.print() {
    println(this)
}

private fun Board.mark(num: String): Board {
    return this.map { row -> row.map { value -> if (value == num) "⋇$value" else value } }
}

private fun Board.check(): Boolean {
    val board = this
    val boardTransposed = List(board.first().size) { idx ->
        board.map { it[idx] }
    }
    val allSequence = board + boardTransposed

    return allSequence.any { row ->
        row.all { value -> value.startsWith("⋇") }
    }
}
