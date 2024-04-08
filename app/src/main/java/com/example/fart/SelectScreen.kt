package com.example.fart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fart.data.AppUiState
import com.example.fart.data.AppViewModel
import com.example.fart.data.ListItem
import com.example.fart.data.SelectionMode

@Composable
fun ItemCard(item: ListItem, onItemSelect: (String) -> Unit) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable(onClick = { onItemSelect(item.name()) })
			.padding(vertical = dimensionResource(R.dimen.padding_small)),
		shape = RoundedCornerShape(dimensionResource(R.dimen.card_rounded_corner)),
		elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.elevation_small)),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface,
			contentColor = MaterialTheme.colorScheme.onSurface
		)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
		) {
			Image(
				painter = painterResource(
					id = when (item) {
						is ListItem.ArtistItem -> item.artist.picture
						is ListItem.CategoryItem -> item.category.picture
					}
				),
				contentDescription = "${item.name()} photo",
				modifier = Modifier
					.size(64.dp)
					.clip(RoundedCornerShape(50))
			)
			Spacer(modifier = Modifier.width(16.dp))
			Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))) {
				Text(
					text = item.name(),
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				val photos = when (item) {
					is ListItem.ArtistItem -> item.artist.photos
					is ListItem.CategoryItem -> item.photos
				}
				Text(
					text = "Photos: ${photos.size}",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				Text(text = "Max Price: $${photos.maxOfOrNull { it.price } ?: "N/A"}",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant)
			}
		}
	}
}


@Composable
fun SelectList(
	items: List<ListItem>,
	onItemSelect: (String) -> Unit,
	paddingValues: PaddingValues
) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxWidth()
	) {
		items.forEach { item ->
			ItemCard(item = item, onItemSelect = onItemSelect)
		}
	}
}


@Composable
fun SelectScreen(
	navigateToPhotoScreen: (String) -> Unit,
	viewModel: AppViewModel,
	navController: NavController
) {
	val uiState by viewModel.uiState.collectAsState()
	Scaffold(topBar = {
		CustomAppBar(
			title = getSelectScreenTitle(uiState.selectionMode),
			navController = navController
		)
	}, content = { paddingValues ->
		SelectList(
			items = getItemsForSelectionMode(uiState), onItemSelect = { selectedItem ->
				viewModel.updateSelection(uiState.selectionMode, selectedItem)
				navigateToPhotoScreen(selectedItem)
			}, paddingValues = paddingValues
		)
	})
}

fun getSelectScreenTitle(selectionMode: SelectionMode): String = when (selectionMode) {
	SelectionMode.ARTIST -> "Select Artist"
	SelectionMode.CATEGORY -> "Select Category"
	else -> "Select Mode"
}

fun getItemsForSelectionMode(uiState: AppUiState): List<ListItem> = when (uiState.selectionMode) {
	SelectionMode.ARTIST -> uiState.artistItems
	SelectionMode.CATEGORY -> uiState.categoryItems
	else -> emptyList()
}


