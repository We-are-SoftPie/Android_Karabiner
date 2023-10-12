package com.softpie.karabiner.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

class KarabinerShape(
    val small: CornerBasedShape = RoundedCornerShape(5.dp),
    val middle: CornerBasedShape = RoundedCornerShape(10.dp),
    val large: CornerBasedShape = RoundedCornerShape(20.dp),
    val cricle: CornerBasedShape = RoundedCornerShape(75.dp)
)

internal val LocalShape = staticCompositionLocalOf { KarabinerShape() }