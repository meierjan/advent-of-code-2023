import Day05.part1
import Day05.part2


object Day05 {

    data class PlaningRule(
        val destinationStart: Long,
        val sourceStart: Long,
        val rangeLength: Long,
    ) {
        fun isInSourceRange(input: Long): Boolean =
            sourceStart <= input && input < sourceStart + rangeLength


        fun findMatch(input: Long): Long {
            require(isInSourceRange(input))
            val offset = input - sourceStart
            return destinationStart + offset
        }
    }

    data class PlantMap(
        val seeds: Set<Long>,
        val seedToSoil: Set<PlaningRule>,
        val soilToFertilizer: Set<PlaningRule>,
        val fertilizerToWater: Set<PlaningRule>,
        val waterToLight: Set<PlaningRule>,
        val lightToTemperature: Set<PlaningRule>,
        val temperatureToHumidity: Set<PlaningRule>,
        val humidityToLocation: Set<PlaningRule>,
    ) {
        fun findLocation(seed: Long): Long =
            seedToSoil.findMatch(seed)
                .let { soilToFertilizer.findMatch(it) }
                .let { fertilizerToWater.findMatch(it) }
                .let { waterToLight.findMatch(it) }
                .let { lightToTemperature.findMatch(it) }
                .let { temperatureToHumidity.findMatch(it) }
                .let { humidityToLocation.findMatch(it) }


    }

    fun Set<PlaningRule>.findMatch(input: Long): Long {
        return find { it.isInSourceRange(input) }?.findMatch(input) ?: input
    }


    fun parse(input: List<String>): PlantMap =
        input.joinToString("#").split("##").let {
            val boxes = it.iterator()
            PlantMap(
                boxes.next().split(" ").drop(1).map { it.toLong() }.toSet(),
                seedToSoil = parseMap(boxes.next().split("#")),
                soilToFertilizer = parseMap(boxes.next().split("#")),
                fertilizerToWater = parseMap(boxes.next().split("#")),
                waterToLight = parseMap(boxes.next().split("#")),
                lightToTemperature = parseMap(boxes.next().split("#")),
                temperatureToHumidity = parseMap(boxes.next().split("#")),
                humidityToLocation = parseMap(boxes.next().split("#")),
            )
        }


    fun parseMap(input: List<String>): Set<PlaningRule> =
        input.drop(1).map {
            val (dest, source, length) = it.split(' ')
            PlaningRule(dest.toLong(), source.toLong(), length.toLong())
        }.toSet()

    fun part1(input: List<String>): Long {
        val plantingMap = parse(input)
        return plantingMap.seeds.minOfOrNull { plantingMap.findLocation(it) }!!
    }


    fun part2(input: List<String>): Long {
        val plantingMap = parse(input)
        return plantingMap.seeds.chunked(2)
            .map { (from, len) -> (from until from + len) }
            .flatMap { it -> it.map { plantingMap.findLocation(it) } }
            .min()

    }

}

fun main() {


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val result = part1(testInput)
    result.println("Test A: ")


    check(result == 35L)

    val resultB = part2(testInput)
    resultB.println("Test B: ")

    check(resultB == 46L)


    val input = readInput("Day05")
    part1(input).println("Part 1:")
    part2(input).println("Part 2:")


}








