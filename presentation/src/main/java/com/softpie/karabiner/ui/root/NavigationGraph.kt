package com.softpie.karabiner.ui.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softpie.karabiner.ui.main.MainScreen


@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = getStartDestination()
    ) {
        composable(NavGroup.Main.MAIN.id) {
            MainScreen(navController = navController)
        }
    }
}

private val Start = NavGroup.Main.MAIN.id

fun getStartDestination() =
    /*if (enableAutoLogin) NavGroup.Main.MAIN.id else */Start
