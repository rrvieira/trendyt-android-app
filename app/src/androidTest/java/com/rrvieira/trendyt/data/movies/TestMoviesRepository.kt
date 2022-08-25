package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.MovieDetails
import com.rrvieira.trendyt.model.MovieSummary
import java.util.*

class TestMoviesRepository : MoviesRepository {
    override suspend fun fetchPopularMovies(page: Int): Result<List<MovieSummary>> = Result.success(
        listOf(
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
    )

    override suspend fun fetchMovieDetails(id: Int): Result<MovieDetails> {
        TODO("Not yet implemented")
    }
}
