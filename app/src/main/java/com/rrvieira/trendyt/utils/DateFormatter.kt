package com.rrvieira.trendyt.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val defaultDateFormat = SimpleDateFormat("MMM dd, yyyy")
    private val numericDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val yearFormat = SimpleDateFormat("yyyy")

    fun formatDefaultDate(date: Date): String {
        return defaultDateFormat.format(date)
    }

    fun formatNumericDate(date: Date): String {
        return numericDateFormat.format(date)
    }

    fun formatYear(date: Date): String {
        return yearFormat.format(date)
    }
}
