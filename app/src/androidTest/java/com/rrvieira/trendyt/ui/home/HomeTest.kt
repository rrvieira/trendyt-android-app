package com.rrvieira.trendyt.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
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

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.setContent {
            TrendytApp()
        }
    }

    @Test
    fun popularMoviesDisplayed() {
        composeTestRule.onNodeWithText("The Batman").assertIsDisplayed()
    }
}
