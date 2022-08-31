package com.rrvieira.trendyt.ui.home

import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.model.MovieSummary
import com.rrvieira.trendyt.ui.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest: BaseViewModelTest() {

    @Test
    fun refreshMoviesSuccess() = runTest {
        val expected = listOf(
            MovieSummary(
                id = 0,
                title = "Sonic the Hedgehog 2",
                imageUrl = "http://image.com/sonic.jpg",
                rating = 7.0f,
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 30).time
            ),
            MovieSummary(
                id = 1,
                title = "The Batman",
                imageUrl = "http://image.com/batman.jpg",
                rating = 8.0f,
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time
            ),
        )

        val mockMoviesRepo = mockk<MoviesRepository> {
            coEvery { fetchPopularMovies(1) } returns Result.success(expected)
        }

        val homeViewModel = HomeViewModel(mockMoviesRepo)

        val state = homeViewModel.uiState.value as? HomeUiState.HasMovies
        assertNotNull("Current state not expected", state)
        assertEquals(expected, state?.moviesFeed)
        assertEquals(emptyList<String>(), state?.errorMessages)
        assertEquals(false, state?.isLoading)
    }

    @Test
    fun refreshMoviesError() = runTest {
        val errorMessage = "some error"
        val expected = listOf(errorMessage)

        val mockMoviesRepo = mockk<MoviesRepository> {
            coEvery { fetchPopularMovies(1) } returns Result.failure(Throwable(errorMessage))
        }

        val homeViewModel = HomeViewModel(mockMoviesRepo)

        val state = homeViewModel.uiState.value
        assertEquals(expected, state.errorMessages)
        assertEquals(false, state.isLoading)
    }
}
