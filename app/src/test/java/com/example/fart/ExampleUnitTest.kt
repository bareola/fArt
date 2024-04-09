package com.example.fart

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
import com.example.fart.data.CartItem
import com.example.fart.data.Category
import com.example.fart.data.Frame
import com.example.fart.data.Framewidth
import com.example.fart.data.Photo
import com.example.fart.data.Size
import org.junit.Assert.assertEquals
import org.junit.Test

class CartItemTests {

	private val testPhoto = Photo(
		id = 1,
		title = "Test Photo",
		resourceId = R.drawable.inga,
		categories = listOf(Category.Nature),
		price = 1000.0
	)

	@Test
	fun testPhotoAndSizePrice() {
		val cartItem = CartItem(
			photo = testPhoto,
			size = Size.MEDIUM,
			frame = Frame.PLASTIC,
			frameWidth = Framewidth.SMALL
		)
		val expectedPrice = 1000.0 + (150.0 * 0.75 * 0.5)
		assertEquals(
			"Price calculation for Photo and Size is incorrect",
			expectedPrice,
			cartItem.price,
			0.01
		)
	}

	@Test
	fun testPhotoSizeAndFramePrice() {
		val cartItem = CartItem(
			photo = testPhoto, size = Size.MEDIUM, frame = Frame.WOOD, frameWidth = Framewidth.SMALL
		)
		val expectedPrice = 1000.0 + (150.0 * 1.0 * 0.5)
		assertEquals(
			"Price calculation with Frame included is incorrect",
			expectedPrice,
			cartItem.price,
			0.01
		)
	}

	@Test
	fun testTotalPriceWithFrameWidth() {
		val cartItem = CartItem(
			photo = testPhoto, size = Size.LARGE, frame = Frame.METAL, frameWidth = Framewidth.LARGE
		)
		val expectedPrice = 1000.0 + (200.0 * 1.25 * 1.5)
		assertEquals(
			"Total price calculation with Frame and Frame Width is incorrect",
			expectedPrice,
			cartItem.price,
			0.01
		)
	}
}

