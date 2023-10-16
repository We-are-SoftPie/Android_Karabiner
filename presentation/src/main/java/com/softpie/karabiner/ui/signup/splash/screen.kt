package com.softpie.karabiner.ui.signup.splash

import android.util.Log
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.softpie.karabiner.R
import com.softpie.karabiner.component.theme.Body
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.gradient
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.ui.root.NavGroup
import com.softpie.karabiner.utiles.TAG
import kotlinx.coroutines.delay

@OptIn(ExperimentalTextApi::class)
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current

    var visible by remember {
        mutableStateOf(false)
    }

    val textStyle = KarabinerTheme.typography.body.copy(
        lineHeight = 22.4.sp,
        fontWeight = FontWeight.Bold
    )
    LaunchedEffect(true) {
        visible = true
        delay(1000L)
        visible = false
        delay(400L)
        val karabinerSharedPreferences = KarabinerSharedPreferences(context)
//        karabinerSharedPreferences.myName = "333"
//        Log.d(TAG, "SplashScreen: ${karabinerSharedPreferences.myName}")
        var target: String? =
            if (karabinerSharedPreferences.myName == "" ) NavGroup.Auth.NAME.id
            else if(karabinerSharedPreferences.myTel == "") NavGroup.Auth.TEL.id
            else if(karabinerSharedPreferences.myEmail == "") NavGroup.Auth.EMAIL.id
            else NavGroup.Main.LIST.id
        if (target != null) {
            navController.navigate(target) {
                popUpTo(NavGroup.Auth.LAUNCH.id) { inclusive = true}
                launchSingleTop = true
            }
            return@LaunchedEffect
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000) ),
            exit = fadeOut(animationSpec = tween(500))
            ) {
            Column {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "로고"
                )
                Spacer(modifier = Modifier.height(27.dp))
                Row(Modifier.align(Alignment.CenterHorizontally),) {
                    Text(
                        text = "당신의 ",
                        style = textStyle
                    )
                    Text(
                        text = "안전",
                        style = textStyle.copy(brush = gradient)
                    )
                    Text(
                        text = "을 위하여",
                        style = textStyle
                    )
                }
                Image(
                    modifier = Modifier
                        .width(180.dp)
                        .height(48.dp),
                    painter = painterResource(id = R.drawable.ic_logo_text),
                    contentDescription = "로고 텍스트"
                )
//                Text(
//                    text = "Karabiner",
//                    style = KarabinerTheme.typography.title.copy(
//                        fontWeight = FontWeight.Medium,
//                        fontSize = 36.sp,
//                        lineHeight = 40.sp
//                    )
//                )
            }

        }
    }
}

//@Composable
//fun drawLogo() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column {
//
//        }
//    }
//}