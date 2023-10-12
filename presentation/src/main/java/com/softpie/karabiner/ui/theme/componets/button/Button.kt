package com.softpie.karabiner.ui.theme.componets.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.softpie.karabiner.ui.theme.KarabinerTheme
import com.softpie.karabiner.ui.theme.Title2
import com.softpie.karabiner.ui.theme.Title3


@Composable
fun KarabinerButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = KarabinerTheme.color.White,
    enabled: Boolean = true,
    shape: Shape = KarabinerTheme.shape.large,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = KarabinerTheme.color.Black),
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        border = border,
    ) {
        Title3(
            text = text,
            textColor = textColor
        )
    }
}