package com.example.aquarium_app.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.aquarium_app.ui.utils.WindowType

@Composable
fun rememberWindowSizeClass(): WindowType {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    return when {
        screenWidthDp < 600 -> WindowType.Compact
        screenWidthDp < 840 -> WindowType.Medium
        else -> WindowType.Expanded
    }
}
