package com.rrvieira.trendyt.model

import org.junit.Assert.*

import org.junit.Test

class ApiConfigurationTest {

    private val configuration = ApiConfiguration(
        imagesBaseUrl = BASE_URL,
        posterSize = POSTER_SIZE,
        backdropSize = BACKDROP_SIZE
    )

    @Test
    fun urlForPoster() {
        val posterFile = "/poster.jpg"
        assertEquals("$BASE_URL$POSTER_SIZE$posterFile", configuration.urlForPoster(posterFile))
        assertEquals("", configuration.urlForPoster(null))
    }

    @Test
    fun urlForBackdrop() {
        val backdropFile = "/backdrop.jpg"
        assertEquals("$BASE_URL$BACKDROP_SIZE$backdropFile", configuration.urlForBackdrop(backdropFile))
        assertEquals("", configuration.urlForBackdrop(null))
    }

    private companion object {
        const val BASE_URL = "http://image-base-url.com/"
        const val POSTER_SIZE = "poster-size"
        const val BACKDROP_SIZE = "backdrop-size"
    }
}
