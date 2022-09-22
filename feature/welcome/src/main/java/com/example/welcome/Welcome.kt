package com.example.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
        when (giphyResult) {
            GiphyResult.Error -> Text(stringResource(com.example.ui.R.string.error_generic))
            GiphyResult.Loading -> CircularProgressIndicator()
            is GiphyResult.Success -> {
                val title = giphyResult.giphy.title
                val username = giphyResult.giphy.username
                val url = giphyResult.giphy.url
                Gif(url = url)
                Spacer(modifier = Modifier.height(com.example.ui.Paddings.large))
                Text(
                    text = stringResource(R.string.welcome_gif_title, title, username),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun Gif(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null
    )
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
