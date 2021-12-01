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

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01P1")

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
