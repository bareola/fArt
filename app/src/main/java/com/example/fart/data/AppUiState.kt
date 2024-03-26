package com.example.fart.data
enum class SelectionMode {
	ARTIST, CATEGORY, NONE
}
data class AppUiState(
	val artists: List<Artist> = emptyList(),
	val artistItems: List<ListItem.ArtistItem> = emptyList(),
	val categories: List<Category> = emptyList(),
	val categoryItems: List<ListItem.CategoryItem> = emptyList(),
	val selectedItems: List<Photo> = emptyList(),
	val selectedArtist: String = "",
	val selectedCategory: String = "",
	val cart: List<Photo> = emptyList(),
	var selectionMode: SelectionMode = SelectionMode.NONE,
	var selectedItem: Photo = Photo(0, "", 0, emptyList(), 0.0)
)
