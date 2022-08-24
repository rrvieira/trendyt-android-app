package com.rrvieira.trendyt.di

import com.rrvieira.trendyt.data.configuration.ConfigurationRemoteDataSource
import com.rrvieira.trendyt.data.configuration.ConfigurationRepository
import com.rrvieira.trendyt.data.configuration.ConfigurationRepositoryImpl
import com.rrvieira.trendyt.data.movies.MoviesRemoteDataSource
import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.data.movies.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun provideMoviesRepository(
        moviesRemoteDataSource: MoviesRemoteDataSource,
        configurationRepository: ConfigurationRepository
    ): MoviesRepository =
        MoviesRepositoryImpl(moviesRemoteDataSource, configurationRepository)

    @Provides
    fun provideConfigurationRepository(
        configurationRemoteDataSource: ConfigurationRemoteDataSource
    ): ConfigurationRepository =
        ConfigurationRepositoryImpl(configurationRemoteDataSource)
}
