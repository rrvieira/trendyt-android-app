package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.Movie

interface MoviesRepository {
    suspend fun fetchPopularMovies(page: Int) : Result<List<Movie>>
}
