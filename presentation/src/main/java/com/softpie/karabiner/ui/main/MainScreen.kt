package com.softpie.karabiner.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.softpie.karabiner.ui.cam.CamScreen
import com.softpie.karabiner.ui.root.MainViewModel
import com.softpie.karabiner.ui.root.NavGroup
import com.softpie.karabiner.utiles.TAG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    vm: MainViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    Log.d(TAG, "MainScreen: ${state.selectedTab.id}")
    Log.d(TAG, "MainScreen: qeweqweweq")
    Scaffold(
        bottomBar = {
            MainBottomNavigation(
                selectedTab = state.selectedTab,
                selectedTabCallback = {
                    vm.updateSelectedTab(it)
                }
            ) {
                Log.d(TAG, "MainScreen: cam click")
            }
        }
    ) {
        Log.d(TAG, "MainScreen: qeweqweweq")
        Box(Modifier.padding(
            bottom = it.calculateBottomPadding())
        ) {
            Column {
                when (state.selectedTab) {
                    NavGroup.Main.CAM -> CamScreen(navController = navController)

                    else -> {

                    }
                }
            }
        }
    }
}