package com.example.fart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fart.data.AppViewModel
import com.example.fart.data.ListItem
import com.example.fart.data.Photo
import com.example.fart.data.SelectionMode

@Composable
fun ItemCard(item: ListItem, onItemSelect: (String) -> Unit) {
	Card(modifier = Modifier
		.fillMaxWidth()
		.clickable {
			when (item) {
				is ListItem.ArtistItem -> onItemSelect(item.artist.name)
				is ListItem.CategoryItem -> onItemSelect(item.category.name)
			}
		}) {
		Row {
			Image(
				painter = painterResource(
					id = when (item) {
						is ListItem.ArtistItem -> item.artist.picture
						is ListItem.CategoryItem -> item.category.picture
					}
				),
				contentDescription = "${item.name()} photo",
				modifier = Modifier
					.size(64.dp)
					.align(Alignment.CenterVertically)
					.padding(4.dp)
			)
			Column {
				Text(text = item.name())
				val photos = when (item) {
					is ListItem.ArtistItem -> item.artist.photos
					is ListItem.CategoryItem -> item.photos
				}
				Text(text = "Photos: ${photos.size}")
				Text(text = "Max Price: $${photos.maxOfOrNull { it.price } ?: "N/A"}")
				Text(text = "Sample: ${photos.randomOrNull()?.title ?: "N/A"}")
			}
		}
	}
}


@Composable
fun SelectList(
	items: List<ListItem>, onItemSelect: (String) -> Unit, paddingValues: PaddingValues
) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxWidth()
	) {
		items.forEach { item ->
			ItemCard(item = item, onItemSelect = onItemSelect)
		}
	}
}


@Composable
fun SelectScreen(navigateToPhotoScreen: (String) -> Unit, viewModel: AppViewModel) {
	val uiState by viewModel.uiState.collectAsState()
	Scaffold(topBar = {
		BasicAppBar(
			title = when (uiState.selectionMode) {
				SelectionMode.ARTIST -> "Select Artist"
				SelectionMode.CATEGORY -> "Select Category"
				else -> "Neither artist or category selected"
			}
		)
	}, content = { paddingValues ->
		when (uiState.selectionMode) {
			SelectionMode.ARTIST -> SelectList(

				items = uiState.artistItems,
				onItemSelect = { selectedItem ->
					viewModel.setSelectedArtist(selectedItem)
					navigateToPhotoScreen(selectedItem)
				},
				paddingValues = paddingValues
			)

			SelectionMode.CATEGORY -> SelectList(
				items = uiState.categoryItems,
				onItemSelect = { selectedItem ->
					viewModel.setSelectedArtist(selectedItem)
					navigateToPhotoScreen(selectedItem)
				},
				paddingValues = paddingValues
			)

			else -> Text("Please select a mode from the main menu")
		}
	})
}
