package presentation.state

data class MainState(
    val greeting: String = "Hello from MVI!",
    val isLoading: Boolean = false,
    val error: String? = null
)
