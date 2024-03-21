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
import com.example.fart.ui.theme.FArtTheme


@Composable
fun MainScreen(appViewModel: AppViewModel = viewModel()) {
	val uiState by appViewModel.uiState.collectAsState()

	Column {
		BasicAppBar("Ola")
		ChooseBasedOn(appViewModel)
		ShoppingCart()
	}
}

@Composable
fun ChooseBasedOn(appViewModel: AppViewModel) {
	Column {
		Text(text = stringResource(id = R.string.choose_based_on))
		Row {
			Button(onClick = {
				appViewModel.navigateToArtistScreen()
			}) {
				Text(text = stringResource(id = R.string.artist))
			}
			Button(onClick = {
				appViewModel.navigateToCategoryScreen()
			}) {
				Text(text = stringResource(id = R.string.category))
			}
		}
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAppBar(title: String) {
	TopAppBar(title = { Text(text = title) }, modifier = Modifier.fillMaxWidth())
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