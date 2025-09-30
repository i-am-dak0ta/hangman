package utils

import core.utils.WordMasker
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class WordMaskerTest {

    @Test
    fun `GIVEN target word and no guessed letters WHEN mask called THEN all letters masked`() {
        val target = "окно"
        val guessed = emptySet<Char>()
        val expected = "****"

        val actual = WordMasker.mask(target, guessed)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN target word and some guessed letters WHEN mask called THEN only guessed letters revealed`() {
        val target = "окно"
        val guessed = setOf('о')
        val expected = "о**о"

        val actual = WordMasker.mask(target, guessed)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN target word and user input matching all letters WHEN evaluate called THEN status POS`() {
        val target = "окно"
        val input = "окно"
        val expected = "окно;POS"

        val actual = WordMasker.evaluate(target, input)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN target word and user input missing letters WHEN evaluate called THEN status NEG`() {
        val target = "волокно"
        val input = "барахло"
        val expected = "*оло**о;NEG"

        val actual = WordMasker.evaluate(target, input)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN target word and mixed case user input WHEN evaluate called THEN case is ignored`() {
        val target = "ОкНо"
        val input = "ОКнО"
        val expected = "окно;POS"

        val actual = WordMasker.evaluate(target, input)

        assertEquals(expected, actual)
    }
}
