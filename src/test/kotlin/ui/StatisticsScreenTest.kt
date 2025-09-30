package ui

import core.io.Console
import entities.GameStatistics
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ui.statistics.StatisticsScreen

class StatisticsScreenTest {

    @Test
    fun `GIVEN statistics WHEN showing THEN prints correct values`() {
        val console = mock<Console>()
        // Статистика: "1" -> Вернуться в меню
        whenever(console.readLine()).thenReturn("1")
        val stats = GameStatistics(games = 5, wins = 3, loses = 2)
        val screen = StatisticsScreen(console, stats)

        screen.show()

        verify(console).println(argThat { contains("• Кол-во сыгранных игр: 5") })
        verify(console).println(argThat { contains("• Кол-во побед: 3") })
        verify(console).println(argThat { contains("• Кол-во поражений: 2") })
    }

    @Test
    fun `GIVEN statistics WHEN choose 1 THEN back to menu`() {
        val console = mock<Console>()
        // Статистика: "1" -> Вернуться в меню
        whenever(console.readLine()).thenReturn("1")
        val stats = GameStatistics()
        val screen = StatisticsScreen(console, stats)

        val expected = ScreenNavEvent.NavigateTo(ScreenId.MENU)
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN statistics WHEN choose exit THEN exit event`() {
        val console = mock<Console>()
        // Статистика: "0" -> Выход
        whenever(console.readLine()).thenReturn("0")
        val stats = GameStatistics()
        val screen = StatisticsScreen(console, stats)

        val expected = ScreenNavEvent.Exit
        val actual = screen.show()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN statistics WHEN invalid input THEN stay after press enter`() {
        val console = mock<Console>()
        // Статистика: "x" -> Некорректный ввод
        // Enter: "" -> Пропуск
        whenever(console.readLine()).thenReturn("x", "")
        val stats = GameStatistics()
        val screen = StatisticsScreen(console, stats)

        val expected = ScreenNavEvent.Stay
        val actual = screen.show()

        assertEquals(expected, actual)
        verify(console).println(argThat { contains("Некорректный ввод. Нажмите Enter для повторной попытки") })
    }
}
