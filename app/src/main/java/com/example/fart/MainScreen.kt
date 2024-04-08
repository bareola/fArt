package com.example.fart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fart.data.AppViewModel
import com.example.fart.data.CartItem
import com.example.fart.data.SelectionMode

@Composable
fun MainScreen(
	navController: NavController,
	navigateToSelectScreen: (String) -> Unit,
	appViewModel: AppViewModel
) {
	val uiState by appViewModel.uiState.collectAsState()

	Column {
		CustomAppBar(navController = navController, title = stringResource(R.string.app_name_full))
		ChooseBasedOn(navigateToSelectScreen, appViewModel)
		ShoppingCart(uiState.cart, removeFromCart = { cartItem ->
			appViewModel.removeFromCart(cartItem)
		}, onCheckout = {
			navController.navigate(Screen.Checkout.route)
		})
	}
}

@Composable
fun ChooseBasedOn(navigateToSelectScreen: (String) -> Unit, appViewModel: AppViewModel) {
	Column(
		modifier = Modifier
			.padding(dimensionResource(R.dimen.padding_large))
	) {
		Text(
			text = stringResource(id = R.string.choose_based_on),
			style = MaterialTheme.typography.bodyLarge,
			modifier = Modifier.align(Alignment.CenterHorizontally)
		)
		Row(modifier = Modifier.padding(top = 8.dp)) {
			Button(onClick = {
				appViewModel.updateSelection(SelectionMode.ARTIST)
				navigateToSelectScreen(SelectionMode.ARTIST.name)
			}, modifier = Modifier.weight(1f)) {
				Text(text = stringResource(id = R.string.artist))
			}
			Spacer(modifier = Modifier.width(8.dp))
			Button(onClick = {
				appViewModel.updateSelection(SelectionMode.CATEGORY)
				navigateToSelectScreen(SelectionMode.CATEGORY.name)
			}, modifier = Modifier.weight(1f)) {
				Text(text = stringResource(id = R.string.category))
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(navController: NavController, title: String) {
	TopAppBar(title = {
		Box(modifier = Modifier.fillMaxWidth()) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.align(Alignment.Center)
			) {
				val logo: Painter = painterResource(id = R.drawable.logonotext)
				Image(
					painter = logo,
					contentDescription = "Logo",
					modifier = Modifier
						.height(dimensionResource(R.dimen.icon))
						.width(dimensionResource(R.dimen.icon))
						.clip(RoundedCornerShape(50))
				)
				Text(
					text = title,
					textAlign = TextAlign.Center,
					modifier = Modifier.padding(horizontal = 50.dp),
					style = MaterialTheme.typography.titleLarge
				)
			}
		}
	}, navigationIcon = {
		if (navController.previousBackStackEntry != null) {
			IconButton(onClick = { navController.navigateUp() }) {
				Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
			}
		}
	}, modifier = Modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(
		containerColor = MaterialTheme.colorScheme.primaryContainer,
		titleContentColor = MaterialTheme.colorScheme.onSurface
	)
	)
}

@Composable
fun ShoppingCart(cart: List<CartItem>, removeFromCart: (CartItem) -> Unit, onCheckout: () -> Unit) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(dimensionResource(R.dimen.padding_large))
	) {
		Text(text = "Total Pictures: ${cart.size}", style = MaterialTheme.typography.bodyLarge)
		val totalPrice = cart.sumOf { it.price }
		Text(text = "Total Price: $$totalPrice", style = MaterialTheme.typography.bodyLarge)

		Spacer(modifier = Modifier.height(16.dp))

		cart.forEach { cartItem ->
			Card(
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp),
				border = BorderStroke(1.dp, Color.Gray),
				shape = RoundedCornerShape(8.dp)
			) {
				Row(Modifier.padding(8.dp)) {
					Image(
						painter = painterResource(id = cartItem.photo.resourceId),
						contentDescription = cartItem.photo.title,
						modifier = Modifier
							.weight(1f)
							.aspectRatio(1f)
							.align(Alignment.CenterVertically),
						contentScale = ContentScale.Crop
					)
					Column(
						modifier = Modifier
							.weight(2f)
							.padding(horizontal = 8.dp)
							.align(Alignment.CenterVertically)
					) {
						Text(
							text = cartItem.photo.title, style = MaterialTheme.typography.bodyLarge
						)
						Text(
							text = "Size: ${cartItem.size.name}",
							style = MaterialTheme.typography.bodySmall
						)
						Text(
							text = "Frame: ${cartItem.frame.type}",
							style = MaterialTheme.typography.bodySmall
						)
						Text(
							text = "Width: ${cartItem.frameWidth.size}",
							style = MaterialTheme.typography.bodySmall
						)
						Text(
							text = "Price: $${cartItem.price}",
							style = MaterialTheme.typography.bodySmall
						)
					}
					IconButton(
						onClick = { removeFromCart(cartItem) },
						modifier = Modifier.align(Alignment.Top)
					) {
						Icon(Icons.Default.Delete, contentDescription = "Remove")
					}
				}
			}
		}

		Spacer(modifier = Modifier.height(16.dp))

		Button(
			onClick = onCheckout,
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 0.dp),
			shape = RoundedCornerShape(4.dp)
		) {
			Text("Checkout")
		}
	}
}
