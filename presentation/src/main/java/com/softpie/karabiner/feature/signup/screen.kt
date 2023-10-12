package com.softpie.karabiner.feature.signup

import android.telephony.TelephonyManager
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.annotations.SerializedName
import com.softpie.karabiner.ui.theme.Gradient1
import com.softpie.karabiner.ui.theme.KarabinerTheme
import com.softpie.karabiner.ui.theme.componets.button.KarabinerButton
import com.softpie.karabiner.ui.theme.gradient
import com.softpie.karabiner.ui.theme.pretendard

@Preview(showBackground = true)
@Composable
private fun Preview() {
    KarabinerTheme {
        Signup()
    }
}

@Composable
fun Signup(
) {
    Gradient1(text = "헬로")
}