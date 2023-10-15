package com.softpie.karabiner.ui.signup.tel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.app.ActivityCompat
import androidx.core.text.isDigitsOnly
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
import com.softpie.karabiner.utiles.isValidPhoneNumber
import com.softpie.karabiner.utiles.shortToast



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignupTelScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as Activity
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    var value by remember { mutableStateOf("")}
    val focus = LocalFocusManager.current
    val keyboardState = WindowInsets.isImeVisible
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        Log.d("TAG", "Signup: $it")
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_NUMBERS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        } else {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val number = telephonyManager.line1Number.toString().replace("+82", "0")
            if (number.isValidPhoneNumber()) {
                value = number
            }
        }
    }
    LaunchedEffect(true) {
        launcher.launch(Manifest.permission.READ_PHONE_NUMBERS)
    }
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
            Headline(text = "다음으로,")
            Row {
                Title(text = "전화번호", karabinerable = true)
                Title(text = "를 입력해주세요")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Label(text = "연락이 가능한 본인의 번호로 입력해주세요.")
            Spacer(modifier = Modifier.height(24.dp))
            KarabinerTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                hint = "ex) 01012345678",
                value = value,
                onValueChange = {
                    if (it.isDigitsOnly()) {
                        value = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
            if (value.isNotEmpty() && value.isValidPhoneNumber()) {
                KarabinerSharedPreferences(context).myTel = value
                navController.navigate(NavGroup.Auth.EMAIL.id)
            } else {
                context.shortToast("휴대폰 번호를 입력해주세요")
            }
        }
    }
}