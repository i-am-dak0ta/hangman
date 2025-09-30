package ui.rules

import core.io.Console
import ui.Screen
import ui.ScreenId
import ui.ScreenNavEvent

class RulesScreen(private val io: Console) : Screen {
    override fun show(): ScreenNavEvent {
        io.println("\n========= ПРАВИЛА =========")
        io.println("\n• В начале игры выберите уровень сложности и категорию слов;")
        io.println("• Из выбранной категории будет случайно загадано слово;")
        io.println("• Ваша задача угадать слово, вводя буквы по одной;")
        io.println("• Если буква есть в слове, то она откроется на своём месте;")
        io.println("• Если буквы нет, то добавляется часть фигурки висельника;")
        io.println("• Количество ошибок ограничено и зависит от выбранного уровня сложности;")
        io.println("• Игра закончится, когда будет угадано слово или висельник будет полностью нарисован.")
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
