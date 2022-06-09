package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.MovieSummary
import com.rrvieira.trendyt.model.MovieDetails

interface MoviesRepository {
    suspend fun fetchPopularMovies(page: Int): Result<List<MovieSummary>>

    suspend fun fetchMovieDetails(id: Int): Result<MovieDetails>
}
