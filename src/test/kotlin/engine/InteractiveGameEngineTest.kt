package engine

import core.dictionary.Dictionary
import core.engine.EngineExit
import core.engine.InteractiveGameEngine
import core.io.Console
import entities.Difficulty
import entities.GameWord
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class InteractiveGameEngineTest {

    private val testWord = GameWord("кот", "мяукает")

    @Test
    fun `GIVEN dictionary and console WHEN player exits from menu THEN game finishes`() {
        val console = mock<Console>()
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn("0")

        val dictionary = mock<Dictionary>()
        val engine = InteractiveGameEngine(console, dictionary)

        val expected = EngineExit.Success
        val actual = engine.initialize()

        assertEquals(expected, actual)
        verify(console).println(argThat { contains("Спасибо за игру!\nДо новых встреч!") })
    }

    @Test
    fun `GIVEN dictionary and console WHEN invalid input THEN prompts retry`() {
        val console = mock<Console>()
        // Меню: "x" -> Некорректный ввод
        // Enter: "" -> Пропуск
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn("x", "", "0")

        val dictionary = mock<Dictionary>()
        val engine = InteractiveGameEngine(console, dictionary)

        engine.initialize()

        verify(console).println(argThat { contains("Некорректный ввод. Нажмите Enter для повторной попытки") })
    }

    @Test
    fun `GIVEN dictionary and console WHEN complete gameplay THEN player wins`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ходы: "к", "о", "т"
        // После победы: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn("1", "1", "1", "к", "о", "т", "", "0")

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        val actual = engine.initialize()
        val expected = EngineExit.Success

        assertEquals(expected, actual)
        verify(console, atLeastOnce()).println(argThat { contains("Поздравляем - Вы выиграли") })
    }

    @Test
    fun `GIVEN dictionary and console WHEN player loses THEN shows losing message`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ходы: "а","б","в","г","д","е","ё","ж","з" -> все ошибки
        // После поражения: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn(
            "1", "1", "1", "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "", "0"
        )

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        val actual = engine.initialize()
        val expected = EngineExit.Success

        assertEquals(expected, actual)
        verify(console, atLeastOnce()).println(argThat { contains("К сожалению, вы проиграли") })
    }

    @Test
    fun `GIVEN dictionary and console WHEN player requests hint THEN hint is shown`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ход: "+" -> Подсказка
        // Ходы: "к","о","т"
        // После победы: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn(
            "1", "1", "1", "+", "к", "о", "т", "", "0"
        )

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        engine.initialize()

        verify(console, atLeastOnce()).println(argThat { contains("Подсказка:") })
    }

    @Test
    fun `GIVEN dictionary and console WHEN player requests hint twice THEN shows invalid input message second time`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ход: "+" -> Первая подсказка
        // Ход: "+" -> Вторая подсказка (ошибка)
        // Enter: "" -> Пропуск после ошибки
        // Ходы: "к","о","т"
        // После победы: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn(
            "1", "1", "1", "+", "+", "", "к", "о", "т", "", "0"
        )

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        engine.initialize()

        verify(console, atLeastOnce()).println(
            argThat {
                contains("Можно вводить ровно одну букву кириллицы, которая ранее не вводилась.")
            }
        )
    }

    @Test
    fun `GIVEN dictionary and console WHEN player inputs already guessed letter THEN shows invalid input message`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ход: "к" -> Первая попытка
        // Ход: "к" -> Повтор (ошибка)
        // Enter: "" -> Пропуск
        // Ходы: "о","т"
        // После победы: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn(
            "1", "1", "1", "к", "к", "", "о", "т", "", "0"
        )

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        engine.initialize()

        verify(console, atLeastOnce()).println(
            argThat {
                contains(
                    "Можно вводить ровно одну букву кириллицы, которая ранее не вводилась."
                )
            }
        )
    }

    @Test
    fun `GIVEN dictionary and console WHEN player inputs english letter THEN shows invalid input message`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ход: "k" -> Английская буква (ошибка)
        // Enter: "" -> Пропуск
        // Ходы: "к","о","т"
        // После победы: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn(
            "1", "1", "1", "k", "", "к", "о", "т", "", "0"
        )

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        engine.initialize()

        verify(console, atLeastOnce()).println(
            argThat {
                contains(
                    "Можно вводить ровно одну букву кириллицы, которая ранее не вводилась."
                )
            }
        )
    }

    @Test
    fun `GIVEN dictionary and console WHEN player inputs digit THEN shows invalid input message`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ход: "5" -> Цифра (ошибка)
        // Enter: "" -> Пропуск
        // Ходы: "к","о","т"
        // После победы: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn(
            "1", "1", "1", "5", "", "к", "о", "т", "", "0"
        )

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        engine.initialize()

        verify(console, atLeastOnce()).println(
            argThat {
                contains(
                    "Можно вводить ровно одну букву кириллицы, которая ранее не вводилась."
                )
            }
        )
    }

    @Test
    fun `GIVEN dictionary and console WHEN player inputs symbol THEN shows invalid input message`() {
        val console = mock<Console>()
        // Меню: "1" -> Играть
        // Сложность: "1" -> Лёгкая
        // Категория: "1" -> Животные
        // Ход: "@" -> Символ (ошибка)
        // Enter: "" -> Пропуск
        // Ходы: "к","о","т"
        // После победы: "" -> Enter
        // Меню: "0" -> Выход
        whenever(console.readLine()).thenReturn(
            "1", "1", "1", "@", "", "к", "о", "т", "", "0"
        )

        val dictionary = mock<Dictionary>().apply {
            whenever(categories(Difficulty.EASY)).thenReturn(listOf("животные"))
            whenever(randomWord("животные", Difficulty.EASY)).thenReturn(testWord)
        }

        val engine = InteractiveGameEngine(console, dictionary)
        engine.initialize()

        verify(console, atLeastOnce()).println(
            argThat {
                contains(
                    "Можно вводить ровно одну букву кириллицы, которая ранее не вводилась."
                )
            }
        )
    }
}
