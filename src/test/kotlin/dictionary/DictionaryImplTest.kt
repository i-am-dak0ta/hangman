package dictionary

import core.dictionary.DictionaryImpl
import entities.Difficulty
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DictionaryImplTest {

    private val dictionary = DictionaryImpl("/test-dictionary.json")

    @Test
    fun `GIVEN dictionary WHEN categories called with easy THEN return only categories with short words`() {
        val categories = dictionary.categories(Difficulty.EASY)
        assertTrue("короткие" in categories)
        assertTrue("средние" !in categories)
        assertTrue("длинные" !in categories)
    }

    @Test
    fun `GIVEN dictionary WHEN categories called with medium THEN return only categories with medium words`() {
        val categories = dictionary.categories(Difficulty.MEDIUM)
        assertTrue("средние" in categories)
        assertTrue("короткие" !in categories)
        assertTrue("длинные" !in categories)
    }

    @Test
    fun `GIVEN dictionary WHEN categories called with hard THEN return only categories with long words`() {
        val categories = dictionary.categories(Difficulty.HARD)
        assertTrue("длинные" in categories)
        assertTrue("короткие" !in categories)
        assertTrue("средние" !in categories)
    }

    @Test
    fun `GIVEN dictionary WHEN randomCategory called with easy THEN return category from easy categories`() {
        val category = dictionary.randomCategory(Difficulty.EASY)
        assertNotNull(category)
        assertEquals(true, category in listOf("короткие"))
    }

    @Test
    fun `GIVEN dictionary WHEN randomCategory called with medium THEN return category from medium categories`() {
        val category = dictionary.randomCategory(Difficulty.MEDIUM)
        assertNotNull(category)
        assertEquals(true, category in listOf("средние"))
    }

    @Test
    fun `GIVEN dictionary WHEN randomCategory called with hard THEN return category from hard categories`() {
        val category = dictionary.randomCategory(Difficulty.HARD)
        assertNotNull(category)
        assertEquals(true, category in listOf("длинные"))
    }

    @Test
    fun `GIVEN dictionary WHEN randomCategory called with hard and no long words THEN return null`() {
        val emptyDictionary = DictionaryImpl("/empty-dictionary.json")
        val category = emptyDictionary.randomCategory(Difficulty.HARD)
        assertNull(category)
    }

    @Test
    fun `GIVEN invalid resource path WHEN DictionaryImpl initialized THEN throw error`() {
        val exception = assertFailsWith<IllegalStateException> {
            DictionaryImpl("/nonexistent.json")
        }
        assertTrue(exception.message!!.contains("dictionary.json not found"))
    }

    @Test
    fun `GIVEN dictionary WHEN randomWord with matching words THEN return fitting word`() {
        val word = dictionary.randomWord("короткие", Difficulty.EASY)
        assertNotNull(word)
        assertTrue(word.word.length in Difficulty.EASY.wordLengthRange)
    }

    @Test
    fun `GIVEN dictionary WHEN randomWord with no matching words THEN return null`() {
        val word = dictionary.randomWord("длинные", Difficulty.EASY)
        assertNull(word)
    }
}
