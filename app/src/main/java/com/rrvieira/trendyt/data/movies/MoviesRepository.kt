package com.rrvieira.trendyt.data.movies

import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) {
    suspend fun fetchPopularMovies(page: Int) = moviesRemoteDataSource.getPopularMovies(page)
}
