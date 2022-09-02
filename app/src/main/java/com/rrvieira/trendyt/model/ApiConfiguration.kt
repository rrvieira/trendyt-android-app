package com.rrvieira.trendyt.model

data class ApiConfiguration(
    val imagesBaseUrl: String,
    val posterSize: String,
    val backdropSize: String
) {

    fun urlForPoster(poster: String?) = poster?.let { "$imagesBaseUrl$posterSize$poster" } ?: ""

    fun urlForBackdrop(backdrop: String?) =
        backdrop?.let { "$imagesBaseUrl$backdropSize$backdrop" } ?: ""
}
