package com.example.login

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.ui.Paddings
import com.example.ui.ThemePreviews
import com.example.ui.TopTracerCaseTheme

@Composable
fun Login(
    viewState: LoginViewState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    navigateToWelcome: () -> Unit,
    consumeLoginEvent: () -> Unit
) {
    var alertMessage: Int? by remember { mutableStateOf(null) }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Paddings.large)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .fillMaxWidth(),
                value = viewState.username,
                onValueChange = onUsernameChanged,
                label = { Text(stringResource(R.string.username_label)) },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .fillMaxWidth(),
                value = viewState.password,
                onValueChange = onPasswordChanged,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(stringResource(R.string.password_label)) },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onLoginClicked()
                    }
                )
            )
            Row(
                modifier = Modifier
                    .padding(top = Paddings.small)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = onForgotPasswordClicked) {
                    Text(
                        stringResource(R.string.forgot_password_button),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
                Spacer(modifier = Modifier.width(Paddings.medium))
                TextButton(onClick = onLoginClicked) {
                    Text(stringResource(R.string.login_button))
                }
            }
        }
    }

    when (viewState.loginEvent) {
        LoginEvent.None -> {
            alertMessage = null
        }
        LoginEvent.NoUsername -> {
            alertMessage = R.string.login_error_username
        }
        LoginEvent.WrongPassword -> {
            alertMessage = R.string.login_error_password
        }
        LoginEvent.Success -> LaunchedEffect(Unit) { navigateToWelcome() }
        LoginEvent.UnspecifiedError -> {
            alertMessage = com.example.ui.R.string.error_generic
        }
    }

    alertMessage?.let {
        LoginDialog(message = stringResource(it), onDismiss = { consumeLoginEvent() })
    }
}

@Composable
private fun LoginDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                stringResource(R.string.login_error_title),
                style = MaterialTheme.typography.h6
            )
        },
        text = { Text(message, style = MaterialTheme.typography.body1) },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.ok_button))
            }
        }
    )
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
            navigateToWelcome = {},
            consumeLoginEvent = {}
        )
    }
}
