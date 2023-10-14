package com.softpie.karabiner.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.softpie.karabiner.component.modifier.karaOuterShadow
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerColor
import com.softpie.karabiner.component.theme.KarabinerRadius
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.contentColorFor
import com.softpie.karabiner.component.theme.gradient

@Composable
fun KarabinerBottomButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType = ButtonType.Black,
    karabinerable: Boolean = false,
    isShadow: Boolean = true,
    enabled: Boolean = true,
    shape: Shape = KarabinerTheme.shape.semiLarge,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(vertical = 14.dp, horizontal = 24.dp),
    onClick: () -> Unit,
) {
    val colors = if (karabinerable)
        ButtonDefaults.buttonColors(
            containerColor = KarabinerColor.White,
            disabledContainerColor = type.disableColor
        )
    else
        ButtonDefaults.buttonColors(
            containerColor = type.buttonColor,
            disabledContainerColor = type.disableColor
        )

    val addModifier = if (type != ButtonType.Transparent && isShadow) Modifier.karaOuterShadow(
        color = KarabinerColor.Black,
        cornerRadius = KarabinerRadius.semiLarge,
        offsetY = 4.dp
    ) else Modifier
    Button(
        modifier = modifier
            .then(addModifier),
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        interactionSource = interactionSource,
        contentPadding = PaddingValues(),
        border = border,
    ) {
        Surface(
            color = KarabinerColor.Transparent,
            modifier = Modifier
                .background(
                    if (karabinerable) gradient else Brush.linearGradient(
                        listOf(
                            KarabinerColor.Transparent,
                            KarabinerColor.Transparent
                        )
                    )
                )
                .padding(contentPadding)
                .then(modifier),
        ) {
            Headline(
                modifier = Modifier,
                text = text,
                textColor = if (karabinerable) KarabinerColor.White else contentColorFor(
                    if (enabled) type.buttonColor
                    else type.disableColor
                )
            )
        }
    }
}