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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fart.data.Category
import com.example.fart.data.Database
import com.example.fart.data.Photo

val db = Database()


@Composable
fun PhotoScreen(selectedItem: String) {
	Scaffold(topBar = { BasicAppBar(title = selectedItem) },
		content = { paddingValues -> Photogrid(selectedItem, paddingValues) })
}

@Composable
fun Photogrid(selectedItem: String, paddingValues: PaddingValues) {
	val photos = when (val category = db.findAllCategories().find { it.name == selectedItem }) {
		null -> db.findPhotosByArtist(selectedItem)
		else -> db.findPhotosByCategory(category)
	}

	LazyVerticalGrid(
		columns = GridCells.Fixed(2),
		contentPadding = paddingValues,
		modifier = Modifier.fillMaxSize()
	) {
		items(photos.size) { index ->
			val photo = photos[index]
			val artistName = db.findAllArtists().find { artist -> artist.photos.contains(photo) }?.name ?: "Unknown"
			PhotoCard(photo, artistName)
		}
	}
}



@Composable
fun PhotoCard(photo: Photo, artistName: String) {
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
				Text(text = artistName)
				Text(text = photo.price.toString())
			}
		}
	}
}