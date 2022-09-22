package com.example.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "light theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark theme",
    group = "themes",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ThemePreviews