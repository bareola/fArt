import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fart.R
import com.example.fart.data.Database
import com.example.fart.data.Photo
import com.example.fart.data.Size

@Composable
fun RadioButtonGroup(
	options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit
) {
	Column {
		options.forEach { option ->
			Row(
				Modifier
					.fillMaxWidth()
					.padding(8.dp)
					.clickable { onOptionSelected(option) }) {
				RadioButton(
					selected = option == selectedOption,
					onClick = { onOptionSelected(option) })
				Text(
					text = option,
					style = MaterialTheme.typography.bodySmall.merge(),
					modifier = Modifier.padding(start = 8.dp)
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrameChoiceRadioButton(viewModel: AppViewModel, onSelected: () -> Unit) {
	val uiState = viewModel.uiState.collectAsState()
		RadioButtonGroup(options = Size.entries.map{it.type}, selectedOption = uiState.value.selectedSize.toString(), onOptionSelected = { viewModel.setSelectedSize(
			Size.valueOf(it)) })
	}


		@OptIn(ExperimentalMaterial3Api::class)
		@Composable
		fun SinglePhotoScreen(
			viewModel: AppViewModel
		) {
			val uiState = viewModel.uiState.collectAsState()
			val photo = uiState.value.selectedItem

			Scaffold(topBar = {
				TopAppBar(title = { Text(text = photo.title) }, navigationIcon = {
					IconButton(onClick = { }) {
						Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
					}
				})
			}) { innerPadding ->
				PhotoDetailContent(
					photo = photo, modifier = Modifier.padding(innerPadding), viewModel = viewModel
				)
			}
		}


		@Composable
		fun PhotoDetailContent(
			photo: Photo,
			modifier: Modifier = Modifier,
			viewModel: AppViewModel
		) {
			Column(
				modifier = modifier
					.fillMaxSize()
					.padding(16.dp)
			) {
				Image(
					painter = painterResource(id = photo.resourceId),
					contentDescription = photo.title,
					modifier = Modifier
						.fillMaxWidth()
						.aspectRatio(1.5f),
					contentScale = ContentScale.Crop
				)
				Spacer(modifier = Modifier.height(16.dp))
				Card {

					Text(
						text = stringResource(id = R.string.photo_details),
						style = MaterialTheme.typography.headlineSmall
					)
					Text(
						text = stringResource(
							id = R.string.photo_details_body, photo.artist(), photo.price
						), style = MaterialTheme.typography.bodySmall
					)
					Text(
						text = stringResource(
							id = R.string.photo_details_categories, photo.categoriesJoined()
						), style = MaterialTheme.typography.bodySmall
					)
					Divider(
						color = Color.Gray,
						thickness = 1.dp,
						modifier = Modifier.padding(vertical = 8.dp)
					)
					Text(
						text = stringResource(id = R.string.photo_details_frame),
						style = MaterialTheme.typography.headlineSmall
					)
					FrameChoiceRadioButton(viewModel = viewModel, onSelected = {})
				}

			}
		}

		@Preview
		@Composable
		fun SinglePhotoScreenPreview() {
			val viewModel = AppViewModel()
			val db = Database().loadPhotos().first()
			viewModel.setSelectedItem(db)
			SinglePhotoScreen(viewModel = viewModel)
		}