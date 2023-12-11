object Day10 {


    data class Map(
        val vertices: List<List<Char>>
    ) {

        val start = this.vertices.findCoordinatesOf('S')!!

        fun get(pair: Pair<Int, Int>): Char = get(pair.first, pair.second)

        fun get(x: Int, y: Int): Char = vertices.getOrNull(y)?.getOrNull(x) ?: '.'

        fun getNeighbors(pair: Pair<Int, Int>) = getNeighbors(pair, get(pair))

        fun getNeighbors(pair: Pair<Int, Int>, tile: Char): List<Pair<Int, Int>> {
            val (x, y) = pair
            return when (tile) {
                // | is a vertical pipe connecting north and south.
                '|' -> listOf(Pair(x, y - 1), Pair(x, y + 1))
                // - is a horizontal pipe connecting east and west.
                '-' -> listOf(Pair(x - 1, y), Pair(x + 1, y))
                // L is a 90-degree bend connecting north and east.
                'L' -> listOf(Pair(x, y - 1), Pair(x + 1, y))
                // J is a 90-degree bend connecting north and west.
                'J' -> listOf(Pair(x, y - 1), Pair(x - 1, y))
                // 7 is a 90-degree bend connecting south and west.
                '7' -> listOf(Pair(x, y + 1), Pair(x - 1, y))
                // F is a 90-degree bend connecting south and east.
                'F' -> listOf(Pair(x, y + 1), Pair(x + 1, y))
                // . is ground; there is no pipe in this tile.
                '.' -> listOf()
                // S is the starting position of the animal; there is a pipe on this
//                'S' -> listOf(Pair(x, y + 1))
                else -> throw Exception("Unknown tile type $tile")
            }
        }


        private

        fun List<List<Char>>.findCoordinatesOf(charToFind: Char): Pair<Int, Int>? {
            for ((y, row) in this.withIndex()) {
                val x = row.indexOf(charToFind)
                if (x != -1) {
                    return Pair(x, y)
                }
            }
            return null
        }


        companion object {
            fun parse(input: List<String>): Map =
                Map(input.map { it.toList() })
        }
    }

    class Dijkstra(val map: Map, val startTile: Char) {


        // 4          dist[v] ← INFINITY
        // 5          prev[v] ← UNDEFINED
        val dist = map.vertices.map { it.map { Int.MAX_VALUE }.toMutableList() }
        private fun dist(pair: Pair<Int, Int>): Int = dist[pair.second][pair.first]

        private val prev: List<MutableList<Pair<Int, Int>?>> =
            map.vertices.map { it.map { null }.toMutableList() }


        private fun getNeighbors(pair: Pair<Int, Int>): List<Pair<Int, Int>> =
            if (pair == map.start) {
                map.getNeighbors(pair, startTile)
            } else {
                map.getNeighbors(pair)
            }


        fun run() {
            val start = map.start

            // 7      dist[source] ← 0
            dist[start.second][start.first] = 0

            val Q = mutableListOf<Pair<Int, Int>>()
            // 6          add v to Q
            Q.addAll(map.vertices.flatMapIndexed { y, chars ->
                chars.mapIndexed { x, _ ->
                    Pair(
                        x,
                        y
                    )
                }
            })

            //  9      while Q is not empty:
            while (Q.isNotEmpty()) {
                // 10 u ← vertex in Q with min dist[u]

                val u = Q.minByOrNull { dist(it) }!!


                // 11 remove u from Q
                Q.remove(u)

                // 13 for each neighbor v of u still in Q:
                for (v in getNeighbors(u)) {
                    //  13 u still in Q:
                    if (!Q.contains(v)) {
                        continue
                    }

                    // 14 alt ← dist[u] + Graph.Edges(u, v)
                    val alt = dist(u) + 1

                    if (alt < dist(v)) {
                        dist[v.second][v.first] = alt
                        prev[v.second][v.first] = u
                    }
                }
            }
        }


    }


    fun part1(input: List<String>): Int {


        val map = Map.parse(input)

        val tiles = listOf('|', '-', 'L', 'J', '7', 'F')


        val pipeCandidates =
            tiles.filter { tile ->
                map.getNeighbors(map.start, tile)
                    .all { neighbor ->
                        map.getNeighbors(neighbor)
                            .contains(map.start)
                    }
            }

        println(pipeCandidates)

        return pipeCandidates.map {
            val dijkstra = Dijkstra(map, it)
            dijkstra.run()

            dijkstra.dist.map { it.filter { it != Int.MAX_VALUE }.maxBy { it } }.max()


        }.min()

    }


}

fun main() {
//    val testInput = readInput("Day10_test")
//    println(Day10.part1(testInput))

    val testInput2 = readInput("Day10_test2")
    println(Day10.part1(testInput2))

    val input = readInput("Day10")
    println(Day10.part1(input))
}