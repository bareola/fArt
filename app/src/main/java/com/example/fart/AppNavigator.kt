package com.example.fart

import AppViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
	object MainScreen : Screen("mainScreen")
	class SelectScreen(val type: String) : Screen("selectScreen/$type") {
		companion object {
			fun createRoute(type: String) = "selectScreen/$type"
		}
	}
	class PhotoScreen(val selectedItem: String) : Screen("photoScreen/$selectedItem") {
		companion object {
			fun createRoute(selectedItem: String) = "photoScreen/$selectedItem"
		}
	}
}


@Composable
fun AppNavigator(appViewModel: AppViewModel) {
	val navController = rememberNavController()
	val currentScreen by appViewModel.currentScreen.collectAsState()

	LaunchedEffect(currentScreen) {
		navController.navigate(currentScreen.route) {
			popUpTo(navController.graph.startDestinationId) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}

	NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
		composable(Screen.MainScreen.route) {
			MainScreen(appViewModel = appViewModel)
		}
		composable(
			route = Screen.SelectScreen("{type}").route,
			arguments = listOf(navArgument("type") {
				type = androidx.navigation.NavType.StringType
			})
		) { backStackEntry ->
			val type = backStackEntry.arguments?.getString("type")
			if (type != null) {
				SelectScreen(type, appViewModel = appViewModel)
			}
		}
		composable(
			route = Screen.PhotoScreen("{selectedItem}").route,
			arguments = listOf(navArgument("selectedItem") {
				type = androidx.navigation.NavType.StringType
			})
		) { backStackEntry ->
			val selectedItem = backStackEntry.arguments?.getString("selectedItem")
			if (selectedItem != null) {
				PhotoScreen(selectedItem)
			}
		}
	}
}