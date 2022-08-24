package com.rrvieira.trendyt.data.configuration

import com.rrvieira.trendyt.model.ApiConfiguration

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<ApiConfiguration>
}
