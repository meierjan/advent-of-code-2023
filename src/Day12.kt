
// coded along with https://www.youtube.com/watch?v=g3Ms5e7Jdqo
object Day12 {

    data class Row(
        val input: String,
        val groups: List<Int>
    )

    fun parse(input: List<String>): List<Row> {
        return input.map {
            val (head, tail) = it.split(" ")
            Row(head, tail.split(",").map { it.toInt() })
        }
    }

    val cache = mutableMapOf<Pair<String, List<Int>>, Long> ()

    fun count(config: String, nums: List<Int>): Long {
        if (config == "") {
            return if (nums.isEmpty()) {
                1L
            } else {
                0L
            }
        }
        if (nums.isEmpty()) {
            return if (config.contains("#")) {
                0L
            } else {
                1L
            }
        }

        val key = Pair(config, nums)
        if(cache.contains(key)) {
            return cache[key]!!
        }
        var result = 0L


        if (config[0] == '.' || config[0] == '?') {
            result += count(config.drop(1), nums)
        }


        if (config[0] == '#' || config[0] == '?') {
            if (nums[0] <= config.length
                && !config.take(nums[0]).contains(".")
                && (nums[0] == config.length || config[nums[0]] != '#')
            ) {
                result += count(config.drop(nums[0] + 1), nums.drop(1))

            }
        }

        cache[key] = result

        return result


    }


    fun part1(input: List<Row>): Long {
        return input.sumOf { count(it.input, it.groups) }
    }


    fun part2(input: List<Row>): Long {
        return input.map { row ->

            Row(
                (0 until 5).joinToString("?") { row.input },
                (0 until 5).flatMap { row.groups })


        }.sumOf { count(it.input, it.groups) }

    }


}

fun main() {
    val testInput = Day12.parse(readInput("Day12_test"))
    val input = Day12.parse(readInput("Day12"))

    println(Day12.part1(testInput))
    println(Day12.part1(input))

    println(Day12.part2(testInput))
    println(Day12.part2(input))
}