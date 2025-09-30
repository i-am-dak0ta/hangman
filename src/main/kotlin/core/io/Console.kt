package core.io

interface Console {
    fun readLine(): String
    fun print(text: String)
    fun println(text: String = "")
}
