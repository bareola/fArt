import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.assertIsDisplayed
import com.example.fart.AppNavigator
import com.example.fart.data.AppViewModel
import com.example.fart.data.SelectionMode
import com.example.fart.ui.theme.AppTheme
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class NavigationTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	@Test
	fun firstTest() {
		val viewModel = AppViewModel()

		composeTestRule.setContent {
			AppTheme {
				AppNavigator(viewModel = viewModel)
			}
		}

		//first screen
		composeTestRule.onNodeWithTag("category_button").assertIsDisplayed().performClick()
		composeTestRule.waitForIdle()

		// second screen - random pick
		val itemsScreen2 = composeTestRule.runOnUiThread {
			when (viewModel.uiState.value.selectionMode) {
				SelectionMode.CATEGORY -> viewModel.uiState.value.categoryItems.size
				SelectionMode.ARTIST -> viewModel.uiState.value.artistItems.size
				else -> 0
			}
		}
		if (itemsScreen2 > 0) {
			val randomIndex = Random.nextInt(itemsScreen2)
			println("Random index 2screen: $randomIndex")
			composeTestRule.waitForIdle()
			composeTestRule.onNodeWithTag("itemCard${randomIndex + 1}").performScrollTo().assertIsDisplayed().performClick()
		}

		// third screen
		composeTestRule.waitForIdle()
		val itemsScreen3 = composeTestRule.runOnUiThread {
			viewModel.uiState.value.selectedItems.size}
		if (itemsScreen3 > 0) {
			val randomIndex = Random.nextInt(itemsScreen3)
			println("Random index 3screen: $randomIndex")
			composeTestRule.waitForIdle()
			composeTestRule.onNodeWithTag("photoCard${randomIndex + 1}").performScrollTo().assertIsDisplayed().performClick()
		}
		// forth screen
		composeTestRule.onNodeWithTag("size1").performScrollTo().assertIsDisplayed().performClick()
		composeTestRule.onNodeWithTag("material1").performScrollTo().assertIsDisplayed().performClick()
		composeTestRule.onNodeWithTag("width1").performScrollTo().assertIsDisplayed().performClick()
		composeTestRule.onNodeWithTag("addToCartButton").performScrollTo().assertIsDisplayed().performClick()
	}
}
