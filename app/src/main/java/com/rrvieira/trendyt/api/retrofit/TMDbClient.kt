package com.rrvieira.trendyt.api.retrofit

import com.rrvieira.trendyt.api.MoviesApiClient
import com.rrvieira.trendyt.api.responses.MovieResponse
import com.rrvieira.trendyt.api.responses.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbClient : MoviesApiClient {

    @GET("movie/popular")
    override suspend fun getPopularMovies(@Query("page") page: Int): Result<PopularMoviesResponse>

    @GET("movie/{id}")
    override suspend fun getMovieDetails(@Path("id") id: Int): Result<MovieResponse>
}
