package ui.game

import core.io.Console
import ui.Screen
import ui.ScreenGameEvent
import ui.ScreenId
import ui.ScreenNavEvent

class CategorySelectionScreen(
    private val io: Console,
    private val onGameEvent: (ScreenGameEvent) -> Unit,
    private val categories: List<String>
) : Screen {
    override fun show(): ScreenNavEvent {
        if (categories.isEmpty()) {
            io.println("Нет категорий для выбранной сложности. Нажмите Enter для возврата.")
            io.readLine()
            return ScreenNavEvent.NavigateTo(ScreenId.DIFFICULTY)
        }

        io.println("\n========= ВЫБОР КАТЕГОРИИ СЛОВА =========\n")
        categories.forEachIndexed { i, c -> io.println("${i + 1} - $c") }
        io.println("0 - Вернуться к выбору сложности")
        io.print("\nВыберите категорию (Enter - случайно): ")

        return when (val input = io.readLine().trim()) {
            in categories.indices.map { (it + 1).toString() } -> {
                val category = categories[input.toInt() - 1]
                onGameEvent(ScreenGameEvent.PickCategoryAndWord(category))
                ScreenNavEvent.NavigateTo(ScreenId.GAMEPLAY)
            }

            "" -> {
                onGameEvent(ScreenGameEvent.PickRandomCategoryAndWord)
                ScreenNavEvent.NavigateTo(ScreenId.GAMEPLAY)
            }

            "0" -> ScreenNavEvent.NavigateTo(ScreenId.DIFFICULTY)
            else -> {
                io.println("\nНекорректный ввод. Нажмите Enter для повторной попытки")
                io.readLine()
                ScreenNavEvent.Stay
            }
        }
    }
}
