import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fart.Screen
import com.example.fart.data.AppUiState
import com.example.fart.data.Database
import com.example.fart.data.ItemCardData
import com.example.fart.data.ListItem
import com.example.fart.data.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(private val database: Database = Database()) : ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
	private val _currentScreen = MutableStateFlow<Screen>(Screen.MainScreen)
	val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

	fun loadArtistItems() {
		viewModelScope.launch {
			val artists = database.findAllArtists()
			_uiState.update { currentState ->
				currentState.copy(artists = artists,
					artistItems = artists.map { ListItem.ArtistItem(it) })
			}
		}
	}

	fun loadCategoryItems() {
		viewModelScope.launch {
			val categoryItems = database.findCategoriesWithPhotos()
			_uiState.update { currentState ->
				currentState.copy(categoryItems = categoryItems)
			}
		}
	}

	fun getCardData(item: ListItem): ItemCardData {
		val name: String
		val picture: Int
		val photos: List<Photo>

		when (item) {
			is ListItem.ArtistItem -> {
				name = item.artist.name
				picture = item.artist.picture
				photos = item.artist.photos
			}

			is ListItem.CategoryItem -> {
				name = item.category.name
				picture = item.category.picture
				photos = item.photos
			}
		}

		return ItemCardData(name, picture, photos)
	}

	fun navigateToArtistScreen() {
		_currentScreen.value = Screen.SelectScreen("artist")
	}

	fun navigateToCategoryScreen() {
		_currentScreen.value = Screen.SelectScreen("category")
	}
}
