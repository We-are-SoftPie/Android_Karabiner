package com.softpie.karabiner.ui.signup.name

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.softpie.karabiner.utiles.shortToast



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignupNameScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as Activity
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    var value by remember { mutableStateOf("")}
    val focus = LocalFocusManager.current
    val keyboardState = WindowInsets.isImeVisible
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
            Headline(text = "먼저,")
            Row {
                Title(text = "이름", karabinerable = true)
                Title(text = "을 입력해주세요")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Label(text = "본인의 실명으로 입력해주세요")
            Spacer(modifier = Modifier.height(24.dp))
            KarabinerTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                hint = "ex) 박병춘",
                value = value,
                onValueChange = {
                    value = it
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        KarabinerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (keyboardState) 0.dp else 24.dp),
            text = "다음으로",
            shape = if (keyboardState) RoundedCornerShape(0.dp) else KarabinerTheme.shape.large
        ) {
            if (value.isNotEmpty() && value.length > 1) {
                KarabinerSharedPreferences(context).myName = value
                navController.navigate(NavGroup.Auth.TEL.id)
            } else {
                context.shortToast("이름을 입력해주세요")
            }
        }
    }
}