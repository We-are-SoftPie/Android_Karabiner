package com.softpie.karabiner.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softpie.karabiner.R
import com.softpie.karabiner.component.appbar.bottomNavHeight
import com.softpie.karabiner.component.modifier.karaClickable
import com.softpie.karabiner.component.modifier.karaOuterShadow
import com.softpie.karabiner.component.theme.KarabinerColor
import com.softpie.karabiner.component.theme.KarabinerRadius
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.contentColorFor
import com.softpie.karabiner.component.theme.gradient


@Composable
fun KarabinerBottomButton(
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    karabinerable: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    Surface(
        color = KarabinerColor.Transparent,
        modifier = modifier
            .background(
                Brush.linearGradient(
                    listOf(
                        KarabinerColor.Transparent,
                        KarabinerColor.Transparent
                    )
                )
            )
            .karaClickable(
                interactionSource = interactionSource,
                rippleEnable = enable,
                bounded = false,
                onClick = onClick
            )
    ) {
        Column(
            modifier = if (karabinerable) Modifier
                .height(bottomNavHeight)
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.SrcAtop)
                    }
                } else Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = KarabinerColor.Gray200
            )
            Spacer(modifier = Modifier.height(6.dp))
            Label(
                modifier = Modifier,
                text = text,
                karabinerable = karabinerable,
                textColor = KarabinerColor.Gray200
            )
        }
    }
}

