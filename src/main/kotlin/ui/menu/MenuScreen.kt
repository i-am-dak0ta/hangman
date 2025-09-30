package ui.menu

import core.io.Console
import ui.Screen
import ui.ScreenId
import ui.ScreenNavEvent

class MenuScreen(private val io: Console) : Screen {
    override fun show(): ScreenNavEvent {
        io.println("\n========= МЕНЮ =========")
        io.println("\n1 - Играть\n2 - Правила\n3 - Статистика\n0 - Выход из игры")
        io.print("\nВыберите действие: ")

        return when (io.readLine().trim()) {
            "1" -> ScreenNavEvent.NavigateTo(ScreenId.DIFFICULTY)
            "2" -> ScreenNavEvent.NavigateTo(ScreenId.RULES)
            "3" -> ScreenNavEvent.NavigateTo(ScreenId.STATISTICS)
            "0" -> ScreenNavEvent.Exit
            else -> {
                io.println("\nНекорректный ввод. Нажмите Enter для повторной попытки")
                io.readLine()
                ScreenNavEvent.Stay
            }
        }
    }
}
