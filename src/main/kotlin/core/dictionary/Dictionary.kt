package core.dictionary

import entities.Difficulty
import entities.GameWord

interface Dictionary {
    fun categories(difficulty: Difficulty): List<String>
    fun randomCategory(difficulty: Difficulty): String?
    fun randomWord(category: String, difficulty: Difficulty): GameWord?
}
