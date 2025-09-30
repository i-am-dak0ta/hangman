package core.dictionary

import entities.Difficulty
import entities.GameWord
import java.io.InputStreamReader
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class DictionaryImpl(resourcePath: String = "/dictionary.json") : Dictionary {

    @Serializable
    private data class WordDto(val word: String, val hint: String)

    @Serializable
    private data class RootDto(val categories: Map<String, List<WordDto>>)

    private val categories: Map<String, List<GameWord>>

    init {
        val stream = javaClass.getResourceAsStream(resourcePath)
            ?: error("dictionary.json not found in resources")
        val text = InputStreamReader(stream, Charsets.UTF_8).readText()
        val root = Json.decodeFromString(RootDto.serializer(), text)
        categories = root.categories.mapValues { (_, list) ->
            list.map { GameWord(it.word, it.hint) }
        }
    }

    override fun categories(difficulty: Difficulty): List<String> =
        categories.filter { (_, words) -> words.any { fitsDifficulty(it, difficulty) } }.keys.toList()

    override fun randomCategory(difficulty: Difficulty): String? {
        val list = categories(difficulty)
        return list.randomOrNull()
    }

    override fun randomWord(category: String, difficulty: Difficulty): GameWord? {
        val filtered = categories[category]?.filter { fitsDifficulty(it, difficulty) }.orEmpty()
        return filtered.randomOrNull()
    }

    private fun fitsDifficulty(word: GameWord, difficulty: Difficulty): Boolean {
        val wordLength = word.word.length
        return wordLength in difficulty.wordLengthRange
    }
}
