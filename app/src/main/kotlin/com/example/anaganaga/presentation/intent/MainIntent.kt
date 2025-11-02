package presentation.intent

sealed class MainIntent {
    object LoadGreeting : MainIntent()
    data class ChangeGreeting(val newGreeting: String) : MainIntent()
}
