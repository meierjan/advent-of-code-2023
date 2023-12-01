fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val line = it
            val firstNumber = line.first { it.isDigit() }.digitToInt()
            val lastNumber = line.last { it.isDigit() }.digitToInt()
            firstNumber * 10 + lastNumber
        }
    }

    fun part2(input: List<String>): Int {
        val numbers = listOf(
            "1",
            "one",
            "2",
            "two",
            "3",
            "three",
            "4",
            "four",
            "5",
            "five",
            "6",
            "six",
            "7",
            "seven",
            "8",
            "eight",
            "9",
            "nine",
        )

        return input.sumOf { line ->
            val firstNumber = numbers
                .mapIndexed { index, it -> Pair(index, line.indexOf(it)) }
                .filter { it.second != -1 }
                .minBy { it.second }
                .first / 2 + 1

            val lastNumber = numbers.mapIndexed { index, it -> Pair(index, line.lastIndexOf(it)) }
                .filter { it.second != -1 }
                .maxBy { it.second }
                .first / 2 + 1

            firstNumber * 10 + lastNumber
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val result = part1(testInput)


    check(result == 142)

    val testInputB = readInput("Day01b_test")
    val resultB = part2(testInputB)

    check(resultB == 281)


    val input = readInput("Day01")
    part1(input).println("Part 1:")
    part2(input).println("Part 2:")
}
