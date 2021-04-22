package com.gowtham.compose_ratingbar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gowtham.compose_ratingbar.ui.theme.ComposeRatingBarTheme
import com.gowtham.ratingbar.StarRatingKey
import kotlinx.coroutines.flow.MutableStateFlow

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RatingBarTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun ratingFirstSetup() {
        composeTestRule
            .onNode(SemanticsMatcher.expectValue(StarRatingKey, MainActivity.initialRating)) // 2015-10-21
            .assertExists()
    }

    @Test
    fun starCount() {
        // Context of the app under test.
        composeTestRule.onAllNodesWithTag(testTag = "RatingStar").assertCountEquals(5)
    }

    @Test
    fun dragHorizontally() {
        composeTestRule
            .onNode(SemanticsMatcher.keyIsDefined(StarRatingKey)) // 2015-10-21
            .performGesture { swipe(
                Offset(x = 0f,y = 0f),Offset(x = 200f,y = 0f),
                durationMillis = 4000L) }
        composeTestRule
            .onNode(SemanticsMatcher.expectValue(StarRatingKey, 2f)) // 2015-10-21
            .assertExists()
    }

    @Test
    fun starClick(){
        composeTestRule.onAllNodesWithTag(testTag = "RatingStar")[3].performClick()

        composeTestRule
            .onNode(SemanticsMatcher.expectValue(StarRatingKey, 4f)) // 2015-10-21
            .assertExists()
    }

}