package com.softpie.karabiner.component.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

object KarabinerRadius {
    val small = 5.dp
    val semiMiddle = 10.dp
    val middle = 12.dp
    val semiLarge = 16.dp
    val large = 20.dp
    val circle = 50.0f
}

class KarabinerShape(
    val small: CornerBasedShape =
        RoundedCornerShape(KarabinerRadius.small),
    val semiMiddle: CornerBasedShape =
        RoundedCornerShape(KarabinerRadius.semiMiddle),
    val middle: CornerBasedShape =
        RoundedCornerShape(KarabinerRadius.middle),
    val semiLarge: CornerBasedShape =
        RoundedCornerShape(KarabinerRadius.semiLarge),
    val large: CornerBasedShape =
        RoundedCornerShape(KarabinerRadius.large),
    val circle: CornerBasedShape =
        RoundedCornerShape(KarabinerRadius.circle)
)

internal val LocalShape = staticCompositionLocalOf { KarabinerShape() }