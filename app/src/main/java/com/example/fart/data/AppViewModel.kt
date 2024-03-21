import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.fart.data.AppUiState
import com.example.fart.data.Database
import com.example.fart.data.ListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel(private val database: Database = Database()) : ViewModel() {
	private val _uiState = MutableStateFlow(AppUiState())
	val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

	fun loadArtistItems() {
		viewModelScope.launch {
			val artists = database.findAllArtists()
			_uiState.update { currentState ->
				currentState.copy(artists = artists, artistItems = artists.map { ListItem.ArtistItem(it) })
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

}
