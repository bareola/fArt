package com.example.fart

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fart.data.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CheckoutScreen(viewModel: AppViewModel, navController: NavController) {
	val uiState = viewModel.uiState.collectAsState()
	val openDialog = remember { mutableStateOf(false) }

	Scaffold {
		Column(modifier = Modifier.padding(16.dp)) { // Cart summary
			Text(text = "Order Summary", style = MaterialTheme.typography.headlineMedium)
			LazyColumn {
				items(uiState.value.cart) { item ->
					Text("${item.photo.title} - $${item.price}", style = MaterialTheme.typography.bodySmall)
				}
			}

			Spacer(modifier = Modifier.height(16.dp))

			Card(modifier = Modifier.fillMaxWidth()) {
				Column(modifier = Modifier.padding(16.dp)) {
					Text(text = "Payment Method", style = MaterialTheme.typography.bodyLarge)
					Text(
						text = "Visa: **** **** **** 1234",
						style = MaterialTheme.typography.bodySmall
					)
					Text(text = "Expiration: 01/23", style = MaterialTheme.typography.bodySmall)
					Text(text = "CVC: 123", style = MaterialTheme.typography.bodySmall)
				}
			}

			Spacer(modifier = Modifier.height(16.dp))

			// Pay Now button
			PayButton(openDialog)
		}
	}

	if (openDialog.value) {
		AlertDialog(onDismissRequest = { openDialog.value = false }, confirmButton = {
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
		}, text = { Text("Your payment has been processed!") })
	}
}

@Composable
fun PayButton(openDialog: MutableState<Boolean>) {
	Button(
		onClick = { openDialog.value = true }, modifier = Modifier.fillMaxWidth()
	) {
		Text("Pay Now")
	}
}