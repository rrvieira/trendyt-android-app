package com.rrvieira.trendyt.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.rrvieira.trendyt.utils.DateFormatter

val LocalDateFormatter = staticCompositionLocalOf<DateFormatter> {
    error("DateFormatter not provided")
}
