package com.rrvieira.trendyt.api

import com.rrvieira.trendyt.api.responses.ConfigurationResponse
import com.rrvieira.trendyt.api.responses.MovieResponse
import com.rrvieira.trendyt.api.responses.PopularMoviesResponse

interface MoviesApiClient {
    suspend fun getApiConfiguration(): Result<ConfigurationResponse>

    suspend fun getPopularMovies(page: Int): Result<PopularMoviesResponse>

    suspend fun getMovieDetails(id: Int): Result<MovieResponse>
}
