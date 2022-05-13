package com.rrvieira.trendyt.data.movies

class MoviesRepositoryImpl(private val moviesRemoteDataSource: MoviesRemoteDataSource) :
    MoviesRepository {

    override suspend fun fetchPopularMovies(page: Int) =
        moviesRemoteDataSource.getPopularMovies(page)
}
