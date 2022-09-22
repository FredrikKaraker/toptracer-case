package com.example.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    if (viewState.username == null) {
        onLogout()
    }
    Welcome(
        viewState = viewState,
        onLogout = {
            viewModel.logout()
            onLogout()
        }
    )
}