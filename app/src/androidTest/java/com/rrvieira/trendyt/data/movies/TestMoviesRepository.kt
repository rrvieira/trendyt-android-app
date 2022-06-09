package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.MovieSummary

class TestMoviesRepository : MoviesRepository {
    override suspend fun fetchPopularMovies(page: Int): Result<List<MovieSummary>> = Result.success(
        listOf(
            MovieSummary(
                title = "Sonic the Hedgehog 2",
                imageUrl = "/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg",
                overview = "After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero.",
                category = listOf(28, 878, 35, 10751, 12).joinToString(", ")
            ),
            MovieSummary(
                title = "The Batman",
                imageUrl = "/5P8SmMzSNYikXpxil6BYzJ16611.jpg",
                overview = "In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.",
                category = listOf(80, 9648, 53).joinToString(", ")
            ),
        )
    )
}
