package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.Movie
import com.rrvieira.trendyt.model.MovieDetails

interface MoviesRepository {
    suspend fun fetchPopularMovies(page: Int): Result<List<Movie>>

    suspend fun fetchMovieDetails(id: Int): Result<MovieDetails>
}
