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
	object VectorArt : Category("Vector Art", R.drawable.oreo)

	override fun toString(): String {
		return name
	}
}

sealed class ListItem {
	data class ArtistItem(val artist: Artist) : ListItem()
	data class CategoryItem(val category: Category, val photos: List<Photo>) : ListItem()

	fun name(): String {
		return when (this) {
			is ArtistItem -> artist.name
			is CategoryItem -> category.name
		}
	}
}

data class Artist(
	val name: String, val age: Int, @DrawableRes val picture: Int, val photos: List<Photo>
)

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
	SMALL("Small", 100.0), MEDIUM("Medium", 150.0), LARGE("Large", 200.0);

	companion object {
		fun fromType(type: String): Size? {
			return entries.firstOrNull { it.type.equals(type, ignoreCase = true) }
		}
	}
}


enum class Frame(val type: String, val price: Double) {
	PLASTIC("Plastic", 0.75), WOOD("Wood", 1.0), METAL("Metal", 1.25);

	companion object {
		fun fromType(type: String): Frame? {
			return entries.firstOrNull { it.type.equals(type, ignoreCase = true) }
		}
	}
}

enum class Framewidth(val size: String, val price: Double) {
	SMALL("1 cm", 0.5), MEDIUM("2 cm", 1.0), LARGE("3 cm", 1.5);

	companion object {
		fun fromSize(material: String): Framewidth? {
			return entries.firstOrNull { it.size.equals(material, ignoreCase = true) }
		}
	}
}

data class CartItem(
	val photo: Photo, val size: Size, val frame: Frame, val frameWidth: Framewidth
) {
	val price: Double
		get() = photo.price + (size.price * frame.price * frameWidth.price)
}

