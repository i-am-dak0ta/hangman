package ui

import core.io.Console
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ui.game.CategorySelectionScreen

class CategorySelectionScreenTest {

    @Test
    fun `GIVEN category WHEN empty list THEN navigate back to difficulty`() {
        val console = mock<Console>()
        // Категория: "" -> Случайная
        whenever(console.readLine()).thenReturn("")
        val screen = CategorySelectionScreen(console, onGameEvent = {}, categories = listOf())

        val expected = ScreenNavEvent.NavigateTo(ScreenId.DIFFICULTY)
        val actual = screen.show()

        assertEquals(expected, actual)
        verify(console).println(
            argThat {
                contains(
                    "Нет категорий для выбранной сложности. Нажмите Enter для возврата."
                )
            }
        )
    }

    @Test
    fun `GIVEN category WHEN choose index THEN pick that category and goto gameplay`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // Категория: "1" -> 1-ая категория
        whenever(console.readLine()).thenReturn("1")
        val cats = listOf("короткие", "средние")
        val screen = CategorySelectionScreen(console, onGameEvent = { events.add(it) }, categories = cats)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.GAMEPLAY)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(
            events.any {
                it is ScreenGameEvent.PickCategoryAndWord && it.category == "короткие"
            }
        )
    }

    @Test
    fun `GIVEN category WHEN press enter THEN pick random category and goto gameplay`() {
        val events = mutableListOf<ScreenGameEvent>()
        val console = mock<Console>()
        // Категория: "" -> Случайная
        whenever(console.readLine()).thenReturn("")
        val cats = listOf("короткие", "средние")
        val screen = CategorySelectionScreen(console, onGameEvent = { events.add(it) }, categories = cats)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.GAMEPLAY)
        val actual = screen.show()

        assertEquals(expected, actual)
        assertTrue(events.any { it is ScreenGameEvent.PickRandomCategoryAndWord })
    }

    @Test
    fun `GIVEN category WHEN invalid input THEN stay after enter`() {
        val console = mock<Console>()
        // Статистика: "x" -> Некорректный ввод
        // Enter: "" -> Пропуск
        whenever(console.readLine()).thenReturn("x", "")
        val screen = CategorySelectionScreen(console, onGameEvent = {}, categories = listOf("a"))

        val expected = ScreenNavEvent.Stay
        val actual = screen.show()

        assertEquals(expected, actual)
        verify(console).println(
            argThat {
                contains(
                    "Некорректный ввод. Нажмите Enter для повторной попытки"
                )
            }
        )
    }
}
