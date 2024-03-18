package com.example.fart.data

import com.example.fart.R

data class Artist(
	val name: String, val age: Int, val photos: List<Photo>
) {
	fun numberOfPhotos(): Int = photos.size
}

data class Photo(
	val id: Int,
	val title: String,
	val resourceId: Int,
	val categories: List<String>,
	val price: Double
)

class Database {
	private val artists = listOf(Artist("Ola Giæver", 39, listOf(
		Photo(
			1, "Inga", R.drawable.inga, listOf("Nature", "People"), 100.0
		), Photo(
			2, "Haagensen Bruket", R.drawable.haagensen, listOf("Nature", "People"), 100.0
		), Photo(
			3,
			"Havøysund fra Svartfjellet",
			R.drawable.havoeysund,
			listOf("Nature", "People"),
			100.0
		), Photo(
			4, "Italian Sunset", R.drawable.italy, listOf("Nature", "People"), 100.0
		), Photo(
			5, "Italian Wineyard", R.drawable.italy2, listOf("Nature", "People"), 100.0
		), Photo(
			6, "Jogger", R.drawable.jogger, listOf("Nature", "People"), 100.0
		), Photo(
			7, "purple", R.drawable.purple, listOf("Nature", "People"), 100.0
		), Photo(
			8,
			"Rialto Bridge, Venice, Italy",
			R.drawable.rialto,
			listOf("Nature", "People"),
			100.0
		), Photo(
			9, "Statue in Venice", R.drawable.statue, listOf("Nature", "People"), 100.0
		), Photo(
			10,
			"Gavelen Windmillpark",
			R.drawable.havoeysund,
			listOf("Nature", "People"),
			100.0
		)
	)))

		fun loadPhotos(): List<Photo> = artists.flatMap { it.photos }

		fun findAllArtists(): List<Artist> = artists

		fun findNumberOfPhotos(artist: Artist): Int = artist.numberOfPhotos()

		fun findPhotosByCategory(): Map<String, List<Photo>> {
			val categoryPhotoMap = mutableMapOf<String, MutableList<Photo>>()

			loadPhotos().forEach { photo ->
				photo.categories.forEach { category ->
					if (!categoryPhotoMap.containsKey(category)) {
						categoryPhotoMap[category] = mutableListOf()
					}
					categoryPhotoMap[category]?.add(photo)
				}
			}

			return categoryPhotoMap
		}
}