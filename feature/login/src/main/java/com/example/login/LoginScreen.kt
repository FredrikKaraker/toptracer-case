package com.example.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToWelcome: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Login(
        viewState = viewState,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onLoginClicked = viewModel::onLogin,
        onForgotPasswordClicked = {},
        navigateToWelcome = navigateToWelcome,
        consumeLoginEvent = viewModel::consumeLoginEvent
    )
}