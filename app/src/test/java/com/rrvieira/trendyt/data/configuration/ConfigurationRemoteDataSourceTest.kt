package com.rrvieira.trendyt.data.configuration

import com.rrvieira.trendyt.api.MoviesApiClient
import com.rrvieira.trendyt.api.responses.ConfigurationResponse
import com.rrvieira.trendyt.api.responses.Images
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConfigurationRemoteDataSourceTest {

    @Test
    fun getApiConfiguration() = runTest {
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
        val expected = Result.success(apiConfigurationResponse)

        val mockMoviesApiClient = mockk<MoviesApiClient> {
            coEvery { getApiConfiguration() } returns Result.success(
                apiConfigurationResponse
            )
        }

        val configurationRemoteDataSource = ConfigurationRemoteDataSource(
            mockMoviesApiClient,
            UnconfinedTestDispatcher(testScheduler)
        )

        assertEquals(expected, configurationRemoteDataSource.getApiConfiguration())
    }
}
