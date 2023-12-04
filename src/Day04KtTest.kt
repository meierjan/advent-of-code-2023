import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Test

class Day04KtTest {
    @Test
    fun testParse1() {
        val input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val result = Day04.parse1(listOf(input)).first()

        MatcherAssert.assertThat(
            result,
            Is.`is`(
                CoreMatchers.equalTo(
                    Day04.Card(
                        number = 1,
                        numsOnCard = setOf(83, 86, 6, 31, 17, 9, 48, 53),
                        winningNums = setOf(41, 48, 83, 86, 17)
                    )
                )
            )
        )
    }
}
