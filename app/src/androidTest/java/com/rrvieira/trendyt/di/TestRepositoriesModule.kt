package com.rrvieira.trendyt.di

import com.rrvieira.trendyt.data.movies.MoviesRepository
import com.rrvieira.trendyt.data.movies.TestMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoriesModule::class]
)
class TestRepositoriesModule {

    @Provides
    fun provideMoviesRepository(): MoviesRepository = TestMoviesRepository()
}
