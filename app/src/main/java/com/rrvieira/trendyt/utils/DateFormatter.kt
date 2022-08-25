package com.rrvieira.trendyt.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val defaultDateFormat = SimpleDateFormat("MMM dd, yyyy")

    fun formatDefaultDate(date: Date): String {
        return defaultDateFormat.format(date)
    }
}
