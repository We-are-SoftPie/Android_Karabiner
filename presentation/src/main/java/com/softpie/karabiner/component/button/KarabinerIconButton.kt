package com.softpie.karabiner.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.softpie.karabiner.component.modifier.karaClickable
import com.softpie.karabiner.component.modifier.karaOuterShadow
import com.softpie.karabiner.component.theme.KarabinerColor
import com.softpie.karabiner.component.theme.KarabinerRadius
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.gradient


@Composable
fun KarabinerIconBottomButton(
    modifier: Modifier = Modifier,
    iconId: Int,
    type: ButtonType = ButtonType.Black,
    karabinerable: Boolean = false,
    isShadow: Boolean = true,
    enabled: Boolean = true,
    shape: Shape = KarabinerTheme.shape.circle,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(13.dp),
    onClick: () -> Unit,
) {

    val addModifier = if (type != ButtonType.Transparent && isShadow) Modifier.karaOuterShadow(
        color = KarabinerColor.Black,
        cornerRadius = KarabinerRadius.semiLarge,
        offsetY = 4.dp
    ) else Modifier

    Surface(
        shape = shape,
        color = KarabinerColor.Transparent,
        modifier = modifier
            .then(addModifier)
            .clip(CircleShape)
            .karaClickable(
                rippleEnable = true,
                interactionSource = interactionSource
            ) { onClick() }
            .background(
                if (karabinerable) gradient else Brush.linearGradient(
                    listOf(
                        KarabinerColor.White,
                        KarabinerColor.White
                    )
                )
            )
            .padding(contentPadding),
    ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = KarabinerColor.Gray200
            )
    }
}
