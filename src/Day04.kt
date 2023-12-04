import Day04.part1
import Day04.part2
import kotlin.math.min
import kotlin.math.pow


object Day04 {

    data class Card(
        val number: Int,
        val winningNums: Set<Int>,
        val numsOnCard: Set<Int>
    ) {
        val matches: Int
            get() = winningNums.intersect(numsOnCard).size

    }

    fun parse1(input: List<String>): List<Card> =
        input.map { input ->
            // "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
            val (winningPart, drawnPart) = input.split("|")
            val (cardHeader, winningNumbers) = winningPart.trim().split(":")
            val cardNum = cardHeader.split(" ").last()
            Card(
                number = cardNum.toInt(),
                winningNums = winningNumbers.trim()
                    .split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .toSet(),
                numsOnCard = drawnPart.split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .toSet()
            )
        }

    fun part1(input: List<String>): Int {
        return parse1(input).sumOf {
            val matches = it.matches
            if (matches > 0) {
                2.toDouble().pow(matches.toDouble() - 1).toLong()
            } else {
                0L
            }.toInt()
        }
    }

    fun part2(input: List<String>): Int {

        val original = parse1(input)
        val stack = original.toMutableList()
        val iterator = stack.listIterator()
        while (iterator.hasNext()) {
            val currentCard = iterator.next()
            val matches = currentCard.matches

            if (matches > 0) {
                val originalCardIndex = currentCard.number - 1
                val nextIndex = originalCardIndex + 1

                val wonRangeStart = min(nextIndex, original.lastIndex)
                val wonRangeEnd = min(nextIndex + matches, original.lastIndex)
                val wonRange = wonRangeStart until wonRangeEnd

                for (wonCard in original.slice(wonRange)) {
                    iterator.add(wonCard)
                    iterator.previous()
                }
            }
        }
        return iterator.previousIndex() + 1
    }

}

fun main() {


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val result = part1(testInput)
    result.println("Test A: ")


    check(result == 13)

    val resultB = part2(testInput)
    resultB.println("Test B: ")

    check(resultB == 30)


    val input = readInput("Day04")
    part1(input).println("Part 1:")
    part2(input).println("Part 2:")


}








