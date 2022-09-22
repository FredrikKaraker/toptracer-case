package com.example.toptracercase.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.toptracercase.R
import com.example.toptracercase.ui.theme.ThemePreviews
import com.example.toptracercase.ui.theme.TopTracerCaseTheme

@Composable
fun Welcome(viewState: WelcomeViewState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.welcome_greeting, viewState.username.orEmpty()))
            Giphy(giphy = viewState.giphy)
        }
    }
}

@Composable
private fun Giphy(giphy: GiphyResult) {
    Box(
        modifier = Modifier.sizeIn(minWidth = 200.dp, minHeight = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        when (giphy) {
            GiphyResult.Error -> Text(stringResource(R.string.error_generic))
            GiphyResult.Loading -> CircularProgressIndicator()
            is GiphyResult.Success -> {

            }
        }
    }
}

@ThemePreviews
@Composable
fun WelcomePreview() {
    TopTracerCaseTheme {
        Welcome(
            WelcomeViewState(username = "Fredrik", giphy = GiphyResult.Loading )
        )

    }
}
