package com.softpie.karabiner.component.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

// - clickable
// - ripple
// - bounded
fun Modifier.karaClickable(
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    onClick: (() -> Unit)? = null
) = composed {
    onClick?.let { onClick ->
        clickable(
            interactionSource = interactionSource,
            indication = if (rippleEnable)
                rememberRipple(
                    color = rippleColor,
                    bounded = bounded
                ) else null,
            onClick = onClick
        )
    } ?: this
}