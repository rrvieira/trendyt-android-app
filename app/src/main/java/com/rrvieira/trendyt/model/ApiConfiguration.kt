package com.rrvieira.trendyt.model

data class ApiConfiguration(
    val imagesBaseUrl: String,
    val posterSize: String,
    val backdropSize: String
)

fun ApiConfiguration.urlForPoster(poster: String) = "$imagesBaseUrl$posterSize$poster"

fun ApiConfiguration.urlForBackdrop(backdrop: String) = "$imagesBaseUrl$backdropSize$backdrop"
