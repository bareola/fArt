package com.example.fart

import com.example.fart.data.AppViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fart.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			AppTheme {
				val viewModel: AppViewModel = viewModel()
				AppNavigator(viewModel = viewModel)

			}
		}
	}
}
