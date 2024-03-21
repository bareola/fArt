package com.example.fart

import AppViewModel
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fart.data.ListItem


@Composable
fun ItemCard(item: ListItem, viewModel: AppViewModel, onItemSelect: (String) -> Unit) {
	val cardData = viewModel.getCardData(item)

	Card(modifier = Modifier
		.fillMaxWidth()
		.clickable {onItemSelect(cardData.name) }) {
		Row {
			Image(
				painter = painterResource(id = cardData.picture),
				contentDescription = "${cardData.name} photo",
				modifier = Modifier
					.size(64.dp)
					.align(Alignment.CenterVertically)
					.padding(4.dp)
			)
			Column {
				Text(text = cardData.name)
				Text(text = cardData.photos.size.toString())
				Text(text = cardData.photos.maxOf { it.price }.toString())
				Text(text = cardData.photos.random().title)
			}
		}
	}
}
@Composable
fun SelectList(items: List<ListItem>, viewModel: AppViewModel, paddingValues: PaddingValues) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxWidth()
	) {
		items.forEach { item ->
			ItemCard(item = item, viewModel = viewModel) { selectedItem ->
				viewModel.navigateToPhotoScreen(selectedItem)
			}
		}
	}
}


@Composable
fun SelectScreen(type: String, appViewModel: AppViewModel = viewModel()) {
	Log.d("Debug", "SelectScreen called with type: $type")
	val uiState by appViewModel.uiState.collectAsState()

	LaunchedEffect(type) {
		when (type) {
			"artist" -> appViewModel.loadArtistItems()
			"category" -> appViewModel.loadCategoryItems()
		}
	}

	Scaffold(
		topBar = { BasicAppBar(title = uiState.title) },
		content = { paddingValues ->
			when (type) {
				"artist" -> SelectList(uiState.artistItems, appViewModel, paddingValues)
				"category" -> SelectList(uiState.categoryItems, appViewModel, paddingValues)
				else -> {}
			}
		}
	)
}


