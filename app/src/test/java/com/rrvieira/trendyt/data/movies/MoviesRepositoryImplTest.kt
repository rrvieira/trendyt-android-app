package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.api.responses.MovieResponse
import com.rrvieira.trendyt.api.responses.PopularMovie
import com.rrvieira.trendyt.api.responses.PopularMoviesResponse
import com.rrvieira.trendyt.data.configuration.ConfigurationRepository
import com.rrvieira.trendyt.model.MovieDetails
import com.rrvieira.trendyt.model.MovieSummary
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRepositoryImplTest {

    @Test
    fun fetchPopularMovies() = runTest {
        val popularMoviesResponse = PopularMoviesResponse(
            page = 1,
            popularMovies = listOf(
                PopularMovie(
                    adult = false,
                    backdropPath = "/sonic-backdropPath.jpg",
                    genreIds = listOf(28, 878, 35, 10751, 12),
                    id = 0,
                    originalLanguage = "en",
                    originalTitle = "Sonic the Hedgehog 2",
                    overview = "Sonic overview.",
                    popularity = 10.0,
                    posterPath = "/sonic-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 30).time,
                    title = "Sonic the Hedgehog 2",
                    video = false,
                    voteAverage = 7.7f,
                    voteCount = 1000
                ),
                PopularMovie(
                    adult = false,
                    backdropPath = "/batman-backdropPath.jpg",
                    genreIds = listOf(80, 9648, 53),
                    id = 1,
                    originalLanguage = "en",
                    originalTitle = "The Batman",
                    overview = "Batman overview",
                    popularity = 3.0,
                    posterPath = "/batman-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                    title = "The Batman",
                    video = false,
                    voteAverage = 7.8f,
                    voteCount = 2000
                ),
            ),
            totalPages = 1,
            totalResults = 2
        )

        val movieImageUrl = "http://some-url/image.jpg"

        val expected = Result.success(
            listOf(
                MovieSummary(
                    id = popularMoviesResponse.popularMovies[0].id,
                    title = popularMoviesResponse.popularMovies[0].title,
                    imageUrl = movieImageUrl,
                    rating = popularMoviesResponse.popularMovies[0].voteAverage,
                    releaseDate = popularMoviesResponse.popularMovies[0].releaseDate
                ),
                MovieSummary(
                    id = popularMoviesResponse.popularMovies[1].id,
                    title = popularMoviesResponse.popularMovies[1].title,
                    imageUrl = movieImageUrl,
                    rating = popularMoviesResponse.popularMovies[1].voteAverage,
                    releaseDate = popularMoviesResponse.popularMovies[1].releaseDate
                ),
            )
        )

        val pageToFetch = 1
        val mockMoviesRemoteDataSource = mockk<MoviesRemoteDataSource> {
            coEvery { getPopularMovies(pageToFetch) } returns Result.success(
                popularMoviesResponse
            )
        }
        val mockConfigurationRepository = mockk<ConfigurationRepository> {
            coEvery { fetchConfiguration() } returns Result.success(mockk {
                every { urlForBackdrop(any()) } returns movieImageUrl
            })
        }

        val moviesRepository =
            MoviesRepositoryImpl(mockMoviesRemoteDataSource, mockConfigurationRepository)

        assertEquals(expected, moviesRepository.fetchPopularMovies(pageToFetch))
    }

    @Test
    fun fetchMovieDetails() = runTest {
        val movieId = 1
        val movieGenres = listOf("Action", "Crime")
        val movieDetailsResponse = MovieResponse(
            id = movieId,
            title = "The Batman",
            tagline = "The Batman tagline",
            overview = "The Batman Overview",
            runtime = 240,
            voteAverage = 7.8f,
            releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
            genres = listOf(
                MovieResponse.Genre(id = 0, movieGenres[0]),
                MovieResponse.Genre(id = 1, movieGenres[1])
            ),
            posterPath = "/batman-poster.jpg",
            backdropPath = "/batman-backdropPath.jpg",
            adult = false,
            belongsToCollection = false,
            budget = 0,
            homepage = "",
            imdbId = "",
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            revenue = 0,
            spokenLanguages = emptyList(),
            status = "",
            video = false,
            voteCount = 0
        )

        val posterImageUrl = "http://some-url/image.jpg"
        val backdropImageUrl = "http://some-url/another-image.jpg"

        val expected = Result.success(
            MovieDetails(
                id = movieDetailsResponse.id,
                title = movieDetailsResponse.title,
                releaseDate = movieDetailsResponse.releaseDate,
                runtime = movieDetailsResponse.runtime,
                tagline = movieDetailsResponse.tagline,
                overview = movieDetailsResponse.overview,
                voteAverage = movieDetailsResponse.voteAverage,
                genres = listOf(movieGenres[0], movieGenres[1]),
                backdropUrl = backdropImageUrl,
                posterUrl = posterImageUrl
            )
        )

        val mockMoviesRemoteDataSource = mockk<MoviesRemoteDataSource> {
            coEvery { getMovieDetails(movieId) } returns Result.success(
                movieDetailsResponse
            )
        }
        val mockConfigurationRepository = mockk<ConfigurationRepository> {
            coEvery { fetchConfiguration() } returns Result.success(mockk {
                every { urlForBackdrop(movieDetailsResponse.backdropPath) } returns backdropImageUrl
                every { urlForPoster(movieDetailsResponse.posterPath) } returns posterImageUrl
            })
        }

        val moviesRepository =
            MoviesRepositoryImpl(mockMoviesRemoteDataSource, mockConfigurationRepository)

        assertEquals(expected, moviesRepository.fetchMovieDetails(movieId))
    }
}
