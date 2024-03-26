package com.example.fart.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(private val database: Database = Database()) : ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

	init {
		loadInitialData()
	}

	fun setSelectionMode(mode: SelectionMode) {
		viewModelScope.launch {
			_uiState.update { currentState ->
				currentState.copy(selectionMode = mode)

			}
			when (mode) {
				SelectionMode.ARTIST -> {
					loadArtistItems()

				}
				SelectionMode.CATEGORY -> {
					loadCategoryItems()

				}
				else -> {Log.d("GIAEVER", "None")}
			}
			Log.d("GIAEVER mode set", "Selection Mode: ${_uiState.value.selectionMode}")
		}
	}

	fun selectItem(selectedItem: String, isArtist: Boolean) {
		viewModelScope.launch {
			if (isArtist) {
				val artist = database.findAllArtists().find { it.name == selectedItem }
				_uiState.update { currentState ->
					currentState.copy(
						selectedArtist = artist.toString(),
						selectedItems = artist?.photos ?: emptyList(),
						selectionMode = SelectionMode.ARTIST
					)
				}
			} else {
				val category = database.findAllCategories().find { it.name == selectedItem }
				_uiState.update { currentState ->
					currentState.copy(
						selectedCategory = category.toString(),
						selectedItems = category?.let { database.findPhotosByCategory(it) } ?: emptyList(),
						selectionMode = SelectionMode.CATEGORY
					)
				}
			}
		}
	}


	private fun loadArtistItems() {
		viewModelScope.launch {
			val artists = database.findAllArtists()
			_uiState.update { currentState ->
				currentState.copy(artists = artists, artistItems = artists.map { ListItem.ArtistItem(it) })
			}
		}
	}

	private fun loadCategoryItems() {
		viewModelScope.launch {
			val categories = database.findAllCategories()
			val categoryItems = categories.map { category ->
				ListItem.CategoryItem(category, database.findPhotosByCategory(category))
			}
			_uiState.update { currentState ->
				currentState.copy(categories = categories, categoryItems = categoryItems)
			}
		}
	}

	private fun loadInitialData() {
		loadArtistItems()
		loadCategoryItems()
	}

	fun addToCart(photo: Photo) {
		viewModelScope.launch {
			_uiState.update { currentState ->
				currentState.copy(cart = currentState.cart + photo)
			}
		}
	}

	fun setSelectedItem(selectedItem: Photo) {
		viewModelScope.launch {
			_uiState.update { currentState ->
				currentState.copy(selectedItem = selectedItem)
			}
		}
	}
fun setSelectedArtist(selectedArtist: String) {
		viewModelScope.launch {
			_uiState.update { currentState ->
				currentState.copy(selectedArtist = selectedArtist)
			}
		}
	}
	fun setSelectedCategory(selectedCategory: String) {
		viewModelScope.launch {
			_uiState.update { currentState ->
				currentState.copy(selectedCategory = selectedCategory)
			}
		}
	}
}
