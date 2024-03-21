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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fart.data.Database
import com.example.fart.data.Photo
import com.example.fart.ui.theme.FArtTheme

val db = Database()


@Composable
fun PhotoScreen() {
 Scaffold(
  topBar = { BasicAppBar(title = "Photos") },
  content = { paddingValues -> Photogrid(paddingValues) }
 )
}

@Composable
fun Photogrid(paddingValues: PaddingValues) {
	val photos = db.loadPhotos()
	val artistMap = db.findAllArtists().associateBy { it.name }  // Map artist name to Artist object

	LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = paddingValues, modifier = Modifier.fillMaxSize()) {
		items(photos.size) { index ->
			val photo = photos[index]
			val artistName =
				artistMap[photo.title]?.name ?: "" // Get artist name from map, handle missing data
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
			.background(brush = Brush.linearGradient(
				colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f))
			)),
		elevation = CardDefaults.cardElevation(4.dp)
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



@Preview
@Composable
fun PhotoCardPreview() {
	FArtTheme {
		PhotoCard(db.loadPhotos().first(), db.findAllArtists().first().name)

	}
}
@Preview
@Composable
fun PhotoScreenPreview() {
	FArtTheme {
		PhotoScreen()

	}
}