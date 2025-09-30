package ui

import core.io.Console
import entities.GameData
import entities.GameStatistics
import ui.game.CategorySelectionScreen
import ui.game.DifficultySelectionScreen
import ui.game.GameplayScreen
import ui.menu.MenuScreen
import ui.rules.RulesScreen
import ui.statistics.StatisticsScreen

class ScreenFactory(
    private val io: Console,
    private val gameData: () -> GameData,
    private val statistics: () -> GameStatistics,
    private val onGameEvent: (ScreenGameEvent) -> Unit
) {
    fun create(screenId: ScreenId): Screen = when (screenId) {
        ScreenId.MENU -> MenuScreen(io)
        ScreenId.RULES -> RulesScreen(io)
        ScreenId.STATISTICS -> StatisticsScreen(io, statistics())
        ScreenId.DIFFICULTY -> DifficultySelectionScreen(io, onGameEvent)
        ScreenId.CATEGORY -> CategorySelectionScreen(io, onGameEvent, gameData().categories)
        ScreenId.GAMEPLAY -> GameplayScreen(io, onGameEvent, gameData())
    }
}
