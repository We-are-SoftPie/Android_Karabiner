package com.softpie.karabiner.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


data class KarabinerColor (
    val White: Color = Color(0xFFFFFFFF),
    val Black: Color = Color(0xFF000000),

    val Red: Color = Color(0xFFFC0D0D),
    val Blue: Color = Color(0xFF0E3BDB),
)

internal val LocalColor = compositionLocalOf { KarabinerColor() }