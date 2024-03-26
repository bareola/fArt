package com.example.fart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fart.data.AppViewModel
import com.example.fart.data.Photo

@Composable
fun PhotoScreen(viewModel: AppViewModel, navigateToSinglePhotoScreen: (Int) -> Unit) {
	val uiState = viewModel.uiState.collectAsState().value
	Scaffold(topBar = { BasicAppBar(title = "Photos") }, content = { paddingValues ->
		PhotoGrid(photos = uiState.selectedItems, paddingValues = paddingValues)
	})
}


@Composable
fun PhotoGrid(photos: List<Photo>, paddingValues: PaddingValues) {
	LazyVerticalGrid(
		columns = GridCells.Fixed(2),
		contentPadding = paddingValues,
		modifier = Modifier.fillMaxSize()
	) {
		items(photos.size) { index ->
			val photo =
				photos[index]			// Assuming you have a way to determine the artist's name from the photo,
			// otherwise, you can leave it as "Unknown" or modify according to your data structure.
			PhotoCard(photo) // Update this according to your data structure
		}
	}
}


@Composable
fun PhotoCard(photo: Photo) {
	val imageId = photo.resourceId

	Card(
		modifier = Modifier
			.fillMaxWidth()
			.aspectRatio(1.5f)
			.padding(4.dp)
			.background(
				brush = Brush.linearGradient(
					colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f))
				)
			), elevation = CardDefaults.cardElevation(4.dp)
	) {
		Box(
			contentAlignment = Alignment.BottomCenter
		) {
			Image(
				painter = painterResource(id = imageId),
				contentDescription = photo.title,
				contentScale = ContentScale.FillBounds,
				modifier = Modifier
					.fillMaxSize()
					.scale(1.02f)
			)

			Column {
				Text(text = photo.title)
				Text(text = "Placeholder")
				Text(text = photo.price.toString())
			}
		}
	}
}