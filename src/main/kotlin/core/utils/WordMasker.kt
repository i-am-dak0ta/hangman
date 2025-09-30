package core.utils

object WordMasker {

    fun mask(
        target: String,
        guessed: Set<Char>,
        hidden: Char = '*',
        separator: String = ""
    ): String {
        val norm = target
        val chars = norm.map { ch ->
            if (!ch.isLetter()) {
                ch.toString()
            } else if (guessed.contains(ch)) {
                ch.toString()
            } else {
                hidden.toString()
            }
        }
        return chars.joinToString(separator)
    }

    fun evaluate(
        targetWord: String,
        userInput: String
    ): String {
        val normTarget = targetWord.trim().lowercase()
        val guessedSet = userInput
            .lowercase()
            .filter { it.isLetter() }
            .toSet()
        val masked = mask(normTarget, guessedSet)
        val status = if (masked == normTarget) "POS" else "NEG"
        return "$masked;$status"
    }
}
