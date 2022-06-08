package com.rrvieira.trendyt.data.movies

class MoviesRepositoryImpl(private val moviesRemoteDataSource: MoviesRemoteDataSource) :
    MoviesRepository {

    override suspend fun fetchPopularMovies(page: Int) =
        moviesRemoteDataSource.getPopularMovies(page)

    override suspend fun fetchMovieDetails(id: Int) =
        moviesRemoteDataSource.getMovieDetails(id)
}
