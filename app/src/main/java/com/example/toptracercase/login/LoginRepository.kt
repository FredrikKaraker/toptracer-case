package com.example.toptracercase.login

import javax.inject.Inject

private const val PASSWORD = "totracer"

class LoginRepository @Inject constructor() {

    fun login(username: String, password: String): Result<Unit> =
        when {
            username.isBlank() -> Result.failure(LoginFailure.NoUsername())
            password == PASSWORD -> Result.success(Unit)
            else -> Result.failure(LoginFailure.WrongPassword())
        }
}

sealed class LoginFailure : Exception() {
    class NoUsername : LoginFailure()
    class WrongPassword : LoginFailure()
}