package com.rrvieira.trendyt.ui.details

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.rrvieira.trendyt.data.movies.TestMoviesRepository
import com.rrvieira.trendyt.ui.MainActivity
import com.rrvieira.trendyt.ui.TrendytApp
import com.rrvieira.trendyt.utils.DateFormatter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailsTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    private val movieDetails = TestMoviesRepository.movieDetailsList.first()

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            TrendytApp()
        }
        //navigate to details
        composeTestRule.onNodeWithText(
            text = movieDetails.title
        ).performClick()
    }

    @Test
    fun detailsIsDisplayed() {
        //assert details' key fields are displayed
        composeTestRule.onNodeWithText(
            text = movieDetails.title, substring = true
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = movieDetails.tagline).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = movieDetails.voteAverage.toString()).assertIsDisplayed()
        movieDetails.genres.forEach { genre ->
            val node = composeTestRule.onNodeWithText(text = genre, substring = true)
            node.assertIsDisplayed()
            node.performTouchInput { swipeLeft() }
        }
        composeTestRule.onNodeWithText(text = movieDetails.overview).assertIsDisplayed()
    }
}
