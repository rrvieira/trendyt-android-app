package com.rrvieira.trendyt.data.configuration

import com.rrvieira.trendyt.model.ApiConfiguration

class ConfigurationRepositoryImpl(
    private val configurationRemoteDataSource: ConfigurationRemoteDataSource
) : ConfigurationRepository {
    private var apiConfiguration: ApiConfiguration? = null

    override suspend fun fetchConfiguration(): Result<ApiConfiguration> {
        apiConfiguration?.let { apiConfiguration ->
            return Result.success(apiConfiguration)
        }

        configurationRemoteDataSource.getApiConfiguration().getOrElse { error ->
            return Result.failure(error)
        }.let { configurationResponse ->
            ApiConfiguration(
                imagesBaseUrl = configurationResponse.images.secureBaseUrl,
                posterSize = configurationResponse.images.posterSizes.reversed()[POSTER_SIZE_REVERSED_INDEX],
                backdropSize = configurationResponse.images.backdropSizes.reversed()[BACKDROP_SIZE_REVERSED_INDEX]
            )
        }.also {
            apiConfiguration = it
            return Result.success(it)
        }
    }

    private companion object {
        const val POSTER_SIZE_REVERSED_INDEX = 1
        const val BACKDROP_SIZE_REVERSED_INDEX = 2
    }
}
