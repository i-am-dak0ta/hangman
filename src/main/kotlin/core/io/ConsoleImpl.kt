package core.io

class ConsoleImpl : Console {
    override fun readLine() = kotlin.io.readLine() ?: ""
    override fun print(text: String) = kotlin.io.print(text)
    override fun println(text: String) = kotlin.io.println(text)
}
