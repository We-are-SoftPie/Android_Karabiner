package com.softpie.karabiner.ui.ee

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.softpie.karabiner.ui.root.MainBottomNavigation
import com.softpie.karabiner.ui.root.MainViewModel
import com.softpie.karabiner.utiles.TAG

@Composable
fun EeScreen(
    navController: NavController,
    vm: MainViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    var bottomVisible by remember { mutableStateOf(true) }
    Log.d(TAG, "MainScreen: ${state.selectedTab.id}")
    Log.d(TAG, "MainScreen: qeweqweweq")
    Scaffold(
        bottomBar = {
            if (bottomVisible) {
                MainBottomNavigation(
                    selectedTab = state.selectedTab,
                    selectedTabCallback = {
                        vm.updateSelectedTab(it)

                    }
                ) {
                    Log.d(TAG, "MainScreen: cam click")
                }
            }
        }
    ) {
        Log.d(TAG, "MainScreen: qeweqweweq")
        Box(
            Modifier.padding(
            bottom = it.calculateBottomPadding())
        ) {
            Column {
                when (state.selectedTab) {
//                    NavGroup.Main.CAM -> CamScreen(
//                        navController = navController,
//                        bottomNavVisible = {
//                            bottomVisible = it
//                        },
//                    )

                    else -> {
                        bottomVisible = true
                    }
                }
            }
        }
    }
}