package ui

import core.io.Console
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ui.menu.MenuScreen

class MenuScreenTest {

    @Test
    fun `GIVEN menu WHEN choose play THEN navigate to difficulty`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        whenever(console.readLine()).thenReturn("1")
        val screen = MenuScreen(console)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.DIFFICULTY)
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN menu WHEN choose rules THEN navigate to rules`() {
        val console = mock<Console>()
        // Меню: "2" -> Правила
        whenever(console.readLine()).thenReturn("2")
        val screen = MenuScreen(console)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.RULES)
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN menu WHEN choose statistics THEN navigate to statistics`() {
        val console = mock<Console>()
        // Меню: "3" -> Статистика
        whenever(console.readLine()).thenReturn("3")
        val screen = MenuScreen(console)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.STATISTICS)
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN menu WHEN choose exit THEN exit event`() {
        val console = mock<Console>()
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn("0")
        val screen = MenuScreen(console)

        val expected = ScreenNavEvent.Exit
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN menu WHEN invalid input THEN stay after press enter`() {
        val console = mock<Console>()
        // Меню: "x" -> Некорректный ввод
        // Enter: "" -> Пропуск
        whenever(console.readLine()).thenReturn("x", "")
        val screen = MenuScreen(console)

        val expected = ScreenNavEvent.Stay
        val actual = screen.show()

        assertEquals(expected, actual)
        verify(console).println(argThat { contains("Некорректный ввод. Нажмите Enter для повторной попытки") })
    }
}
