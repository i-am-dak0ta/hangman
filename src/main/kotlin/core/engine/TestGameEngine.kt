package core.engine

import core.utils.WordMasker

class TestGameEngine(private val targetWord: String, private val userInput: String) : GameEngine {
    override fun initialize(): EngineExit {
        val trimmedTarget = targetWord.trim()
        val trimmedInput = userInput.trim()
        val regex = Regex("^[а-яА-ЯёЁ]+$")

        val errorMessage = when {
            trimmedTarget.isEmpty() || trimmedInput.isEmpty() ->
                "Ошибка: одно или оба слова пустые"

            !regex.matches(trimmedTarget) || !regex.matches(trimmedInput) ->
                "Ошибка: одно или оба слова содержат недопустимые символы (разрешены только буквы кириллицы)"

            trimmedTarget.length != trimmedInput.length ->
                "Ошибка: загаданное и введённое слово должны быть одинаковой длины"

            else -> null
        }

        return if (errorMessage != null) {
            println(errorMessage)
            EngineExit.Error
        } else {
            println(WordMasker.evaluate(targetWord, userInput))
            EngineExit.Success
        }
    }
}
