package com.softpie.karabiner.ui.root

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.softpie.karabiner.component.button.ButtonType
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.theme.Body
import com.softpie.karabiner.component.theme.Caption
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.Title
import tech.thdev.compose.extensions.keyboard.state.MutableExKeyboardStateSource
import tech.thdev.compose.extensions.keyboard.state.foundation.removeFocusWhenKeyboardIsHidden
import tech.thdev.compose.extensions.keyboard.state.localowners.LocalMutableExKeyboardStateSourceOwner

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            KarabinerTheme {

                Box(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.systemBars)
                ) {
                    Box(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.safeDrawing)
                    )
                    CompositionLocalProvider(
                        LocalMutableExKeyboardStateSourceOwner provides MutableExKeyboardStateSource() // 2
                    ) {
                        Scaffold(
                            modifier = Modifier
                                .removeFocusWhenKeyboardIsHidden()
                        ) {
                            NavigationGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KarabinerTheme {

    }
}