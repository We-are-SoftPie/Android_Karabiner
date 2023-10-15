package com.softpie.karabiner.ui.signup.email

import android.app.Activity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.textfield.KarabinerTextField
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.Title
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.ui.root.NavGroup
import com.softpie.karabiner.utiles.isValidEmail
import com.softpie.karabiner.utiles.rememberKeyboardIsOpen
import com.softpie.karabiner.utiles.shortToast



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignupEmailScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as Activity
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    var value by remember { mutableStateOf("")}
    val focus = LocalFocusManager.current
    val keyboardShow by rememberKeyboardIsOpen()
    Column(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focus.clearFocus()

            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Headline(text = "마지막으로")
            Row {
                Title(text = "이메일", karabinerable = true)
                Title(text = "을 입력해주세요")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Label(text = "연락이 가능한 본인의 이메일로 넣어주세요.")
            Spacer(modifier = Modifier.height(24.dp))
            KarabinerTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                hint = "ex) byungchoon@4rne5.dev",
                value = value,
                onValueChange = {
                    value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        KarabinerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = if (keyboardShow) 0.dp else 24.dp,
                    end = if (keyboardShow) 0.dp else 24.dp,
                    bottom = if (keyboardShow) 0.dp else 16.dp,
                ),
            text = "다음으로",
            shape = if (keyboardShow) RoundedCornerShape(0.dp) else KarabinerTheme.shape.semiLarge
        ) {
            if (value.isNotEmpty() && value.isValidEmail()) {
                KarabinerSharedPreferences(context).myEmail = value
                navController.navigate(NavGroup.Auth.COMPLETE.id)
            } else {
                context.shortToast("이메일을 입력해주세요")
            }
        }
    }
}