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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fart.data.ListItem
import com.example.fart.data.Photo


@Composable
fun ItemCard(item: ListItem) {
	val name: String
	val picture: Int
	val photos: List<Photo>

	when (item) {
		is ListItem.ArtistItem -> {
			name = item.artist.name
			picture = item.artist.picture
			photos = item.artist.photos
		}

		is ListItem.CategoryItem -> {
			name = item.category.name
			picture = item.category.picture
			photos = item.photos
		}
	}

	Card(modifier = Modifier.fillMaxWidth().clickable { }) {
		Row {
			Image(
				painter = painterResource(id = picture),
				contentDescription = "$name photo",
				modifier = Modifier
					.size(64.dp)
					.align(Alignment.CenterVertically)
					.padding(4.dp)
			)
			Column {
				Text(text = name)
				Text(text = photos.size.toString())
				Text(text = photos.maxOf { it.price }.toString())
				Text(text = photos.random().title)
			}
		}
	}
}

@Composable
fun SelectList(items: List<ListItem>, paddingValues: PaddingValues) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxWidth()
	) {
		items.forEach { item ->
			when (item) {
				is ListItem.ArtistItem -> ItemCard(item = item)
				is ListItem.CategoryItem -> ItemCard(item = item)
			}
		}
	}
}

@Composable
fun SelectScreen(type: String, appViewModel: AppViewModel = viewModel()) {
	Log.d("Debug", "SelectScreen called with type: $type")
	val uiState by appViewModel.uiState.collectAsState()

	when (type) {
		"artist" -> appViewModel.loadArtistItems()
		"category" -> appViewModel.loadCategoryItems()
	}

	Scaffold(topBar = { BasicAppBar(title = uiState.title) }, // Use state for the title
		content = { paddingValues -> // Pass the relevant items list based on the type
			when (type) {
				"artist" -> SelectList(uiState.artistItems, paddingValues)
				"category" -> SelectList(uiState.categoryItems, paddingValues)
				else -> {}
			}
		})
}

