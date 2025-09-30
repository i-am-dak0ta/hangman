package ui

import entities.Difficulty

sealed interface ScreenGameEvent {

    data class PickDifficulty(val difficulty: Difficulty) : ScreenGameEvent
    data class PickCategoryAndWord(val category: String) : ScreenGameEvent
    object PickRandomCategoryAndWord : ScreenGameEvent
    data class GiveGuess(val input: String) : ScreenGameEvent
    object ResetGame : ScreenGameEvent
}
