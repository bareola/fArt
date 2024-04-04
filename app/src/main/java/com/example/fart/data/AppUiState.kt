package com.example.fart.data

import androidx.compose.runtime.mutableStateListOf

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
	var cart: List<CartItem> = mutableStateListOf(),
	var selectionMode: SelectionMode = SelectionMode.NONE,
	var selectedItem: Photo = Photo(0, "", 0, emptyList(), 0.0),
	val selectedSize: Size = Size.MEDIUM,
	var selectedFrame: Frame = Frame.WOOD,
	var selectedFrameWidth: Framewidth = Framewidth.MEDIUM,
	var selectedPhoto: Photo? = null,
	var isSizeExpanded: Boolean = false,
	var isFrameExpanded: Boolean = false,
	var isFrameWidthExpanded: Boolean = false


)