class Database {
	private val artists = listOf(
		Artist(
			"Ola Giæver", 39, R.drawable.ola_giaever, listOf(
				Photo(
					1,
					"Inga",
					R.drawable.inga,
					listOf(Category.Nature, Category.Architecture, Category.Travel),
					1700.0
				), Photo(
					2,
					"Haagensen Bruket",
					R.drawable.haagensen,
					listOf(Category.Nature, Category.Architecture),
					1200.0
				), Photo(
					3,
					"Havøysund fra Svartfjellet",
					R.drawable.havoeysund,
					listOf(Category.Nature),
					1000.0
				), Photo(
					4,
					"Italian Sunset",
					R.drawable.italy,
					listOf(Category.Nature, Category.Travel),
					1500.0
				), Photo(
					5,
					"Italian Wineyard",
					R.drawable.italy2,
					listOf(Category.Nature, Category.Travel),
					1500.0
				), Photo(
					6, "Jogger", R.drawable.jogger, listOf(Category.Nature), 2000.0
				), Photo(
					7, "purple", R.drawable.purple, listOf(Category.Other), 1750.0
				), Photo(
					8,
					"Rialto Bridge, Venice, Italy",
					R.drawable.rialto,
					listOf(Category.Travel, Category.Architecture),
					1200.0
				), Photo(
					9,
					"Statue in Venice",
					R.drawable.statue,
					listOf(Category.Art, Category.Travel, Category.Architecture),
					1000.0
				), Photo(
					10,
					"Gavelen Windmillpark",
					R.drawable.windmills,
					listOf(Category.Nature, Category.Travel),
					1200.0
				)
			)
		),
		Artist(
			"Sjur Berntsen", 42, R.drawable.syk_ola, listOf(
				Photo(11, "Stranda mi", R.drawable.sjur1, listOf(Category.Nature), 50.0),
				Photo(12, "Jeg ror", R.drawable.sjur2, listOf(Category.Nature), 65.0),
				Photo(13, "Fjorden min", R.drawable.sjur3, listOf(Category.Nature), 55.0),
				Photo(14, "Tatt fra sjarken", R.drawable.sjur4, listOf(Category.Nature), 55.0),
				Photo(15, "Broa mi", R.drawable.sjur5, listOf(Category.Nature), 50.0),
				Photo(16, "Regnbuen min", R.drawable.sjur6, listOf(Category.Nature), 55.0),
				Photo(17, "Kysten min", R.drawable.sjur7, listOf(Category.Nature), 65.0),
				Photo(18, "Stoffen min", R.drawable.sjur8, listOf(Category.Nature), 99.0),
				Photo(19, "Rosa min", R.drawable.sjur9, listOf(Category.Nature), 40.0),
				Photo(20, "Den andre stranda min", R.drawable.sjur10, listOf(Category.Nature), 45.0)
			)
		),
		Artist(
			"Stygg Ola", 39, R.drawable.stygg_ola, listOf(
				Photo(21, "Rex", R.drawable.stygg, listOf(Category.Animals), 100.0),
				Photo(22, "King", R.drawable.stygg2, listOf(Category.Animals), 100.0),
				Photo(23, "Diaz", R.drawable.stygg3, listOf(Category.Animals), 100.0),
				Photo(24, "Hunter", R.drawable.stygg4, listOf(Category.Animals), 100.0),
				Photo(25, "Buddy", R.drawable.stygg5, listOf(Category.Animals), 100.0),
				Photo(26, "Killer", R.drawable.stygg6, listOf(Category.Animals), 100.0),
				Photo(27, "Vato", R.drawable.stygg7, listOf(Category.Animals), 100.0),
				Photo(28, "Per Ante", R.drawable.stygg8, listOf(Category.Animals), 100.0),
				Photo(29, "Per Per Per", R.drawable.stygg9, listOf(Category.Animals), 100.0),
			)
		),
		Artist(
			"Tjukk Ola", 39, R.drawable.tjukk_ola, listOf(
				Photo(
					30,
					"Cupcake",
					R.drawable.cupcake,
					listOf(Category.Food, Category.VectorArt),
					15.0
				),
				Photo(
					31,
					"Donut",
					R.drawable.donut,
					listOf(Category.Food, Category.VectorArt),
					20.0
				),
				Photo(
					32,
					"Eclair",
					R.drawable.eclair,
					listOf(Category.Food, Category.VectorArt),
					25.0
				),
				Photo(
					33,
					"Froyo",
					R.drawable.froyo,
					listOf(Category.Food, Category.VectorArt),
					30.0
				),
				Photo(
					34,
					"Gingerbread",
					R.drawable.gingerbread,
					listOf(Category.Food, Category.VectorArt),
					35.0
				),
				Photo(
					35,
					"Honeycomb",
					R.drawable.honeycomb,
					listOf(Category.Food, Category.VectorArt),
					40.0
				),
				Photo(
					36,
					"Icecream Sandwich",
					R.drawable.icecreamsandwich,
					listOf(Category.Food, Category.VectorArt),
					45.0
				),
				Photo(
					37,
					"Jellybean",
					R.drawable.jellybean,
					listOf(Category.Food, Category.VectorArt),
					50.0
				),
				Photo(
					38,
					"Kitkat",
					R.drawable.kitkat,
					listOf(Category.Food, Category.VectorArt),
					55.0
				),
				Photo(
					39,
					"Lollipop",
					R.drawable.lollipop,
					listOf(Category.Food, Category.VectorArt),
					60.0
				),
				Photo(
					40,
					"Marshmallow",
					R.drawable.marshmallow,
					listOf(Category.Food, Category.VectorArt),
					65.0
				),
				Photo(
					41,
					"Nougat",
					R.drawable.nougat,
					listOf(Category.Food, Category.VectorArt),
					70.0
				),
				Photo(42, "Oreo", R.drawable.oreo, listOf(Category.Food, Category.VectorArt), 75.0)
			)
		),
	)

	private fun loadPhotos(): List<Photo> = artists.flatMap { it.photos }

	fun findAllArtists(): List<Artist> = artists

	fun findAllCategories(): List<Category> {
		return loadPhotos().flatMap { it.categories }.distinct()
	}

	fun findPhotosByCategory(category: Category): List<Photo> {
		val allPhotos = loadPhotos()
		return allPhotos.filter { it.categories.contains(category) }
	}
}