package com.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.session.LoginFailure
import com.example.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState.EMPTY)
    val viewState = _viewState

    fun onLogin() {
        viewModelScope.launch {
            viewState.value.let { viewState ->
                sessionRepository.login(viewState.username, viewState.password)
                    .onSuccess { emitLoginEvent(LoginEvent.Success) }
                    .onFailure { error ->
                        val event = when (error) {
                            is LoginFailure.NoUsername -> LoginEvent.NoUsername
                            is LoginFailure.WrongPassword -> LoginEvent.WrongPassword
                            else -> LoginEvent.UnspecifiedError
                        }
                        emitLoginEvent(event)
                    }
            }
        }
    }

    fun onUsernameChanged(username: String) {
        _viewState.value = _viewState.value.copy(username = username)
    }

    fun onPasswordChanged(password: String) {
        _viewState.value = _viewState.value.copy(password = password)
    }

    fun consumeLoginEvent() {
        _viewState.value = _viewState.value.copy(loginEvent = LoginEvent.None)
    }

    private fun emitLoginEvent(event: LoginEvent) {
        _viewState.value = _viewState.value.copy(loginEvent = event)
    }
}

data class LoginViewState(val username: String, val password: String, val loginEvent: LoginEvent) {
    companion object {
        val EMPTY = LoginViewState(username = "", password = "", loginEvent = LoginEvent.None)
    }
}

enum class LoginEvent {
    None,
    NoUsername,
    WrongPassword,
    Success,
    UnspecifiedError
}