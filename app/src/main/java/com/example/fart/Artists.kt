package com.example.fart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.fart.data.Artist
import com.example.fart.data.Database

val database = Database()
@Composable
fun ArtistsScreen() {
	BasicAppBar(title ="Artists")


}
@Composable
fun ListColumn(artists: List<Artist> = database.findAllArtists()) {
	Column { 	artists.forEach() { artist -> ArtistCard(artist.name, artist.numberOfPhotos(), ) }

	}
}
