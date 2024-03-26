package com.example.fart

import AppViewModel
import SinglePhotoScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

enum class Screen(val route: String) {
	Main("MainScreen"), Select("SelectScreen"), Photolist("PhotoScreen"), Singlephoto("SinglePhotoScreen");

	fun createRoute(item: String): String {
		return route.replace("{item}", item)
	}
}

@Composable
fun AppNavigator(
	viewModel: AppViewModel, navController: NavHostController = rememberNavController()
) {

	NavHost(
		navController = navController, startDestination = Screen.Main.name
	) {

		//Main Screen
		composable(Screen.Main.name) {
			MainScreen(
				navigateToSelectScreen = { navController.navigate(Screen.Select.name) }, viewModel
			)
		}

		//Select Screen
		composable(Screen.Select.name) {
			SelectScreen(
				navigateToPhotoScreen = { navController.navigate(Screen.Photolist.name) }, viewModel
			)
		}

		//Photo Screen
		composable(Screen.Photolist.name) {
			PhotoScreen(
				navigateToSinglePhotoScreen = { navController.navigate(Screen.Singlephoto.name) },
				viewModel = viewModel
			)
		}

		//Single Photo Screen
		composable(
			Screen.Singlephoto.name) {
			SinglePhotoScreen(
				viewModel = viewModel
			)
		}
	}
}