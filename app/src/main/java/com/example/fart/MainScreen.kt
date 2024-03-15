package com.example.fart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.fart.ui.theme.FArtTheme


@Composable
fun MainScreen() {
	Column {
		BasicAppBar("Ola")
		ChooseBasedOn()
		ShoppingCart()
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAppBar(title: String) {
	TopAppBar(title = { Text(text = title) }, modifier = Modifier.fillMaxWidth())
}

@Composable
fun ChooseBasedOn() {
	Column {
		Text(text = stringResource(id = R.string.choose_based_on))
		Row {
			Button(onClick = { /*TODO*/ }) {
				Text(text = stringResource(id = R.string.artist))
			}
			Button(onClick = { /*TODO*/ }) {
				Text(text = stringResource(id = R.string.category))
			}
		}
	}
}
@Composable
fun ShoppingCart () {
	Column {
		Text(text = stringResource(id = R.string.my_cart))
		Text(text = stringResource(id = R.string.num_chosen_pics))
		Text(text = stringResource(id = R.string.total_price))

	}
}

@Composable
fun ArtistCard(title: String, numPhotos: Int, mostExpensive: String, featured: String) {
	Card {
		Row {			//placeholder for a photo of a painting
			Column {
				Text(text = title)
				Text(text = numPhotos.toString())
				Text(text = mostExpensive)
				Text(text = featured)
			}
		}
	}
}
@Composable
fun ShoppingCartCard() {
	Card {
	}
}
