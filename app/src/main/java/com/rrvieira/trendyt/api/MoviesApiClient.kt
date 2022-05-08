package com.rrvieira.trendyt.api

import com.rrvieira.trendyt.api.responses.PopularMoviesResponse

interface MoviesApiClient {
    suspend fun getPopularMovies(page: Int): Result<PopularMoviesResponse>
}
