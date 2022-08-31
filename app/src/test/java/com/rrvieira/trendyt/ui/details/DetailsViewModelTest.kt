package com.rrvieira.trendyt.ui.details

import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.model.MovieDetails
import com.rrvieira.trendyt.rules.MainDispatcherRule
import com.rrvieira.trendyt.ui.BaseViewModelTest
import com.rrvieira.trendyt.ui.home.HomeUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest: BaseViewModelTest() {

    @Test
    fun fetchMovieDetails() = runTest {
        val movieId = 1
        val expected = MovieDetails(
            id = movieId,
            title = "The Batman",
            releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
            runtime = 240,
            tagline = "The Batman tagline",
            overview = "The Batman Overview",
            voteAverage = 7.8f,
            genres = listOf("Action", "Crime"),
            backdropUrl = "http://some-url/another-image.jpg",
            posterUrl = "http://some-url/image.jpg"
        )

        val mockMoviesRepo = mockk<MoviesRepository> {
            coEvery { fetchMovieDetails(movieId) } returns Result.success(expected)
        }

        val detailsViewModel = DetailsViewModel(mockMoviesRepo)
        detailsViewModel.fetchMovieDetails(movieId)

        val state = detailsViewModel.uiState.value as? DetailsUiState.HasDetails
        assertNotNull("Current state not expected", state)
        assertEquals(expected, state?.movieDetails)
    }

    @Test
    fun fetchMovieDetailsError() = runTest {
        val errorMessage = "some error"
        val movieId = 1

        val mockMoviesRepo = mockk<MoviesRepository> {
            coEvery { fetchMovieDetails(movieId) } returns Result.failure(Throwable(errorMessage))
        }

        val detailsViewModel = DetailsViewModel(mockMoviesRepo)
        detailsViewModel.fetchMovieDetails(movieId)

        val state = detailsViewModel.uiState.value as? DetailsUiState.Empty
        assertNotNull("Current state not expected", state)
        assertEquals(false, state?.isLoading)
        assertEquals(errorMessage, state?.errorMessage)
    }
}
