package com.example.fart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fart.ui.theme.FArtTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			FArtTheme { // A surface container using the 'background' color from the theme
				MainScreen()

			}
		}
	}
}


@Preview
@Composable
fun FullPreview() {
	FArtTheme {
		MainScreen()
	}}

@Preview
@Composable
fun PhotocardPreview() {
	FArtTheme {
	}
}
@Preview
@Composable
fun PhotoScreenPreview() {
	FArtTheme {
		PhotoScreen()

	}
}