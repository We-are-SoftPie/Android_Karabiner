package com.softpie.karabiner.ui.root

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.softpie.karabiner.ui.cam.CamScreen
import com.softpie.karabiner.ui.ee.EeScreen
import com.softpie.karabiner.ui.log.LogScreen
import com.softpie.karabiner.ui.log.info.LogInfoScreen
import com.softpie.karabiner.ui.main.MainScreen
import com.softpie.karabiner.ui.signup.complete.SignupCompleteScreen
import com.softpie.karabiner.ui.signup.email.SignupEmailScreen
import com.softpie.karabiner.ui.signup.name.SignupNameScreen
import com.softpie.karabiner.ui.signup.splash.SplashScreen
import com.softpie.karabiner.ui.signup.tel.SignupTelScreen
import kotlinx.coroutines.flow.Flow


@Composable
fun NavigationGraph(
    navController: NavHostController,
    capture: Boolean,
    changePage: () -> Unit,
    onChangeNav: (NavGroup.Main) -> Unit,
    bottomVisible: (Boolean) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = getStartDestination()
    ) {
        composable(NavGroup.Main.TEST.id) {
            EeScreen(navController = navController)
    }
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
            bottomVisible(false)
            SplashScreen(navController = navController)
        }
        composable(NavGroup.Main.CAM.id) {
            onChangeNav(NavGroup.Main.CAM)
            CamScreen(navController = navController, bottomNavVisible = bottomVisible, capture = capture)
        }
        navigation(
            startDestination = NavGroup.Main.LIST.id,
            route = NavGroup.Main.LIST.group
        ){
            composable(NavGroup.Main.LIST.id) {
                bottomVisible(true)
                changePage()
                onChangeNav(NavGroup.Main.LIST)
                Log.d("TAG", "NavigationGraph: 돌아옴")
                LogScreen(navController = navController)
            }
            composable(
                NavGroup.Main.LIST_IFNO.id,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) {entry ->
                val ids = entry.arguments?.getString("id")
                ids?.let {
                    bottomVisible(true)
                    changePage()
                    onChangeNav(NavGroup.Main.LIST)
                    LogInfoScreen(
                        navController = navController,
                        id = it.toInt()
                    )
                }
            }

        }
    }
}

private val Start = NavGroup.Auth.LAUNCH.id//Auth.LAUNCH.id

fun getStartDestination() =
    /*if (enableAutoLogin) NavGroup.Main.MAIN.id else */Start
