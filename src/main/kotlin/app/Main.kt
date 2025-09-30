package app

import core.dictionary.DictionaryImpl
import core.engine.GameEngine
import core.engine.InteractiveGameEngine
import core.engine.TestGameEngine
import core.io.ConsoleImpl
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val engine: GameEngine = when (args.size) {
        0 -> InteractiveGameEngine(ConsoleImpl(), DictionaryImpl())
        2 -> TestGameEngine(args[0], args[1])
        else -> {
            println("Ошибка: Неизвестные настройки игры")
            exitProcess(1)
        }
    }
    val result = engine.initialize()
    exitProcess(result.status)
}
