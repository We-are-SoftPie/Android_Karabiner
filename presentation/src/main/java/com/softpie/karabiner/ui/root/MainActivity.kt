package com.softpie.karabiner.ui.root

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.utiles.TAG
import com.softpie.karabiner.utiles.collectAsSideEffect
import tech.thdev.compose.extensions.keyboard.state.MutableExKeyboardStateSource
import tech.thdev.compose.extensions.keyboard.state.foundation.removeFocusWhenKeyboardIsHidden
import tech.thdev.compose.extensions.keyboard.state.localowners.LocalMutableExKeyboardStateSourceOwner

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val state by vm.state.collectAsState()
            var capture by remember { mutableStateOf(false) }
            var showBottomBar by remember { mutableStateOf(false) }
            vm.sideEffect.collectAsSideEffect {
                Log.d("TAG", "onCreate: qweeqw")
            }
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
                        Scaffold(
                            modifier = Modifier
                                .removeFocusWhenKeyboardIsHidden(),
                            bottomBar = {
                                if (showBottomBar) {
                                    MainBottomNavigation(
                                        selectedTab = state.selectedTab,
                                        selectedTabCallback = {
//                                            vm.updateSelectedTab(it)
                                            val nav = when (
                                                it
                                            ) {
                                                is NavGroup.Main.SET -> {
                                                    NavGroup.Main.SET.id
                                                }

                                                is NavGroup.Main.CAM -> {
                                                    NavGroup.Main.CAM.id
                                                }

                                                is NavGroup.Main.LIST -> {
                                                    NavGroup.Main.LIST.id
                                                }

                                                else -> {
                                                    NavGroup.Main.SET.id
                                                }
                                            }
                                            navController.navigate(nav) {
                                                launchSingleTop = true
                                            }
                                        },
                                        camCallback = {
                                            Log.d(TAG, "onCreate: vm onClick")
                                            vm.clickSideEffect()
                                            capture = !capture
                                        }
                                    )
                                }
                            }
                        ) {
                            Box(modifier = Modifier.padding(it)) {
                                NavigationGraph(
                                    navController = navController,
                                    capture = capture,
                                    changePage = {
                                        Log.d(TAG, "onCreate: 페이지 변경")
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
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KarabinerTheme {

    }
}