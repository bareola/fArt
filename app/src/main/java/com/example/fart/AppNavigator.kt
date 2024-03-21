package com.example.fart

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object MainScreen : Screen("mainScreen")
    object SelectScreen : Screen("selectScreen/{type}") {
        fun withArgs(type: String): String {
            return route.replace("{type}", type)
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.SelectScreen.route) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            if (type != null) {
                SelectScreen(type)
            }
        }
    }
}
