package com.softpie.karabiner.ui.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.softpie.karabiner.ui.main.MainScreen
import com.softpie.karabiner.ui.signup.complete.SignupCompleteScreen
import com.softpie.karabiner.ui.signup.email.SignupEmailScreen
import com.softpie.karabiner.ui.signup.name.SignupNameScreen
import com.softpie.karabiner.ui.signup.splash.SplashScreen
import com.softpie.karabiner.ui.signup.tel.SignupTelScreen


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
        composable(NavGroup.Auth.NAME.id) {
            SignupNameScreen(navController = navController)
        }
        composable(NavGroup.Auth.TEL.id) {
            SignupTelScreen(navController = navController)
        }
        composable(NavGroup.Auth.EMAIL.id) {
            SignupEmailScreen(navController = navController)
        }
        composable(NavGroup.Auth.COMPLETE.id) {
            SignupCompleteScreen(navController = navController)
        }
        composable(NavGroup.Auth.LAUNCH.id) {
            SplashScreen(navController = navController)
        }
    }
}

private val Start = NavGroup.Main.MAIN.id

fun getStartDestination() =
    /*if (enableAutoLogin) NavGroup.Main.MAIN.id else */Start
