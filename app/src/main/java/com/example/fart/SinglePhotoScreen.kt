package com.example.fart

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fart.data.AppViewModel
import com.example.fart.data.Frame
import com.example.fart.data.Framewidth
import com.example.fart.data.Photo
import com.example.fart.data.Size
import com.example.fart.ui.theme.AppTheme
import kotlin.math.pow
import kotlin.math.roundToInt


@Composable
fun FrameChoiceRadioButton(viewModel: AppViewModel) {
	val uiState = viewModel.uiState.collectAsState()

	val options = listOf(
		Pair(
			R.string.size,
			Size.entries.map { it.type to "${it.type}: $${it.price}" }
		),
		Pair(
			R.string.frame_material,
			Frame.entries.map {
				it.type to "${it.type}: $${((uiState.value.selectedSize.price * it.price) - uiState.value.selectedSize.price).roundTo(2)}"
			}
		),
		Pair(
			R.string.frame_width,
			Framewidth.entries.map {
				it.size to "${it.size}: $${((uiState.value.selectedSize.price + (uiState.value.selectedSize.price * uiState.value.selectedFrame.price) - uiState.value.selectedSize.price) * it.price - (uiState.value.selectedSize.price + (uiState.value.selectedSize.price * uiState.value.selectedFrame.price) - uiState.value.selectedSize.price)).roundTo(2)}"
			}
		)
	)

	Column {
		options.forEach { (titleRes, entries) ->
			SettingCard(
				title = stringResource(id = titleRes),
				entries = entries,
				selectedOption = when (titleRes) {
					R.string.size -> uiState.value.selectedSize.type
					R.string.frame_material -> uiState.value.selectedFrame.type
					R.string.frame_width -> uiState.value.selectedFrameWidth.size
					else -> ""
				},
				onOptionSelected = { selectedType ->
					when (titleRes) {
						R.string.size -> Size.fromType(selectedType)
							?.let { viewModel.setSelectedSize(it) }

						R.string.frame_material -> Frame.fromType(selectedType)
							?.let { viewModel.setSelectedFrame(it) }

						R.string.frame_width -> Framewidth.fromSize(selectedType)
							?.let { viewModel.setSelectedFrameWidth(it) }
					}
				}
			)
		}
	}
}


@Composable
fun SettingCard(
	title: String,
	entries: List<Pair<String, String>>,
	selectedOption: String,
	onOptionSelected: (String) -> Unit
) {
	Card(
		border = BorderStroke(1.dp, Color.Gray),
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 4.dp, horizontal = 8.dp)
	) {
		Row(modifier = Modifier.padding(8.dp)) {
			Text(
				text = title,
				style = MaterialTheme.typography.labelLarge,
				modifier = Modifier.padding(bottom = 4.dp)
			)
			RadioButtonGroup(entries, selectedOption, onOptionSelected)
		}
	}
}

@Composable
fun RadioButtonGroup(
	options: List<Pair<String, String>>,
	selectedOption: String,
	onOptionSelected: (String) -> Unit
) {
	Column(modifier = Modifier.fillMaxWidth()) {
		options.forEach { (type, displayString) ->
			Row(Modifier
				.clickable { onOptionSelected(type) }
				.align(Alignment.End)) {
				Text(
					text = displayString,
					style = MaterialTheme.typography.bodySmall,
					modifier = Modifier
						.padding(start = 8.dp)
						.align(Alignment.CenterVertically)
				)
				RadioButton(selected = type == selectedOption, onClick = { onOptionSelected(type) })

			}
		}
	}
}

@Composable
fun SinglePhotoScreen(
	viewModel: AppViewModel, navController: NavController
) {
	val uiState = viewModel.uiState.collectAsState()
	val photo = uiState.value.selectedItem

	Scaffold(topBar = {
		CustomAppBar(navController = navController, title = photo.title)
	}) { innerPadding ->
		PhotoDetailContent(
			photo = photo,
			modifier = Modifier.padding(innerPadding),
			viewModel = viewModel,
			navController = navController
		)
	}
}


@Composable
fun PhotoDetailContent(
	photo: Photo,
	modifier: Modifier = Modifier,
	viewModel: AppViewModel,
	navController: NavController
) {
	LazyColumn(
		modifier = modifier
			.fillMaxSize()
			.padding(horizontal = 12.dp, vertical = 8.dp)
	) {
		item {
			Card(elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.elevation_small))) {
				Image(
					painter = painterResource(id = photo.resourceId),
					contentDescription = photo.title,
					modifier = Modifier
						.fillMaxWidth()
						.aspectRatio(1.5f),
					contentScale = ContentScale.Crop
				)
			}
			Spacer(modifier = Modifier.height(8.dp))
		}
		item {
			Card(
				elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.elevation_small)),
				modifier = Modifier.fillMaxWidth(),
			) {
				Column(Modifier.padding(12.dp)) {
					 Card(
						modifier = Modifier.fillMaxWidth()
					) {
						Text(
							text = stringResource(id = R.string.photo_details),
							style = MaterialTheme.typography.titleMedium
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
					}
					Divider(
						color = Color.Gray,
						thickness = 1.dp,
						modifier = Modifier.padding(vertical = 8.dp)
					)
					Text(
						text = stringResource(id = R.string.photo_details_frame),
						style = MaterialTheme.typography.titleMedium
					)
					FrameChoiceRadioButton(viewModel = viewModel)
					Button(
						onClick = {
							viewModel.addToCart(photo)
							navController.navigate(Screen.Main.route) {
								popUpTo(Screen.Main.route) {
									inclusive = true
								}
							}
						},
						modifier = Modifier.fillMaxWidth(),
						elevation = ButtonDefaults.buttonElevation(0.dp)
					) {
						Text("Add to Cart")
					}
					Spacer(modifier = Modifier.height(4.dp))
					Button(
						onClick = {
							navController.navigate(Screen.Main.route) {
								popUpTo(navController.graph.startDestinationId) {
									inclusive = true
								}
							}
						},
						modifier = Modifier.fillMaxWidth(),
						elevation = ButtonDefaults.buttonElevation(0.dp)
					) {
						Text("Go Home")
					}
				}
			}
		}
	}
}

@Preview
@Composable
fun SinglePhotoScreenPreview() {
	val photo = Photo(
		id = 1,
		title = "Photo Title",
		resourceId = R.drawable.ola_giaever,
		categories = emptyList(),
		price = 100.0
	)
	val viewModel = AppViewModel()
	AppTheme {

		PhotoDetailContent(
			photo = photo,
			viewModel = viewModel,
			navController = rememberNavController()
		)
	}
}
fun Double.roundTo(decimals: Int): Double {
	val multiplier = 10.0.pow(decimals.toDouble())
	return (this * multiplier).roundToInt() / multiplier
}
