package com.example.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Welcome(
    viewState: WelcomeViewState,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(com.example.ui.Paddings.large)
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.welcome_greeting, viewState.username.orEmpty()),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(com.example.ui.Paddings.large))
        Giphy(giphyResult = viewState.giphy)
        Spacer(modifier = Modifier.height(com.example.ui.Paddings.medium))
        TextButton(onClick = onLogout) {
            Text(stringResource(R.string.logout_button))
        }
    }
}

@Composable
private fun Giphy(giphyResult: GiphyResult) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Gif(giphyResult)
        when (giphyResult) {
            GiphyResult.Error -> Text(stringResource(com.example.ui.R.string.error_generic))
            is GiphyResult.Success -> {
                val title = giphyResult.giphy.title
                val username = giphyResult.giphy.username
                Spacer(modifier = Modifier.height(com.example.ui.Paddings.large))
                Text(
                    text = stringResource(R.string.welcome_gif_title, title, username),
                    textAlign = TextAlign.Center
                )
            }
            else -> {}
        }
    }
}

@Composable
private fun Gif(giphyResult: GiphyResult) {
    var isLoading by remember { mutableStateOf(true) }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.heightIn(min = 100.dp)) {
        if (giphyResult is GiphyResult.Success) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(giphyResult.giphy.url)
                    .crossfade(true)
                    .listener(
                        onSuccess = { _, _ ->
                            isLoading = false
                        }
                    )
                    .build(),
                contentDescription = null
            )
        }
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@com.example.ui.ThemePreviews
@Composable
fun WelcomePreview() {
    com.example.ui.TopTracerCaseTheme {
        Welcome(
            WelcomeViewState(username = "Fredrik", giphy = GiphyResult.Loading),
            onLogout = {}
        )

    }
}
