package ui.game

import core.io.Console
import entities.Difficulty
import ui.Screen
import ui.ScreenGameEvent
import ui.ScreenId
import ui.ScreenNavEvent

class DifficultySelectionScreen(
    private val io: Console,
    private val onGameEvent: (ScreenGameEvent) -> Unit
) : Screen {
    override fun show(): ScreenNavEvent {
        io.println("\n========= ВЫБОР УРОВНЯ СЛОЖНОСТИ =========")
        io.println("\n1 - Легкая\n2 - Средняя\n3 - Тяжёлая\n0 - Вернуться в меню")
        io.print("\nВыберите сложность (Enter - случайно): ")

        return when (io.readLine().trim()) {
            "1" -> {
                onGameEvent(ScreenGameEvent.PickDifficulty(Difficulty.EASY))
                ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
            }

            "2" -> {
                onGameEvent(ScreenGameEvent.PickDifficulty(Difficulty.MEDIUM))
                ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
            }

            "3" -> {
                onGameEvent(ScreenGameEvent.PickDifficulty(Difficulty.HARD))
                ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
            }

            "" -> {
                val difficulty = listOf(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD).random()
                onGameEvent(ScreenGameEvent.PickDifficulty(difficulty))
                ScreenNavEvent.NavigateTo(ScreenId.CATEGORY)
            }

            "0" -> ScreenNavEvent.NavigateTo(ScreenId.MENU)
            else -> {
                io.println("\nНекорректный ввод. Нажмите Enter для повторной попытки")
                io.readLine()
                ScreenNavEvent.Stay
            }
        }
    }
}
