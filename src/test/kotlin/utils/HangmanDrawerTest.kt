package utils

import core.utils.HangmanDrawer
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class HangmanDrawerTest {

    @Test
    fun `GIVEN full attempts left WHEN draw called THEN empty gallows returned`() {
        val attempts = 9
        val expected = """






    =========
        """.trimIndent()

        val actual = HangmanDrawer.draw(attempts)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN five attempts left WHEN draw called THEN head is drawn`() {
        val attempts = 5
        val expected = """
      +---+
      |   |
      O   |
          |
          |
          |
    =========
        """.trimIndent()

        val actual = HangmanDrawer.draw(attempts)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN no more attempts left WHEN draw called THEN hangman with gallows returned`() {
        val attempts = 0
        val expected = """
      +---+
      |   |
      O   |
     /|\  |
     / \  |
          |
    =========
        """.trimIndent()

        val actual = HangmanDrawer.draw(attempts)

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN invalid attempts WHEN draw called THEN empty string returned`() {
        val attempts = -1
        val expected = ""

        val actual = HangmanDrawer.draw(attempts)

        assertEquals(expected, actual)
    }
}
