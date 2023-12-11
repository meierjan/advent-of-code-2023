import Day11.calculate
import java.lang.Math.abs

object Day11 {

    data class Point(var x: Long, var y: Long)

    private fun parse(input: List<String>): List<Point> =
        input.flatMapIndexed { y, s ->
            s.mapIndexed { x, c ->
                if (c == '#') {
                    Point(x.toLong(), y.toLong())
                } else null
            }
        }
            .filterNotNull()

    fun calculate(list: List<String>, expandBy: Long = 1): Long {
        val input = parse(list)

        val xCords = input.map { it.x }.sorted()
        val xGaps = (xCords.first() until xCords.last()).minus(xCords.toSet())
        input.forEach { p -> p.x += ((expandBy - 1) * xGaps.filter { it < p.x }.size) }

        val yCords = input.map { it.y }.sorted()
        val yGaps = (yCords.first() until yCords.last()).minus(yCords.toSet())
        input.forEach { p -> p.y += ((expandBy - 1) * yGaps.filter { it < p.y }.size) }

        return input.flatMap { a -> input.map { b -> Pair(a, b) } }
            .sumOf { (a, b) -> abs(a.x - b.x) + abs(a.y - b.y) } / 2

    }

    fun part1(list: List<String>): Long {
        return calculate(list, 2)
    }

    fun part2(list: List<String>): Long {
        return calculate(list, 1_000_000)
    }
}

fun main() {
    val input = readInput("Day11")
    val testInput = readInput("Day11_test")
    println("Part 1: " + Day11.part1(testInput))
    println("Supposed to be 374")

    println("Part 1: " + Day11.part1(input))

    println("Part 1:")
    println("Part by 10: " + calculate(testInput, 10))
    println("Part by 100: " + calculate(testInput, 100))
    println("Part by 1_000_000: " + Day11.part2(input))
}