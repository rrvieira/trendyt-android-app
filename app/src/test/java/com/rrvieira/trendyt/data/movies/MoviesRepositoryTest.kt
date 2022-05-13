package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRepositoryTest {

    @Test
    fun fetchPopularMovies() = runTest {
        val mockedResponse = Result.success(
            listOf(
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
        )

        val mockMoviesRemoteDataSource = mockk<MoviesRemoteDataSource>()
        val pageToFetch = 1

        coEvery { mockMoviesRemoteDataSource.getPopularMovies(pageToFetch) } returns mockedResponse

        val moviesRepository = MoviesRepository(mockMoviesRemoteDataSource)

        assertEquals(mockedResponse, moviesRepository.fetchPopularMovies(pageToFetch))
    }
}
