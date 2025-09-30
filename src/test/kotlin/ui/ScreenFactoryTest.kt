package ui

import core.io.Console
import entities.GameData
import entities.GameStatistics
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ui.game.CategorySelectionScreen
import ui.game.DifficultySelectionScreen
import ui.game.GameplayScreen
import ui.menu.MenuScreen
import ui.rules.RulesScreen
import ui.statistics.StatisticsScreen

class ScreenFactoryTest {

    @Test
    fun `GIVEN factory WHEN create each id THEN returns corresponding screen type`() {
        val console = mock<Console>()
        whenever(console.readLine()).thenReturn("0")
        val gameData = GameData()
        val statistics = GameStatistics()
        val factory = ScreenFactory(
            io = console,
            gameData = { gameData },
            statistics = { statistics },
            onGameEvent = {}
        )

        assertTrue(factory.create(ScreenId.MENU) is MenuScreen)
        assertTrue(factory.create(ScreenId.RULES) is RulesScreen)
        assertTrue(factory.create(ScreenId.STATISTICS) is StatisticsScreen)
        assertTrue(factory.create(ScreenId.DIFFICULTY) is DifficultySelectionScreen)
        assertTrue(factory.create(ScreenId.CATEGORY) is CategorySelectionScreen)
        assertTrue(factory.create(ScreenId.GAMEPLAY) is GameplayScreen)
    }
}
