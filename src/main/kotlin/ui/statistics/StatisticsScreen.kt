package ui.statistics

import core.io.Console
import entities.GameStatistics
import ui.Screen
import ui.ScreenId
import ui.ScreenNavEvent

class StatisticsScreen(
    private val io: Console,
    private val statistics: GameStatistics
) : Screen {
    override fun show(): ScreenNavEvent {
        io.println("\n========= СТАТИСТИКА =========")
        io.println("\n• Кол-во сыгранных игр: ${statistics.games}")
        io.println("• Кол-во побед: ${statistics.wins}")
        io.println("• Кол-во поражений: ${statistics.loses}")
        io.println("\n1 - Вернуться в меню")
        io.println("0 - Выход из игры")
        io.print("\nВыберите действие: ")

        return when (io.readLine().trim()) {
            "1" -> ScreenNavEvent.NavigateTo(ScreenId.MENU)
            "0" -> ScreenNavEvent.Exit
            else -> {
                io.println("\nНекорректный ввод. Нажмите Enter для повторной попытки")
                io.readLine()
                ScreenNavEvent.Stay
            }
        }
    }
}
