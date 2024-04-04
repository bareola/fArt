package com.example.fart.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fart.R

// Set of Material typography styles to start with
val Typography = Typography(
	bodyLarge = TextStyle(
		fontFamily = FontFamily.Default,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		lineHeight = 24.sp,
		letterSpacing = 0.5.sp
	)/* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */)
	val montSerratFamily = FontFamily(
		Font(R.font.montserrat_light, FontWeight.Light),
Font(R.font.montserrat_regular, FontWeight.Normal),
Font(R.font.montserrat_medium, FontWeight.Medium),
Font(R.font.montserrat_bold, FontWeight.Bold),
Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
Font(R.font.montserrat_black, FontWeight.Black),
Font(R.font.montserrat_bolditalic, FontWeight.Bold, FontStyle.Italic),
Font(R.font.montserrat_lightitalic, FontWeight.Light, FontStyle.Italic)
)

val CustomTypography = Typography(
	displayLarge = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 57.sp,
		letterSpacing = 0.sp
	),
	displayMedium = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 45.sp,
		letterSpacing = 0.sp
	),
	displaySmall = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Bold,
		fontSize = 36.sp,
		letterSpacing = 0.sp
	),
	headlineLarge = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 32.sp,
		letterSpacing = 0.sp
	),
	headlineMedium = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 28.sp,
		letterSpacing = 0.sp
	),
	headlineSmall = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 24.sp,
		letterSpacing = 0.sp
	),
	titleLarge = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 22.sp,
		letterSpacing = 0.sp
	),
	titleMedium = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 16.sp,
		letterSpacing = 0.15.sp
	),
	titleSmall = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		letterSpacing = 0.1.sp
	),
	bodyLarge = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		letterSpacing = 0.5.sp
	),
	bodyMedium = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 14.sp,
		letterSpacing = 0.25.sp
	),
	bodySmall = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Normal,
		fontSize = 12.sp,
		letterSpacing = 0.4.sp
	),
	labelLarge = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 14.sp,
		letterSpacing = 0.1.sp
	),
	labelMedium = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 12.sp,
		letterSpacing = 0.5.sp
	),
	labelSmall = TextStyle(
		fontFamily = montSerratFamily,
		fontWeight = FontWeight.Medium,
		fontSize = 11.sp,
		letterSpacing = 0.5.sp
	)
)
