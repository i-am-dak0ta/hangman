package ui

import core.io.Console
import entities.Difficulty
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ui.game.DifficultySelectionScreen

class DifficultySelectionScreenTest {

    @Test
    fun `GIVEN difficulty WHEN choose 1 THEN pick EASY and goto category`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // Сложность: "1" -> Лёгкая
        whenever(console.readLine()).thenReturn("1")
        val screen = DifficultySelectionScreen(console, onGameEvent = { events.add(it) })

        val expected = ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.PickDifficulty && it.difficulty == Difficulty.EASY })
    }

    @Test
    fun `GIVEN difficulty WHEN choose 2 THEN pick MEDIUM and goto category`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // Сложность: "2" -> Нормальная
        whenever(console.readLine()).thenReturn("2")
        val screen = DifficultySelectionScreen(console, onGameEvent = { events.add(it) })

        val expected = ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.PickDifficulty && it.difficulty == Difficulty.MEDIUM })
    }

    @Test
    fun `GIVEN difficulty WHEN choose 3 THEN pick HARD and goto category`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // Сложность: "3" -> Сложная
        whenever(console.readLine()).thenReturn("3")
        val screen = DifficultySelectionScreen(console, onGameEvent = { events.add(it) })

        val expected = ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.PickDifficulty && it.difficulty == Difficulty.HARD })
    }

    @Test
    fun `GIVEN difficulty WHEN press enter THEN pick random difficulty and goto category`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // Сложность: "" -> Случайная
        whenever(console.readLine()).thenReturn("3")
        val screen = DifficultySelectionScreen(console, onGameEvent = { events.add(it) })

        val expected = ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.PickDifficulty })
    }

    @Test
    fun `GIVEN difficulty WHEN invalid input THEN stay after enter`() {
        val console = mock<Console>()
        // Статистика: "x" -> Некорректный ввод
        // Enter: "" -> Пропуск
        whenever(console.readLine()).thenReturn("x", "")
        val screen = DifficultySelectionScreen(console, onGameEvent = {})

        val expected = ScreenNavEvent.Stay
        val actual = screen.show()

        assertEquals(expected, actual)
        verify(console).println(argThat { contains("Некорректный ввод. Нажмите Enter для повторной попытки") })
    }
}
