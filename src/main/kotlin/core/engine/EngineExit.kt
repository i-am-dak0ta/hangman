package core.engine

sealed class EngineExit(val status: Int) {
    object Success : EngineExit(0)
    object Error : EngineExit(1)
}
