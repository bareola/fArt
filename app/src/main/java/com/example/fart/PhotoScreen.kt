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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fart.data.AppUiState
import com.example.fart.data.AppViewModel
import com.example.fart.data.Photo

@Composable
fun PhotoScreen(
	viewModel: AppViewModel,
	navController: NavController,
	navigateToSinglePhotoScreen: (Int) -> Unit = {}
) {
	val uiState = viewModel.uiState.collectAsState().value
	Scaffold(topBar = { CustomAppBar(title = "Photos", navController = navController) },
		content = { paddingValues ->
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
				viewModel = viewModel
			)
		}
	}
}


@Composable
fun PhotoCard(
	photo: Photo,
	navigateToSinglePhotoScreen: (Int) -> Unit = {},
	viewModel: AppViewModel
) {
	val imageId = photo.resourceId

	Card(modifier = Modifier
		.clickable {
			viewModel.setSelectedItem(photo); navigateToSinglePhotoScreen(
			photo.id
		)
		}
		.fillMaxWidth()
		.aspectRatio(1f)
		.padding(8.dp),
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
				painter = painterResource(id = imageId),
				contentDescription = photo.title,
				contentScale = ContentScale.Crop,
				modifier = Modifier.fillMaxSize()
			)
			Column(
				modifier = Modifier
					.align(Alignment.BottomStart)
					.padding(8.dp)
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
