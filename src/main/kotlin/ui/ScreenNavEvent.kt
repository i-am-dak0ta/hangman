package ui

sealed interface ScreenNavEvent {
    object Stay : ScreenNavEvent
    data class NavigateTo(val next: ScreenId) : ScreenNavEvent
    object Exit : ScreenNavEvent
}
