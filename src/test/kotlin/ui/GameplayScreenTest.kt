package ui

import core.io.Console
import entities.Difficulty
import entities.GameData
import entities.GameWord
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ui.game.GameplayScreen

class GameplayScreenTest {

    @Test
    fun `GIVEN gameplay WHEN not finished THEN ask for guess and emit GiveGuess`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // Ход: "к"
        whenever(console.readLine()).thenReturn("к")
        val data = GameData(
            difficulty = Difficulty.EASY,
            category = "короткие",
            targetWord = GameWord("кот", "мяукает"),
            guessedLetters = mutableSetOf(),
            wrongLetters = mutableSetOf(),
            attemptsUsed = 0,
            isFinished = false
        )
        val screen = GameplayScreen(console, onGameEvent = { events.add(it) }, gameData = data)

        val expected = ScreenNavEvent.Stay
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.GiveGuess && it.input == "к" })
    }

    @Test
    fun `GIVEN gameplay WHEN finished and win THEN prints win and returns to menu and resets`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // После победы: "" -> Enter
        whenever(console.readLine()).thenReturn("")
        val data = GameData(
            difficulty = Difficulty.EASY,
            category = "короткие",
            targetWord = GameWord("кот", "мяукает"),
            guessedLetters = mutableSetOf('к', 'о', 'т'),
            wrongLetters = mutableSetOf(),
            attemptsUsed = 0,
            isFinished = true,
            isWin = true
        )
        val screen = GameplayScreen(console, onGameEvent = { events.add(it) }, gameData = data)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.MENU)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.ResetGame })
        verify(console).println(argThat { contains("Поздравляем - Вы выиграли") })
    }

    @Test
    fun `GIVEN gameplay WHEN finished and lose THEN prints lose and returns to menu and resets`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // После поражения: "" -> Enter
        whenever(console.readLine()).thenReturn("")
        val data = GameData(
            difficulty = Difficulty.EASY,
            category = "короткие",
            targetWord = GameWord("кот", "мяукает"),
            guessedLetters = mutableSetOf(),
            wrongLetters = mutableSetOf('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з'),
            attemptsUsed = Difficulty.EASY.attempts,
            isFinished = true,
            isWin = false
        )
        val screen = GameplayScreen(console, onGameEvent = { events.add(it) }, gameData = data)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.MENU)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.ResetGame })
        verify(console).println(argThat { contains("К сожалению, вы проиграли") })
    }
}
