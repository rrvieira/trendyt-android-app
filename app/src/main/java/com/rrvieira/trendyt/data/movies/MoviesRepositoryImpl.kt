package com.rrvieira.trendyt.data.movies

import com.rrvieira.trendyt.model.MovieDetails
import com.rrvieira.trendyt.model.MovieSummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher
) :
    MoviesRepository {

    override suspend fun fetchPopularMovies(page: Int) = withContext(defaultDispatcher) {
        Result.success(
            moviesRemoteDataSource.getPopularMovies(page).getOrElse { error ->
                return@withContext Result.failure(error)
            }.popularMovies.map { movie ->
                MovieSummary(
                    id = movie.id,
                    title = movie.title,
                    releaseDate = movie.releaseDate,
                    imageUrl = "https://image.tmdb.org/t/p/w780${movie.backdropPath}"
                )
            }
        )
    }

    override suspend fun fetchMovieDetails(id: Int) = withContext(defaultDispatcher) {
        Result.success(
            moviesRemoteDataSource.getMovieDetails(id).getOrElse { error ->
                return@withContext Result.failure(error)
            }.let { details ->
                MovieDetails(
                    id = details.id,
                    title = details.title,
                    tagline = details.tagline,
                    overview = details.overview,
                    releaseDate = details.releaseDate,
                    popularity = details.popularity,
                    voteAverage = details.voteAverage,
                    voteCount = details.voteCount,
                    genres = details.genres.map { genre ->
                        genre.name
                    },
                    backdropUrl = "https://image.tmdb.org/t/p/w780${details.backdropPath}",
                    posterUrl = "https://image.tmdb.org/t/p/w780${details.posterPath}",
                )
            }
        )
    }
}
