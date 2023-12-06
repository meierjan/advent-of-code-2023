import org.junit.Test

class Day05KtTest {
    @Test
    fun test_range() {
        val rule = Day05.PlaningRule(100, 0, 100)
        assert(rule.isInSourceRange(0))
        assert(rule.isInSourceRange(50))
        assert(rule.isInSourceRange(99))
        assert(!rule.isInSourceRange(101))
        assert(!rule.isInSourceRange(100))
    }

    @Test
    fun test_mapping() {
        val rule = Day05.PlaningRule(52, 50, 48)
        val seed = 79L
        assert(rule.findMatch(seed) == 81L)
    }

    @Test
    fun test_example() {
        val testInput = readInput("Day05_test")
        val result = Day05.parse(testInput)

        val list = result.seeds.map { result.findLocation(it) }.sorted()
        assert(list[0] == 35L)
        assert(list[1] == 43L)
        assert(list[2] == 82L)
        assert(list[3] == 86L)


    }
//
//    @Test
//    fun test_no_mapped( ) {
//        val rule = Day05.PlaningRule(52 ,50 ,48)
//        val seed = 1L
//        assert(rule.findMatch(seed) == 1L)
//    }
}
