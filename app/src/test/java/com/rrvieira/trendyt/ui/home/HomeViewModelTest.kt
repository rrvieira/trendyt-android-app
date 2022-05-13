package com.rrvieira.trendyt.ui.home

import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.model.Movie
import com.rrvieira.trendyt.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun getMovieListSuccess() = runTest {
        val expected = listOf(
            Movie(
                title = "Sonic the Hedgehog 2",
                imageUrl = "/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                overview = "After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero.",
                category = listOf(28, 878, 35, 10751, 12).joinToString(", ")
            ),
            Movie(
                title = "The Batman",
                imageUrl = "/5P8SmMzSNYikXpxil6BYzJ16611.jpg",
                overview = "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.",
                category = listOf(80, 9648, 53).joinToString(", ")
            ),
        )

        val moviesRepo = mockk<MoviesRepository>()
        coEvery { moviesRepo.fetchPopularMovies(1) } returns Result.success(expected)

        val homeViewModel = HomeViewModel(moviesRepo)
        homeViewModel.getMovieList()

        assertEquals(HomeViewModelState.SUCCESS, homeViewModel.movieState.value)
        assertEquals(expected, homeViewModel.movieList)
    }

    @Test
    fun getMovieListError() = runTest {
        val errorMessage = "some error"
        val expected = Throwable(errorMessage)

        val moviesRepo = mockk<MoviesRepository>()
        coEvery { moviesRepo.fetchPopularMovies(1) } returns Result.failure(expected)

        val homeViewModel = HomeViewModel(moviesRepo)
        homeViewModel.getMovieList()

        assertEquals(HomeViewModelState.FAILURE(errorMessage), homeViewModel.movieState.value)
    }
}
