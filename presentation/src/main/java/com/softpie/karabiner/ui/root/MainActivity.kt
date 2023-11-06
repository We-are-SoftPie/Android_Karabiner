package com.softpie.karabiner.ui.root

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.utiles.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.thdev.compose.extensions.keyboard.state.MutableExKeyboardStateSource
import tech.thdev.compose.extensions.keyboard.state.foundation.removeFocusWhenKeyboardIsHidden
import tech.thdev.compose.extensions.keyboard.state.localowners.LocalMutableExKeyboardStateSourceOwner

val ClickComposableLocalStatic = compositionLocalOf<CamEvent> { CamEvent.Click }

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val state by vm.state.collectAsState()
            var capture by remember { mutableStateOf(false) }
            var showBottomBar by remember { mutableStateOf(false) }
            var camEvent: CamEvent by remember { mutableStateOf(CamEvent.None) }
            val coroutine = rememberCoroutineScope()
            KarabinerTheme {
                Box(
                    modifier = if (showBottomBar) Modifier.windowInsetsPadding(WindowInsets.systemBars) else Modifier
                ) {
                    Box(
                        modifier = if (showBottomBar) Modifier.windowInsetsPadding(WindowInsets.safeDrawing) else Modifier
                    )
                    CompositionLocalProvider(
                        LocalMutableExKeyboardStateSourceOwner provides MutableExKeyboardStateSource() // 2
                    ) {
                        CompositionLocalProvider(ClickComposableLocalStatic provides camEvent) {
                            Scaffold(
                                modifier = Modifier
                                    .removeFocusWhenKeyboardIsHidden(),
                                bottomBar = {
                                    if (showBottomBar) {
                                        MainBottomNavigation(
                                            selectedTab = state.selectedTab,
                                            selectedTabCallback = {
                                                navController.navigate(it.id) {
                                                    launchSingleTop = true
                                                }
                                            },
                                            camCallback = {
                                                camEvent = CamEvent.Click
                                                coroutine.launch {
                                                    delay(10)
                                                    camEvent = CamEvent.None
                                                }
                                            }
                                        )
                                    }
                                }
                            ) { it ->
                                Box(modifier = Modifier.padding(
                                    top = if (showBottomBar) 0.dp else it.calculateTopPadding(),
                                    bottom = it.calculateBottomPadding()
                                )
                                ) {
                                    NavigationGraph(
                                        navController = navController,
                                        changePage = {
                                            capture = false
                                        },
                                        onChangeNav = {
                                            vm.updateSelectedTab(it)
                                        }
                                    ) {
                                        if (showBottomBar != it) {
                                            showBottomBar = it
                                        }
                                    }
                                }
                            }
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