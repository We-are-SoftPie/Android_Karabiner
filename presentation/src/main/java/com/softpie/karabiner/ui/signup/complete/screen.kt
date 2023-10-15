package com.softpie.karabiner.ui.signup.complete

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.softpie.karabiner.R
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignupCompleteScreen(
    navController: NavController
) {
    val lottieAnime by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
    val focus = LocalFocusManager.current
    Column(modifier = Modifier
        .fillMaxSize()
        .imePadding()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focus.clearFocus()

            })
        }
    ) {

        Spacer(modifier = Modifier.height(31.dp))
        LottieAnimation(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize(),
            composition = lottieAnime,
            restartOnPlay = false)
        Headline(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "모두 완료되었습니다."
        )
        Spacer(modifier = Modifier.weight(1f))
        KarabinerButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                ),
            text = "완료하기",
            shape = KarabinerTheme.shape.semiLarge
        ) {

        }
    }
}