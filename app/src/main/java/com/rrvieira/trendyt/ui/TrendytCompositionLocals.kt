package com.rrvieira.trendyt.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.rrvieira.trendyt.utils.DateFormatter
import com.rrvieira.trendyt.utils.TimeFormatter

val LocalDateFormatter = staticCompositionLocalOf<DateFormatter> {
    error("DateFormatter not provided")
}

val LocalTimeFormatter = staticCompositionLocalOf<TimeFormatter> {
    error("TimeFormatter not provided")
}
