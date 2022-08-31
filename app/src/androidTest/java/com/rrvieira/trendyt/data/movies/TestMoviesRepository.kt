package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.MovieDetails
import com.rrvieira.trendyt.model.MovieSummary
import java.util.*

class TestMoviesRepository : MoviesRepository {

    override suspend fun fetchPopularMovies(page: Int): Result<List<MovieSummary>> = Result.success(
        popularMoviesList
    )

    override suspend fun fetchMovieDetails(id: Int): Result<MovieDetails> {
        val movieDetails = movieDetailsList.find { it.id == id }

        return movieDetails?.let {
            Result.success(movieDetails)
        } ?: Result.failure(Throwable("Movie not found"))
    }

    companion object {
        val popularMoviesList = listOf(
            MovieSummary(
                id = 0,
                title = "Sonic the Hedgehog 2",
                imageUrl = "sonic-image.jpg",
                rating = 7.0f,
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time
            ),
            MovieSummary(
                id = 1,
                title = "The Batman",
                imageUrl = "batman-image.jpg",
                rating = 8.0f,
                releaseDate = GregorianCalendar(2022, Calendar.MAY, 15).time
            ),
            MovieSummary(
                id = 2,
                title = "Prey",
                imageUrl = "prey-image.jpg",
                rating = 8.0f,
                releaseDate = GregorianCalendar(2022, Calendar.AUGUST, 2).time
            ),
            MovieSummary(
                id = 3,
                title = "Nope",
                imageUrl = "nope-image.jpg",
                rating = 7.5f,
                releaseDate = GregorianCalendar(2022, Calendar.JULY, 22).time
            ),
            MovieSummary(
                id = 4,
                title = "Bullet Train",
                imageUrl = "bullet-image.jpg",
                rating = 7.4f,
                releaseDate = GregorianCalendar(2022, Calendar.AUGUST, 5).time
            )
        )

        val movieDetailsList = listOf(
            MovieDetails(
                id = 1,
                title = "The Batman",
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                runtime = 240,
                tagline = "Tagline text",
                overview = "Overview body",
                voteAverage = 7.8f,
                genres = listOf("Action", "Crime"),
                backdropUrl = "http://some-url/another-image.jpg",
                posterUrl = "http://some-url/image.jpg"
            )
        )
    }
}
