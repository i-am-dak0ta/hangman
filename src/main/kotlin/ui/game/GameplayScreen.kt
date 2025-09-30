package ui.game

import core.io.Console
import core.utils.HangmanDrawer
import core.utils.WordMasker
import entities.GameData
import ui.Screen
import ui.ScreenGameEvent
import ui.ScreenId
import ui.ScreenNavEvent

class GameplayScreen(
    private val io: Console,
    private val onGameEvent: (ScreenGameEvent) -> Unit,
    private val gameData: GameData
) : Screen {
    override fun show(): ScreenNavEvent {
        val attemptsLeft = gameData.difficulty.attempts - gameData.attemptsUsed
        val hangman = HangmanDrawer.draw(attemptsLeft)
        val masked = WordMasker.mask(
            target = gameData.targetWord.word,
            guessed = gameData.guessedLetters,
            hidden = '_',
            separator = " "
        )
        io.println("\n========= ИГРА «ВИСЕЛИЦА» =========")
        io.println("Сложность: ${gameData.difficulty.displayName}")
        io.println("Категория: ${gameData.category}")
        io.println(hangman)
        io.println("Попытки: $attemptsLeft/${gameData.difficulty.attempts}")
        io.println(masked)
        if (gameData.isHintShown) io.println("Подсказка: ${gameData.targetWord.hint}")
        io.println("Угаданные буквы: ${gameData.guessedLetters.toList().sorted().joinToString()}")
        io.println("Неправильные буквы: ${gameData.wrongLetters.toList().sorted().joinToString()}")

        if (gameData.isFinished) {
            if (gameData.isWin) {
                io.println("\nПоздравляем - Вы выиграли! Загаданное слово: ${gameData.targetWord.word}")
            } else {
                io.println("\nК сожалению, вы проиграли. Загаданное слово: ${gameData.targetWord.word}")
            }
            io.println("\nНажмите Enter, чтобы вернуться в меню.")
            io.readLine()
            onGameEvent(ScreenGameEvent.ResetGame)
            return ScreenNavEvent.NavigateTo(ScreenId.MENU)
        }

        io.print("\nВведите букву${if (!gameData.isHintShown) " или '+' для подсказки" else ""}: ")
        val input = io.readLine().trim()

        onGameEvent(ScreenGameEvent.GiveGuess(input))

        return ScreenNavEvent.Stay
    }
}
