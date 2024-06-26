package com.example.fart.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
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

	private fun loadInitialData() {
		viewModelScope.launch {
			val artists = database.findAllArtists()
			val categories = database.findAllCategories()
			_uiState.update { currentState ->
				currentState.copy(artists = artists,
					categories = categories,
					artistItems = artists.map { ListItem.ArtistItem(it) },
					categoryItems = categories.map {
						ListItem.CategoryItem(
							it, database.findPhotosByCategory(it)
						)
					})
			}
		}
	}

	fun updateSelection(mode: SelectionMode, selectedItem: String? = null) {
		viewModelScope.launch {
			when (mode) {
				SelectionMode.ARTIST -> {
					selectedItem?.let {
						val artist = database.findAllArtists().find { it.name == selectedItem }
						_uiState.update { currentState ->
							currentState.copy(
								selectedArtist = artist?.name ?: "",
								selectedItems = artist?.photos ?: emptyList(),
								selectionMode = SelectionMode.ARTIST
							)
						}
					}
						?: _uiState.update { currentState -> currentState.copy(selectionMode = SelectionMode.ARTIST) }
				}

				SelectionMode.CATEGORY -> {
					selectedItem?.let {
						val category = database.findAllCategories().find { it.name == selectedItem }
						_uiState.update { currentState ->
							currentState.copy(
								selectedCategory = category?.name ?: "",
								selectedItems = database.findPhotosByCategory(
									category ?: Category.Other
								),
								selectionMode = SelectionMode.CATEGORY
							)
						}
					}
						?: _uiState.update { currentState -> currentState.copy(selectionMode = SelectionMode.CATEGORY) }
				}

				else -> _uiState.update { currentState -> currentState.copy(selectionMode = SelectionMode.NONE) }
			}
		}
	}

	fun setSelectedItem(photo: Photo) {
		viewModelScope.launch {
			_uiState.update { currentState -> currentState.copy(selectedItem = photo) }
		}
	}

	fun setSelectedSize(size: Size) {
		_uiState.update { currentState -> currentState.copy(selectedSize = size) }
	}

	fun setSelectedFrame(frame: Frame) {
		_uiState.update { currentState -> currentState.copy(selectedFrame = frame) }
	}

	fun setSelectedFrameWidth(frameWidth: Framewidth) {
		_uiState.update { currentState -> currentState.copy(selectedFrameWidth = frameWidth) }
	}

	fun setSelectedPhoto(photo: Photo) {
		_uiState.update { currentState -> currentState.copy(selectedPhoto = photo) }
	}
	fun addToCart(photo: Photo) {
		val currentSize = uiState.value.selectedSize
		val currentFrame = uiState.value.selectedFrame
		val currentFrameWidth = uiState.value.selectedFrameWidth

		val cartItem = CartItem(photo, currentSize, currentFrame, currentFrameWidth)
		_uiState.value = uiState.value.copy(
			cart = (_uiState.value.cart + cartItem).toMutableStateList()
		)
	}
	fun removeFromCart(cartItem: CartItem) {
		_uiState.update { currentState ->
			currentState.copy(cart = currentState.cart.toMutableList().also { it.remove(cartItem) })
		}
	}
	fun clearCart() {
		_uiState.update { currentState ->
			currentState.copy(cart = mutableStateListOf())
		}
	}


}