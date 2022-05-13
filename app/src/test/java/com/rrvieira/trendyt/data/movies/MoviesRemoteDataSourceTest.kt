package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.api.MoviesApiClient
import com.rrvieira.trendyt.api.responses.PopularMovie
import com.rrvieira.trendyt.api.responses.PopularMoviesResponse
import com.rrvieira.trendyt.model.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRemoteDataSourceTest {

    @Test
    fun getPopularMovies() = runTest {
        val popularMoviesResponse = PopularMoviesResponse(
            page = 1,
            popularMovies = listOf(
                PopularMovie(
                    adult = false,
                    backdropPath = "/egoyMDLqCxzjnSrWOz50uLlJWmD.jpg",
                    genreIds = listOf(28, 878, 35, 10751, 12),
                    id = 675353,
                    originalLanguage = "en",
                    originalTitle = "Sonic the Hedgehog 2",
                    overview = "After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero.",
                    popularity = 10974.265,
                    posterPath = "/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                    releaseDate = "2022-03-30",
                    title = "Sonic the Hedgehog 2",
                    video = false,
                    voteAverage = 7.7,
                    voteCount = 1172
                ),
                PopularMovie(
                    adult = false,
                    backdropPath = "/5P8SmMzSNYikXpxil6BYzJ16611.jpg",
                    genreIds = listOf(80, 9648, 53),
                    id = 414906,
                    originalLanguage = "en",
                    originalTitle = "The Batman",
                    overview = "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.",
                    popularity = 4447.894,
                    posterPath = "/5P8SmMzSNYikXpxil6BYzJ16611.jpg",
                    releaseDate = "2022-03-01",
                    title = "The Batman",
                    video = false,
                    voteAverage = 7.8,
                    voteCount = 4416
                ),
            ),
            totalPages = 1,
            totalResults = 2
        )
        val mockedResponse = Result.success(popularMoviesResponse)
        val expected = Result.success(
            listOf(
                Movie(
                    title = popularMoviesResponse.popularMovies[0].title,
                    imageUrl = popularMoviesResponse.popularMovies[0].posterPath,
                    overview = popularMoviesResponse.popularMovies[0].overview,
                    category = "28, 878, 35, 10751, 12"
                ),
                Movie(
                    title = popularMoviesResponse.popularMovies[1].title,
                    imageUrl = popularMoviesResponse.popularMovies[1].posterPath,
                    overview = popularMoviesResponse.popularMovies[1].overview,
                    category = "80, 9648, 53"
                ),
            )
        )
        val pageToFetch = 1

        val mockMoviesApiClient = mockk<MoviesApiClient>()
        coEvery { mockMoviesApiClient.getPopularMovies(pageToFetch) } returns mockedResponse

        val moviesRemoteDataSource =
            MoviesRemoteDataSource(mockMoviesApiClient, UnconfinedTestDispatcher(testScheduler))

        assertEquals(expected, moviesRemoteDataSource.getPopularMovies(pageToFetch))
    }
}
