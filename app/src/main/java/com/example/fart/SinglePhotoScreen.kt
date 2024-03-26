import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fart.data.Photo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePhotoScreen(
	viewModel: AppViewModel
) {
	val uiState = viewModel.uiState.collectAsState()
	val photo = uiState.value.selectedItem

	Scaffold(topBar = {
		TopAppBar(title = { Text(text = photo.title) }, navigationIcon = {
			IconButton(onClick = { /* Handle back action */ }) {
				Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
			}
		})
	}) { innerPadding ->
		PhotoDetailContent(photo = photo, modifier = Modifier.padding(innerPadding))
		}
	}


@Composable
fun PhotoDetailContent(photo: Photo, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier.fillMaxSize().padding(16.dp)
	) {
		Image(
			painter = painterResource(id = photo.resourceId),
			contentDescription = photo.title,
			modifier = Modifier.fillMaxWidth().aspectRatio(1.5f),
			contentScale = ContentScale.Crop
		)
		Spacer(modifier = Modifier.height(16.dp))
		Text(text = "Title: ${photo.title}", style = MaterialTheme.typography.headlineMedium)
		Text(text = "Price: $${photo.price}", style = MaterialTheme.typography.bodySmall)
		Text(
			text = "Categories: ${photo.categories.joinToString { it.name }}",
			style = MaterialTheme.typography.bodyMedium
		)
	}
}
