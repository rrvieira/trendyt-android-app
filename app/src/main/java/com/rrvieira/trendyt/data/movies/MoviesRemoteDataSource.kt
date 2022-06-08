package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.api.MoviesApiClient
import com.rrvieira.trendyt.di.IODispatcher
import com.rrvieira.trendyt.model.Movie
import com.rrvieira.trendyt.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MoviesRemoteDataSource @Inject constructor(
    private val moviesApiClient: MoviesApiClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getPopularMovies(page: Int) = withContext(ioDispatcher) {
        val movies = moviesApiClient.getPopularMovies(page).getOrElse { error ->
            return@withContext Result.failure(error)
        }.popularMovies.map { movie ->
            Movie(
                movie.id,
                movie.title,
                "https://image.tmdb.org/t/p/w780${movie.backdropPath}"
            )
        }

        Result.success(movies)
    }

    suspend fun getMovieDetails(id: Int) = withContext(ioDispatcher) {
        val movieResponse = moviesApiClient.getMovieDetails(id).getOrElse { error ->
            return@withContext Result.failure(error)
        }

        Result.success(MovieDetails(movieResponse.id, movieResponse.title))
    }
}
