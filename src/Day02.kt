import java.awt.Color.blue
import java.awt.Color.green
import java.awt.Color.red
import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    fun part1(red: Int, green: Int, blue: Int, input: List<String>): Int {
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        return input.sumOf {
            val (h, t) = it.split(":")
            val id = h.split(" ")[1].toInt()
            val works = t.split(";").all { pick ->
                pick.split(",").all {
                    val (dices, color) = it.trim().split(" ")
                    when (color.trim()) {
                        "red" -> red >= dices.toInt()
                        "blue" -> blue >= dices.toInt()
                        "green" -> green >= dices.toInt()
                        else -> false
                    }
                }
            }
            if (works) id else 0
        }
    }

    fun part2(input: List<String>): Int {

        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        return input.sumOf {

            var red = 0
            var blue = 0
            var green = 0

            val (_, t) = it.split(":")
            t.split(";").forEach { pick ->
                pick.split(",").forEach {
                    val (dices, color) = it.trim().split(" ")
                    when (color.trim()) {
                        "red" -> red = max(red, dices.toInt())
                        "blue" -> blue = max(blue, dices.toInt())
                        "green" -> green = max(green, dices.toInt())
                        else -> throw Exception("Unknown color")
                    }
                }
            }
            red * green * blue
        }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val result = part1(12, 13, 14, testInput)
    result.println("Test A: ")


    check(result == 8)

    val resultB = part2(testInput)

    check(resultB == 2286)


    val input = readInput("Day02")
    part1(12, 13, 14, input).println("Part 1:")
    part2(input).println("Part 2:")
}
