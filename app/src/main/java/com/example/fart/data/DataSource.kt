package com.example.fart.data

import androidx.annotation.DrawableRes
import com.example.fart.R

sealed class Category(val name: String, @DrawableRes val picture: Int) {
	object Nature : Category("Nature", R.drawable.nature)
	object People : Category("People", R.drawable.people)
	object Travel : Category("Travel", R.drawable.travel)
	object Architecture : Category("Architecture", R.drawable.architecture)
	object Animals : Category("Animals", R.drawable.animals)
	object Food : Category("Food", R.drawable.food)
	object Art : Category("Art", R.drawable.art)
	object Other : Category("Other", R.drawable.other)

	override fun toString(): String {
		return name
	}
}

sealed class ListItem {
	data class ArtistItem(val artist: Artist) : ListItem()
	data class CategoryItem(val category: Category, val photos: List<Photo>) : ListItem() {

	}

	fun name(): String {
		return when (this) {
			is ArtistItem -> artist.name
			is CategoryItem -> category.name
		}
	}
}

data class Artist(
	val name: String, val age: Int, @DrawableRes val picture: Int, val photos: List<Photo>
) {
	fun numberOfPhotos(): Int = photos.size
}

data class Photo(
	val id: Int,
	val title: String,
	val resourceId: Int,
	val categories: List<Category>,
	val price: Double
) {
	fun artist(): String {
		return Database().findAllArtists().find { it.photos.contains(this) }?.name ?: ""
	}

	fun categoriesJoined(): String {
		return categories.joinToString { it.name }
	}

}


enum class Size(val type: String, val price: Double) {
	SMALL("Small",50.0), MEDIUM("Medium",150.0), LARGE("Large",300.0)
}

enum class Frame(val type: String, val price: Double) {
	WOOD("Wood", 1.0), METAL("Metal", 1.5), PLASTIC("Plastic", 0.75)
}

enum class Framewidth(val material: String, val price: Double) {
	SMALL("1 cm", 15.0), MEDIUM("2 cm", 10.0), LARGE("3 cm", 0.0)
}

data class CartItem(
	val photo: Photo,
	val size: Size,
	val frame: Frame,
	val frameWidth: Framewidth
)
data class ItemCardData(
	val name: String, val picture: Int, val photos: List<Photo>
)

class Database {
	private val artists = listOf(
		Artist(
			"Ola Giæver", 39, R.drawable.ola_giaever, listOf(
				Photo(
					1,
					"Inga",
					R.drawable.inga,
					listOf(Category.Nature, Category.Architecture, Category.Travel),
					100.0
				), Photo(
					2,
					"Haagensen Bruket",
					R.drawable.haagensen,
					listOf(Category.Nature, Category.Architecture),
					100.0
				), Photo(
					3,
					"Havøysund fra Svartfjellet",
					R.drawable.havoeysund,
					listOf(Category.Nature),
					100.0
				), Photo(
					4,
					"Italian Sunset",
					R.drawable.italy,
					listOf(Category.Nature, Category.Travel),
					100.0
				), Photo(
					5,
					"Italian Wineyard",
					R.drawable.italy2,
					listOf(Category.Nature, Category.Travel),
					100.0
				), Photo(
					6, "Jogger", R.drawable.jogger, listOf(Category.Nature), 100.0
				), Photo(
					7, "purple", R.drawable.purple, listOf(Category.Other), 100.0
				), Photo(
					8,
					"Rialto Bridge, Venice, Italy",
					R.drawable.rialto,
					listOf(Category.Travel, Category.Architecture),
					100.0
				), Photo(
					9,
					"Statue in Venice",
					R.drawable.statue,
					listOf(Category.Art, Category.Travel, Category.Architecture),
					100.0
				), Photo(
					10,
					"Gavelen Windmillpark",
					R.drawable.windmills,
					listOf(Category.Nature, Category.Travel),
					100.0
				)
			)
		)
	)

	fun loadPhotos(): List<Photo> = artists.flatMap { it.photos }

	fun findAllArtists(): List<Artist> = artists

	fun findAllCategories(): List<Category> {
		return loadPhotos().flatMap { it.categories }.distinct()
	}

	fun findPhotosByCategory(category: Category): List<Photo> {
		val allPhotos = loadPhotos()
		return allPhotos.filter { it.categories.contains(category) }
	}

	fun findPhotosByArtist(artistName: String): List<Photo> {
		return findAllArtists().find { it.name == artistName }?.photos ?: emptyList()
	}

	fun findCategoriesWithPhotos(): List<ListItem.CategoryItem> {
		val allPhotos = loadPhotos()
		val categoriesWithPhotos = allPhotos.flatMap { it.categories }.distinct()
		val result = categoriesWithPhotos.map { category ->
			val photosInCategory = allPhotos.filter { it.categories.contains(category) }
			ListItem.CategoryItem(category, photosInCategory)
		}
		return result
	}

	fun findPhotoById(photoId: Int): Photo {
		return loadPhotos().find { it.id == photoId } ?: Photo(0, "", 0, emptyList(), 0.0)
	}


}