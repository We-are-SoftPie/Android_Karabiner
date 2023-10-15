package com.softpie.karabiner.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softpie.karabiner.component.theme.Body
import com.softpie.karabiner.component.theme.KarabinerTheme


@Composable
fun KarabinerTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var isFocus by remember { mutableStateOf(false) }
    CompositionLocalProvider {
        BasicTextField(
            modifier = modifier
                .border(1.5.dp, KarabinerTheme.color.TextFieldStroke, RoundedCornerShape(15.dp))
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocus = it.isFocused
                },
            value = value,
            textStyle = KarabinerTheme.typography.body,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            cursorBrush = SolidColor(Black)
        ) { innerTextField ->
            Box(modifier = Modifier.padding(20.dp)) {
                innerTextField()
                if (!isFocus && value.isEmpty()) {
                    Body(
                        text = hint,
                        textColor = Color(0xFF9099A6)
                    )
                }
            }
        }
    }
}