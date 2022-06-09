package com.rrvieira.trendyt.model

import java.util.*

data class MovieDetails(
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val releaseDate: Date,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<String>,
    val backdropUrl: String,
    val posterUrl: String
)
