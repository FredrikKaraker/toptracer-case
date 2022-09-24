package com.example.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

private const val PASSWORD = "password"

@Singleton
class SessionRepository @Inject constructor() {

    private val _currentUser: MutableStateFlow<LoggedInUser?> = MutableStateFlow(null)
    val currentUser = _currentUser

    fun login(username: String, password: String): Result<Unit> =
        when {
            username.isBlank() -> Result.failure(LoginFailure.NoUsername)
            password == PASSWORD -> Result.success(Unit).also { setCurrentUser(username) }
            else -> Result.failure(LoginFailure.WrongPassword)
        }

    fun logout() {
        _currentUser.update { null }
    }

    private fun setCurrentUser(username: String) {
        _currentUser.update { LoggedInUser(username) }
    }
}

data class LoggedInUser(val username: String)

sealed class LoginFailure : Exception() {
    object NoUsername : LoginFailure()
    object WrongPassword : LoginFailure()
}