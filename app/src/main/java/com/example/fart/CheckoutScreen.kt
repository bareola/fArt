package com.example.fart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fart.data.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CheckoutScreen(viewModel: AppViewModel, navController: NavController) {
	val uiState = viewModel.uiState.collectAsState()
	val openDialog = remember { mutableStateOf(false) }

	Scaffold {
		Column(modifier = Modifier.padding(16.dp)) {
			Text("Order Summary", style = MaterialTheme.typography.headlineMedium)

			LazyColumn {
				items(uiState.value.cart) { item ->
					Card(
						modifier = Modifier
							.fillMaxWidth()
							.padding(vertical = 4.dp),
						elevation = CardDefaults.cardElevation(2.dp)
					) {
						Column(modifier = Modifier.padding(16.dp)) {
							Text(
								"${item.photo.title} - $${item.price}",
								style = MaterialTheme.typography.bodyMedium
							)
						}
					}
				}
			}

			Spacer(modifier = Modifier.height(16.dp))

			Card(
				modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)
			) {
				Column(modifier = Modifier.padding(16.dp)) {
					Text("Payment Method", style = MaterialTheme.typography.headlineSmall)
					Text("Visa: **** **** **** 1234", style = MaterialTheme.typography.bodyMedium)
					Text("Expiration: 01/23", style = MaterialTheme.typography.bodyMedium)
					Text("CVC: 123", style = MaterialTheme.typography.bodyMedium)
				}
			}

			Spacer(modifier = Modifier.height(16.dp))
			PayButton(openDialog)
		}
	}

	if (openDialog.value) {
		AlertDialog(onDismissRequest = { openDialog.value = false },
			confirmButton = {
				Button(onClick = {
					viewModel.clearCart()
					navController.navigate(Screen.Main.route) {
						popUpTo(navController.graph.startDestinationId) {
							inclusive = true
						}
					}
				}) {
					Text("OK")
				}
			},
			text = { Text(stringResource(id = R.string.payment_processed)) },
			title = { Text(stringResource(id = R.string.payment_title)) })
	}
}


@Composable
fun PayButton(openDialog: MutableState<Boolean>) {
	Button(
		onClick = { openDialog.value = true },
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		shape = RoundedCornerShape(8.dp)
	) {
		Text("Pay Now")
	}
}
