import java.lang.Math.pow

object Day07 {


    private val cardValue = mapOf(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'J' to 11,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2,
    )

    private val cardValueWithJoker = cardValue.toMutableMap()
        .apply { this['J'] = 1 }


    private fun List<Int>.handType(): Int {
        return when {
            // five of a kind
            this.first() == 5 -> 6
            // four of a kind
            this.first() == 4 -> 5
            // full house
            this.first() == 3 && this.second() == 2 -> 4
            // three of a kind
            this.first() == 3 -> 3
            // two pair
            this.first() == 2 && this.second() == 2 -> 2
            // one pair
            this.first() == 2 -> 1
            // high card
            else -> 0
        }
    }


    data class Hand(
        val cards: List<Char>,
        val bid: Long,
    ) {

        fun orderValue(map: Map<Char, Int>): Long = cards.reversed()
            .mapIndexed { index, c ->
                (map[c]!! * pow(100.0, index.toDouble()).toLong())
            }.sum()

    }


    private fun parse(input: List<String>): List<Hand> =
        input.map {
            val (cards, bid) = it.split(" ")
            Hand(cards.toList(), bid.toLong())
        }


    fun part1(input: List<String>): Long {

        fun List<Char>.type(): Int {
            return this.groupBy { it }
                .values
                .map { it.size }
                .sortedDescending()
                .handType()
        }


        return parse(input).groupBy { it.cards.type() }
            .toSortedMap()
            .flatMap { (_, hands) -> hands.sortedBy { it.orderValue(cardValue) } }
            .mapIndexed { index, hand ->
                (index + 1) * hand.bid
            }
            .sum()

    }

    fun part2(input: List<String>): Long {

        fun List<Char>.type(): Int {
            val typeWithoutJ = this.groupBy { it }.toMutableMap()
            val jokers = typeWithoutJ['J'] ?: emptyList()

            typeWithoutJ.remove('J')


            val type = typeWithoutJ.values
                .map { it.size }
                .sortedDescending()
                .toMutableList()

            if (type.size == 0) {
                type.add(0)
            }

            type[0] += jokers.size

            return type.handType()
        }

        return parse(input).groupBy { it.cards.type() }
            .toSortedMap()
            .flatMap { (_, hands) -> hands.sortedBy { it.orderValue(cardValueWithJoker) } }
            .mapIndexed() { index, hand ->
                (index + 1) * hand.bid
            }
            .sum()
    }

}

fun <T> List<T>.second(): T = this[1]


fun main() {
    println("test")
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")


    val part1_test = Day07.part1(testInput)
    println("Part 1 test: $part1_test")
    val part1 = Day07.part1(input)
    println("Part 1: $part1")

    val part2_test = Day07.part2(testInput)
    println("Part 2 test: $part2_test")
    val part2 = Day07.part2(input)
    println("Part 2: $part2")


}
