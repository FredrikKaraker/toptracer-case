package com.example.toptracercase.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.toptracercase.ui.theme.ThemePreviews
import com.example.toptracercase.ui.theme.TopTracerCaseTheme

@Composable
fun Login(
    viewState: LoginViewState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    navigateToGif: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.widthIn(max = 500.dp).fillMaxWidth(),
                value = viewState.username,
                onValueChange = onUsernameChanged,
                label = { Text("Username") },
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier.widthIn(max = 500.dp).fillMaxWidth(),
                value = viewState.password,
                onValueChange = onPasswordChanged,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password") },
                singleLine = true
            )
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                TextButton(onClick = onForgotPasswordClicked) {
                    Text(
                        "Forgot Password",
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(onClick = onLoginClicked) {
                    Text("Login")
                }
            }
        }
    }

    when (viewState.loginEvent) {
        LoginEvent.None -> {
            // NOP
        }
        LoginEvent.NoUserName -> TODO()
        LoginEvent.WrongPassword -> TODO()
        LoginEvent.Success -> navigateToGif()
        LoginEvent.UnspecifiedError -> TODO()
    }
}


@ThemePreviews
@Composable
fun LoginPreview() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    TopTracerCaseTheme {
        Login(
            viewState = LoginViewState(
                username = username,
                password = password,
                loginEvent = LoginEvent.None
            ),
            onUsernameChanged = { username = it },
            onPasswordChanged = { password = it },
            onLoginClicked = {},
            onForgotPasswordClicked = {},
            navigateToGif = {}
        )
    }
}
