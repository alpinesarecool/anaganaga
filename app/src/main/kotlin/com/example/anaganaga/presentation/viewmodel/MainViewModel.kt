package presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import presentation.intent.MainIntent
import presentation.state.MainState

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    private val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    fun sendIntent(intent: MainIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.receiveAsFlow().collect { intent ->
                when (intent) {
                    is MainIntent.LoadGreeting -> loadGreeting()
                    is MainIntent.ChangeGreeting -> updateGreeting(intent.newGreeting)
                }
            }
        }
    }

    private fun loadGreeting() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            // simulate loading
            delay(1000)
            _state.value = _state.value.copy(
                isLoading = false,
                greeting = "Welcome to Anaganaga!"
            )
        }
    }

    private fun updateGreeting(newGreeting: String) {
        _state.value = _state.value.copy(greeting = newGreeting)
    }
}
