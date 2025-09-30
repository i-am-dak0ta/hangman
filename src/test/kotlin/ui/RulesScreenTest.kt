package ui

import core.io.Console
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ui.rules.RulesScreen

class RulesScreenTest {

    @Test
    fun `GIVEN rules WHEN choose 1 THEN back to menu`() {
        val console = mock<Console>()
        // Правила: "1" -> Вернуться в меню
        whenever(console.readLine()).thenReturn("1")
        val screen = RulesScreen(console)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.MENU)
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN rules WHEN choose exit THEN exit event`() {
        val console = mock<Console>()
        // Правила: "0" -> Выход
        whenever(console.readLine()).thenReturn("0")
        val screen = RulesScreen(console)

        val expected = ScreenNavEvent.Exit
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN rules WHEN invalid input THEN stay after press enter`() {
        val console = mock<Console>()
        // Правила: "x" -> Некорректный ввод
        // Enter: "" -> Пропуск
        whenever(console.readLine()).thenReturn("x", "")
        val screen = RulesScreen(console)

        val expected = ScreenNavEvent.Stay
        val actual = screen.show()

        assertEquals(expected, actual)
        verify(console).println(argThat { contains("Некорректный ввод. Нажмите Enter для повторной попытки") })
    }
}
