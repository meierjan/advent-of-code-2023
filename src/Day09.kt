object Day09 {
    fun parse(input: List<String>): List<List<Long>> {
        return input.map { it.split(" ").map { it.toLong() } }
    }


    fun part1(input: List<List<Long>>): Long =
        input.sumOf {
            val resultList = mutableListOf(it)
            while (resultList.last().any { it != 0L }) {
                resultList.add(resultList.last().zipWithNext { a, b -> b - a })
            }
            resultList.reversed().fold(0L) { acc, list -> acc + list.last() }
        }


    fun part2(input: List<List<Long>>): Long =
        input.sumOf {
            val resultList = mutableListOf(it)
            while (resultList.last().any { it != 0L }) {
                resultList.add(resultList.last().zipWithNext { a, b -> b - a })
            }
            resultList.reversed().fold(0L) { acc, list -> list.first() - acc }
        }
}

fun main() {
    val testInput = Day09.parse(readInput("Day09_test"))
    Day09.part1(testInput).println("Part 1 Test:")
    println("Supposed to be 114")

    val realInput = Day09.parse(readInput("Day09"))
    Day09.part1(realInput).println("Part 1:")


    Day09.part2(testInput).println("Part 2 Test:")
    println("Supposed to be 2")
    Day09.part2(realInput).println("Part 2:")



}