package com.example.fart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fart.data.AppUiState
import com.example.fart.data.AppViewModel
import com.example.fart.data.Database
import com.example.fart.data.Photo
import com.example.fart.data.SelectionMode
import com.example.fart.ui.theme.AppTheme

@Composable
fun PhotoScreen(
	viewModel: AppViewModel,
	navController: NavController,
	navigateToSinglePhotoScreen: (Int) -> Unit = {}
) {
	val uiState = viewModel.uiState.collectAsState().value
	Scaffold(topBar = {
		CustomAppBar(
			title = if (uiState.selectionMode == SelectionMode.ARTIST) {
				uiState.selectedArtist
			} else {
				uiState.selectedCategory
			}, navController = navController
		)
	}, content = { paddingValues ->
		PhotoGrid(
			uiState = uiState,
			paddingValues = paddingValues,
			navigateToSinglePhotoScreen = navigateToSinglePhotoScreen,
			viewModel = viewModel
		)
	})
}


@Composable
fun PhotoGrid(
	uiState: AppUiState,
	paddingValues: PaddingValues,
	navigateToSinglePhotoScreen: (Int) -> Unit = {},
	viewModel: AppViewModel
) {
	val photos = uiState.selectedItems
	LazyVerticalGrid(
		columns = GridCells.Fixed(2),
		contentPadding = paddingValues,
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier
			.padding(8.dp)
			.fillMaxSize()
	) {
		items(photos.size) { index ->
			PhotoCard(
				photo = photos[index],
				navigateToSinglePhotoScreen = navigateToSinglePhotoScreen,
				viewModel = viewModel,
				tag = "photoCard${index + 1}"
			)
		}
	}
}


@Composable
fun PhotoCard(
	photo: Photo,
	navigateToSinglePhotoScreen: (Int) -> Unit = {},
	viewModel: AppViewModel,
	tag: String
) {
	Card(modifier = Modifier
		.clickable {
			viewModel.setSelectedItem(photo)
			navigateToSinglePhotoScreen(photo.id)
		}
		.fillMaxWidth()
		.aspectRatio(1f)
		.padding(8.dp)
		.testTag(tag),
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.elevation_small))) {
		Box(
			contentAlignment = Alignment.BottomStart, modifier = Modifier.background(
				brush = Brush.verticalGradient(
					colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f))
				)
			)
		) {
			Image(
				painter = painterResource(id = photo.resourceId),
				contentDescription = photo.title,
				contentScale = ContentScale.Crop,
				modifier = Modifier.fillMaxSize()
			)
			Column(
				modifier = Modifier
					.align(Alignment.BottomStart)
					.padding(dimensionResource(id = R.dimen.padding_small))
					.background(Color.Black.copy(alpha = 0.6f))
			) {
				Text(
					text = photo.title,
					style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
				)
				Text(
					text = "$${photo.price}",
					style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
				)
			}
		}
	}
}


@Preview
@Composable
fun PhotoScreenPreview() {
	val viewModel = AppViewModel()
	val db = Database()
	viewModel.updateSelection(
		mode = SelectionMode.ARTIST, selectedItem = db.findAllArtists()[0].name
	)
	AppTheme {
		PhotoScreen(viewModel, rememberNavController())
	}
}