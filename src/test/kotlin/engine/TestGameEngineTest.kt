package engine

import core.engine.EngineExit
import core.engine.TestGameEngine
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class TestGameEngineTest {

    @Test
    fun `GIVEN valid target and input WHEN initialize called THEN return Success`() {
        val engine = TestGameEngine("окно", "окно")
        val expected = EngineExit.Success

        val actual = engine.initialize()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN empty target WHEN initialize called THEN return Error`() {
        val engine = TestGameEngine("", "окно")
        val expected = EngineExit.Error

        val actual = engine.initialize()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN empty input WHEN initialize called THEN return Error`() {
        val engine = TestGameEngine("окно", "")
        val expected = EngineExit.Error

        val actual = engine.initialize()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN target with nonCyrillicCharacters WHEN initialize called THEN return Error`() {
        val engine = TestGameEngine("window", "окно")
        val expected = EngineExit.Error

        val actual = engine.initialize()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN input with nonCyrillicCharacters WHEN initialize called THEN return Error`() {
        val engine = TestGameEngine("окно", "1234")
        val expected = EngineExit.Error

        val actual = engine.initialize()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN target and input of different length WHEN initialize called THEN return Error`() {
        val engine = TestGameEngine("окно", "дом")
        val expected = EngineExit.Error

        val actual = engine.initialize()

        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN valid target and partially matching input WHEN initialize called THEN return Success but NEG status`() {
        val engine = TestGameEngine("волокно", "толокно")
        val expected = EngineExit.Success

        val actual = engine.initialize()

        assertEquals(expected, actual)
    }
}
