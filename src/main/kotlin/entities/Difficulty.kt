package entities

enum class Difficulty(val attempts: Int, val wordLengthRange: IntRange, val displayName: String) {
    EASY(9, 1..4, "Лёгкая"),
    MEDIUM(6, 4..7, "Нормальная"),
    HARD(3, 7..10, "Сложная")
}
