package com.example.fart

import AppViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fart.ui.theme.FArtTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			FArtTheme {
				val appViewModel = AppViewModel()
				AppNavigator(appViewModel)

			}
		}
	}
}
