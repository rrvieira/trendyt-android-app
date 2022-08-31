package com.rrvieira.trendyt.ui.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.rrvieira.trendyt.data.movies.TestMoviesRepository
import com.rrvieira.trendyt.ui.MainActivity
import com.rrvieira.trendyt.ui.TrendytApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    private val movieList = TestMoviesRepository.popularMoviesList

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            TrendytApp()
        }
    }

    @Test
    fun popularMoviesDisplayed() {
        //assert all items are displayed
        movieList.forEach { movie ->
            val node = composeTestRule.onNodeWithText(
                text = movie.title
            )

            node.assertExists()
            node.performTouchInput {
                swipeUp()
            }
        }
    }
}
