package core.utils

object HangmanDrawer {
    private val hangmanStages: Map<Int, String> = mapOf(
        9 to """






    =========
        """.trimIndent(),
        8 to """
          +
          |
          |
          |
          |
          |
    =========
        """.trimIndent(),
        7 to """
      +---+
          |
          |
          |
          |
          |
    =========
        """.trimIndent(),
        6 to """
      +---+
      |   |
          |
          |
          |
          |
    =========
        """.trimIndent(),
        5 to """
      +---+
      |   |
      O   |
          |
          |
          |
    =========
        """.trimIndent(),
        4 to """
      +---+
      |   |
      O   |
      |   |
          |
          |
    =========
        """.trimIndent(),
        3 to """
      +---+
      |   |
      O   |
     /|   |
          |
          |
    =========
        """.trimIndent(),
        2 to """
      +---+
      |   |
      O   |
     /|\  |
          |
          |
    =========
        """.trimIndent(),
        1 to """
      +---+
      |   |
      O   |
     /|\  |
     /    |
          |
    =========
        """.trimIndent(),
        0 to """
      +---+
      |   |
      O   |
     /|\  |
     / \  |
          |
    =========
        """.trimIndent()
    )

    fun draw(attemptsLeft: Int): String = hangmanStages[attemptsLeft] ?: ""
}
