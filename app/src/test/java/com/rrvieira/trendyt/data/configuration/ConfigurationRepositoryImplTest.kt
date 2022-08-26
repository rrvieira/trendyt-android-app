package com.rrvieira.trendyt.data.configuration

import com.rrvieira.trendyt.api.responses.ConfigurationResponse
import com.rrvieira.trendyt.api.responses.Images
import com.rrvieira.trendyt.model.ApiConfiguration
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConfigurationRepositoryImplTest {

    @Test
    fun fetchConfiguration() = runTest {
        val apiConfigurationResponse = ConfigurationResponse(
            changeKeys = emptyList(),
            images = Images(
                baseUrl = "http://image.org/",
                secureBaseUrl = "https://image.org/",
                backdropSizes = listOf(
                    "w300",
                    "w780",
                    "w1280",
                    "original"
                ),
                posterSizes = listOf(
                    "w342",
                    "w500",
                    "w780",
                    "original"
                ),
                logoSizes = emptyList(),
                profileSizes = emptyList(),
                stillSizes = emptyList()
            )
        )

        val expected = Result.success(
            ApiConfiguration(
                imagesBaseUrl = apiConfigurationResponse.images.secureBaseUrl,
                posterSize = apiConfigurationResponse.images.posterSizes[2],
                backdropSize = apiConfigurationResponse.images.backdropSizes[1]
            )
        )

        val mockConfigurationRemoteDataSource = mockk<ConfigurationRemoteDataSource> {
            coEvery { getApiConfiguration() } returns Result.success(
                apiConfigurationResponse
            )
        }

        val configurationRepository = ConfigurationRepositoryImpl(mockConfigurationRemoteDataSource)

        assertEquals(expected, configurationRepository.fetchConfiguration())
    }
}
