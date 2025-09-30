package core.engine

import core.dictionary.Dictionary
import core.io.Console
import entities.Difficulty
import entities.GameData
import entities.GameStatistics
import ui.ScreenFactory
import ui.ScreenGameEvent
import ui.ScreenId
import ui.ScreenNavEvent

class InteractiveGameEngine(
    private val io: Console,
    private val dictionary: Dictionary,
) : GameEngine {
    private var running = true
    private var currentId = ScreenId.MENU
    private var gameData: GameData = GameData()
    private var statistics: GameStatistics = GameStatistics()
    private val factory = ScreenFactory(
        io = io,
        gameData = { gameData },
        statistics = { statistics },
        onGameEvent = this::onGameEvent
    )

    override fun initialize(): EngineExit {
        while (running) {
            val screen = factory.create(currentId)
            val event = screen.show()
            handleNavigationEvent(event)
        }
        return EngineExit.Success
    }

    private fun handleNavigationEvent(event: ScreenNavEvent) {
        when (event) {
            is ScreenNavEvent.Stay -> Unit
            is ScreenNavEvent.NavigateTo -> currentId = event.next
            is ScreenNavEvent.Exit -> {
                io.println("\n" + "—".repeat(32) + "\n")
                io.println("Спасибо за игру!\nДо новых встреч!")
                running = false
            }
        }
    }

    private fun onGameEvent(event: ScreenGameEvent) {
        when (event) {
            is ScreenGameEvent.PickDifficulty -> pickDifficulty(event.difficulty)
            is ScreenGameEvent.PickCategoryAndWord -> pickCategoryAndWord(event.category)
            is ScreenGameEvent.PickRandomCategoryAndWord -> pickRandomCategoryAndWord()
            is ScreenGameEvent.GiveGuess -> handleGiveGuess(event.input)
            is ScreenGameEvent.ResetGame -> gameData = GameData()
        }
    }

    private fun pickDifficulty(difficulty: Difficulty) {
        gameData = gameData.copy(
            difficulty = difficulty,
            categories = dictionary.categories(difficulty)
        )
    }

    private fun pickCategoryAndWord(category: String) {
        val word = dictionary.randomWord(category, gameData.difficulty)
            ?: error("Нет слов для категории $category и сложности ${gameData.difficulty.displayName}")
        gameData = gameData.copy(category = category, targetWord = word)
    }

    private fun pickRandomCategoryAndWord() {
        val category = dictionary.randomCategory(gameData.difficulty)
            ?: error("Нет категорий для сложности ${gameData.difficulty.displayName}")
        val word = dictionary.randomWord(category, gameData.difficulty)
            ?: error("Нет слов для категории $category и сложности ${gameData.difficulty.displayName}")
        gameData = gameData.copy(category = category, targetWord = word)
    }

    private fun handleGiveGuess(input: String) {
        if (input == "+" && !gameData.isHintShown) {
            gameData = gameData.copy(isHintShown = true)
            return
        }
        val ch = validateGuess(input) ?: return
        submitGuess(ch)
    }

    private fun validateGuess(input: String): Char? {
        val cyrillicRegex = Regex("^[А-Яа-яЁё]$")
        val trimmed = input.trim()
        val validChar = when {
            trimmed.length != 1 -> null
            !cyrillicRegex.matches(trimmed) -> null
            else -> trimmed[0].lowercaseChar()
        }

        if (validChar == null || validChar in gameData.guessedLetters || validChar in gameData.wrongLetters) {
            io.println(
                "\nМожно вводить ровно одну букву кириллицы, которая ранее не вводилась. " +
                    "Нажмите Enter для продолжения."
            )
            io.readLine()
            return null
        }

        return validChar
    }

    private fun submitGuess(char: Char) {
        val wordLower = gameData.targetWord.word.lowercase()

        if (wordLower.contains(char)) {
            val newGuessed = (gameData.guessedLetters + char).toMutableSet()
            gameData = gameData.copy(guessedLetters = newGuessed)
        } else {
            val newWrong = (gameData.wrongLetters + char).toMutableSet()
            gameData = gameData.copy(
                wrongLetters = newWrong,
                attemptsUsed = gameData.attemptsUsed + 1
            )
        }

        checkEndCondition()
    }

    private fun checkEndCondition() {
        val wordLetters = gameData.targetWord.word.lowercase().filter { it.isLetter() }.toSet()
        val allRevealed = wordLetters.isNotEmpty() && wordLetters.all { it in gameData.guessedLetters }
        val lost = gameData.attemptsUsed >= gameData.difficulty.attempts

        if (allRevealed || lost) {
            val win = allRevealed && !lost
            statistics = statistics.copy(
                games = statistics.games + 1,
                wins = statistics.wins + if (win) 1 else 0,
                loses = statistics.loses + if (!win) 1 else 0
            )
            gameData = gameData.copy(isFinished = true, isWin = win)
        }
    }
}
