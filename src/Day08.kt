object Day08 {


    data class Vertex(
        val left: String,
        val right: String,
    )

    data class Path(
        val movingPattern: List<Char>,
        val vertexes: Map<String, Vertex>
    ) {
        fun getOp(pos: Long): Char {
            return movingPattern[(pos % movingPattern.size).toInt()]
        }
    }

    private fun parse(input: List<String>): Path =
        Path(
            movingPattern = input.first().toList(),
            vertexes = input.drop(2).associate {

                val (h, t) = it.split("=")
                val from = h.trim()
                val (left, right) = t.trim()
                    .removePrefix("(")
                    .removeSuffix(")")
                    .split(",")
                    .map { it.trim() }
                from to Vertex(left, right)
            }
        )

    fun solve(path: Path, start: String, isEnd: (String) -> Boolean) : Long {
        var currentPosition = start
        for (i in 0 until Long.MAX_VALUE) {
            if (isEnd(currentPosition)) {
                return i
            }

            val cOp = path.getOp(i)
            currentPosition = when (cOp) {
                'L' -> path.vertexes[currentPosition]!!.left
                'R' -> path.vertexes[currentPosition]!!.right
                else -> throw IllegalStateException("invalid op $cOp")
            }
        }

        return -1
    }

    fun part1(input: List<String>): Long =
        solve(parse(input), "AAA") { it == "ZZZ" }

    fun part2(input: List<String>): Long {
        val path = parse(input)
        val result = path.vertexes.keys.filter { it.endsWith('A') }.map {
            solve(path, it) { it.endsWith('Z') }
        }
        return findLCMOfListOfNumbers(result)
    }



    private fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }

    private fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}

fun main() {
    val testInputA = readInput("Day08_test")
    val input = readInput("Day08")

    println("Part 1 test: ${Day08.part1(testInputA)}")
    println("Part 1: ${Day08.part1(input)}")

    val testInputB = readInput("Day08b_test")
    println("Part 2 test: ${Day08.part2(testInputB)}")
    println("Part 2: ${Day08.part2(input)}")

}