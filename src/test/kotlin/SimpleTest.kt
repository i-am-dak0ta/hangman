import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SimpleTest {

    @Test
    fun simpleAdditionTest() {
        val result = 2 + 2
        assertEquals(4, result, "2+2 должно быть 4")
    }

    @Test
    fun stringStartsWithHello() {
        val text = "Hello, Kotlin"
        val actualStartText = "Hello"
        assertTrue(text.startsWith(actualStartText), "Строка должна начинаться с Hello")
    }
}
