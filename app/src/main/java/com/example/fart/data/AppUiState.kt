package com.example.fart.data

data class AppUiState(
	val title: String = "",
	var artists: List<Artist> = emptyList(),
	val categories: List<String> = emptyList(),
	val categoryItems: List<ListItem> = emptyList(),
	val artistItems: List<ListItem> = emptyList(),
	val selectedArtist: Artist? = null,
	val selectedCategory: String? = null,
	val cart: List<Photo> = emptyList()
) {


}
