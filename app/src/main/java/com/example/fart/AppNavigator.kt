package com.example.fart

import com.example.fart.data.AppViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController

enum class Screen(val route: String) {
	Main("MainScreen"),
	Select("SelectScreen"),
	Photolist("PhotoScreen"),
	Singlephoto("SinglePhotoScreen"),
	Checkout("CheckoutScreen")
}

@Composable
fun AppNavigator(viewModel: AppViewModel) {
	val navController: NavHostController = rememberNavController()

	NavHost(navController = navController, startDestination = Screen.Main.route) {

		// Main Screen
		composable(Screen.Main.route) {
			MainScreen(navController = navController, navigateToSelectScreen = {
				navController.navigate(Screen.Select.route)
			}, appViewModel = viewModel)
		}

		// Select Screen
		composable(Screen.Select.route) {
			SelectScreen(navigateToPhotoScreen = {
				navController.navigate(Screen.Photolist.route)
			}, viewModel = viewModel, navController = navController)
		}

		// Photo Screen
		composable(Screen.Photolist.route) {
			PhotoScreen(navigateToSinglePhotoScreen = {
				navController.navigate(Screen.Singlephoto.route)
			}, viewModel = viewModel, navController = navController)
		}

		// Single Photo Screen
		composable(Screen.Singlephoto.route) {
			SinglePhotoScreen(viewModel = viewModel, navController = navController)
		}
		// Checkout Screen
		composable(Screen.Checkout.route) {
			CheckoutScreen(viewModel = viewModel, navController = navController)
	}

	}
}
