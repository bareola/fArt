package com.example.fart

import AppViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fart.ui.theme.FArtTheme


@Composable
fun MainScreen(appViewModel: AppViewModel = viewModel(), navController: NavController) {
	val uiState by appViewModel.uiState.collectAsState()

	Column {
		BasicAppBar("Ola")
		ChooseBasedOn(navController, appViewModel)
		ShoppingCart()
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAppBar(title: String) {
	TopAppBar(title = { Text(text = title) }, modifier = Modifier.fillMaxWidth())
}

@Composable
fun ChooseBasedOn(navController: NavController, appViewModel: AppViewModel) {
	Column {
		Text(text = stringResource(id = R.string.choose_based_on))
		Row {
			Button(onClick = {
				navController.navigate(Screen.SelectScreen.withArgs("artist"))
			}) {
				Text(text = stringResource(id = R.string.artist))
			}
			Button(onClick = {
				navController.navigate(Screen.SelectScreen.withArgs("category"))
			}) {
				Text(text = stringResource(id = R.string.category))
			}
		}
	}
}

@Composable
fun ShoppingCart() {
	Column {
		Text(text = stringResource(id = R.string.my_cart))
		Text(text = stringResource(id = R.string.num_chosen_pics))
		Text(text = stringResource(id = R.string.total_price))

	}
}

@Composable
fun ShoppingCartCard() {
	Card { }
}

@Preview
@Composable
fun FullPreview() {
	FArtTheme {		//MainScreen()
	}
}

@Preview
@Composable
fun PhotocardPreview() {
	FArtTheme { }
}