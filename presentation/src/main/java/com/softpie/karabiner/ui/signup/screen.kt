package com.softpie.karabiner.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Title

@Preview(showBackground = true)
@Composable
private fun Preview() {
    KarabinerTheme {
        Signup()
        KarabinerButton(text = "다음으로") {

        }
    }
}

@Composable
fun Signup(
) {
    Title(text = "헬로", karabinerable = true)
}