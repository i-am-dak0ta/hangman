package entities

data class GameData(
    val difficulty: Difficulty = Difficulty.EASY,
    val categories: List<String> = listOf(),
    val category: String = "",
    val targetWord: GameWord = GameWord("", ""),
    val guessedLetters: MutableSet<Char> = mutableSetOf(),
    val wrongLetters: MutableSet<Char> = mutableSetOf(),
    val attemptsUsed: Int = 0,
    val isHintShown: Boolean = false,
    val isFinished: Boolean = false,
    val isWin: Boolean = false
)
