package com.example.fart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fart.data.AppViewModel
import com.example.fart.data.Photo
import com.example.fart.data.SelectionMode

@Composable
fun MainScreen(navigateToSelectScreen: (String) -> Unit, appViewModel: AppViewModel) {
	val uiState by appViewModel.uiState.collectAsState()
	val context = LocalContext.current

	Column {
		BasicAppBar(title = context.getString(R.string.app_name))
		ChooseBasedOn(navigateToSelectScreen, appViewModel)
		ShoppingCart(uiState.cart)
	}
}

@Composable
fun ChooseBasedOn(navigateToSelectScreen: (String) -> Unit, appViewModel: AppViewModel) {
	Column {
		Text(text = stringResource(id = R.string.choose_based_on))
		Row {
			Button(onClick = {
				appViewModel.setSelectionMode(SelectionMode.ARTIST);
				navigateToSelectScreen(SelectionMode.ARTIST.name)
			}) {
				Text(text = stringResource(id = R.string.artist))
			}
			Spacer(modifier = Modifier.width(8.dp))
			Button(onClick = {
				appViewModel.setSelectionMode(SelectionMode.CATEGORY)
				navigateToSelectScreen(SelectionMode.CATEGORY.name)
			}) {
				Text(text = stringResource(id = R.string.category))
			}
		}
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAppBar(title: String) {
	TopAppBar(
		title = { Text(text = title) }, modifier = Modifier.fillMaxWidth()
	)
}

@Composable
fun ShoppingCart(cart: List<Photo>) {
	val total = cart.sumOf { it.price }
	Card(modifier = Modifier.padding(16.dp)) {
		Column(modifier = Modifier.padding(16.dp)) {
			Text(
				text = stringResource(id = R.string.my_cart),
				style = MaterialTheme.typography.headlineMedium
			)
			Spacer(modifier = Modifier.height(8.dp))
			Text(text = "${stringResource(id = R.string.num_chosen_pics)}: ${cart.size}")
			Spacer(modifier = Modifier.height(4.dp))
			Text(text = "${stringResource(id = R.string.total_price)}: $total")
		}
	}
}
