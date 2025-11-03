package com.example.anaganaga.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anaganaga.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    private val _loginErrors = MutableStateFlow<List<String>>(emptyList())
    val loginErrors: StateFlow<List<String>> = _loginErrors

    fun login(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            _loginSuccess.value = result.success
            _loginErrors.value = result.errors
            _loading.value = false
        }
    }
}
