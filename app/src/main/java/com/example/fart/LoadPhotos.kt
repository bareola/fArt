package com.example.fart

import android.widget.GridView
import android.widget.TextClock
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fart.data.Database
import com.example.fart.ui.theme.FArtTheme

val db = Database()


@Composable
fun PhotoScreen() {
	}


@Preview
@Composable
fun PhotoScreenPreview() {
	FArtTheme {
		PhotoScreen()

		}

	}
