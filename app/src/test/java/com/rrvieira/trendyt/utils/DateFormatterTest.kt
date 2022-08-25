package com.rrvieira.trendyt.utils

import org.junit.Assert.*

import org.junit.Test
import java.util.*
import com.rrvieira.trendyt.utils.DateFormatter.formatDefaultDate

class DateFormatterTest {

    @Test
    fun formatDefaultDate() {
        assertEquals(
            "Mar 01, 2022",
            formatDefaultDate(GregorianCalendar(2022, Calendar.MARCH, 1).time)
        )
        assertEquals(
            "Apr 15, 2025",
            formatDefaultDate(GregorianCalendar(2025, Calendar.APRIL, 15).time)
        )
        assertEquals(
            "Oct 29, 2199",
            formatDefaultDate(GregorianCalendar(2199, Calendar.OCTOBER, 29).time)
        )
        assertEquals(
            "Dec 31, 1949",
            formatDefaultDate(GregorianCalendar(1949, Calendar.DECEMBER, 31).time)
        )
        assertEquals(
            "Jan 08, 1934",
            formatDefaultDate(GregorianCalendar(1934, Calendar.JANUARY, 8).time)
        )
    }
}
