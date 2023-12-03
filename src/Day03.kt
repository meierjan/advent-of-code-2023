import java.lang.Integer.max
import java.lang.Integer.min


data class Symbol(
    val char: Char,
    val x: Int,
    val y: Int
)

data class Element(
    val number: String,
    val lineNumber: Int,
    val range: IntRange,
    val adjacentSymbols: List<Symbol>
)

fun main() {

    fun computeResult(input: List<String>): List<Element> {
        val regExp = Regex("""\d+""")
        val characterMap = input.map { it.toCharArray() }.toTypedArray()
        val isSymbol: (Char) -> Boolean = { !it.isDigit() && it != '.' }
        val adjacentSymbols: (String, Int, IntRange) -> List<Symbol> =
            { number, lineNumber, range ->

                val lineLength = characterMap[lineNumber].size
                val lines = characterMap.size
                val checkingRangeX = max(0, range.first - 1) until min(range.last + 2, lineLength)
                val checkingRangeY = max(0, lineNumber - 1) until min(lineNumber + 2, lines)

                characterMap.slice(checkingRangeY).flatMapIndexed { i, it ->
                    val y = i + checkingRangeY.first
                    it.slice(checkingRangeX).mapIndexedNotNull { j, it ->
                        val x = j + checkingRangeX.first
                        if (isSymbol(it)) {
                            Symbol(it, x, y)
                        } else {
                            null
                        }
                    }
                }


            }

        // characterMap[y][x]

        return input.mapIndexed { lineNumber, line ->
            regExp.findAll(line).toList()
                .map { match ->
                    val adjacentSymbols = adjacentSymbols(match.value, lineNumber, match.range)

                    Element(
                        match.value,
                        lineNumber,
                        match.range,
                        adjacentSymbols
                    )
                }
        }.flatten()


    }


    fun part1(input: List<String>): Int =
        computeResult(input)
            .filter { it.adjacentSymbols.isNotEmpty() }
            .sumOf { it.number.toInt() }


    fun part2(input: List<String>): Int {
        val result= computeResult(input)
        val map : MutableMap<Symbol, List<Element>> = mutableMapOf()
        for(e in result)  {
            for(s in e.adjacentSymbols.filter { it.char == '*' }) {
               if(!map.containsKey(s))  {
                   map[s] = mutableListOf()
               }
                map[s] = map[s]!!.plus(e)
            }

        }

        return map.filterValues { it.size == 2 }
            .map { it.value[0].number.toInt() * it.value[1].number.toInt() }
            .sum()

    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val result = part1(testInput)
    result.println("Test A: ")


    check(result == 4361)

    val resultB = part2(testInput)
    resultB.println("Test B: ")

    check(resultB == 467835)


    val input = readInput("Day03")
    part1(input).println("Part 1:")
    part2(input).println("Part 2:")
}
