package com.rrvieira.trendyt.utils

import org.junit.Assert.*

import org.junit.Test
import java.util.*
import com.rrvieira.trendyt.utils.DateFormatter.formatDefaultDate
import com.rrvieira.trendyt.utils.DateFormatter.formatNumericDate
import com.rrvieira.trendyt.utils.DateFormatter.formatYear

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

    @Test
    fun formatNumericDate() {
        assertEquals(
            "01/03/2022",
            formatNumericDate(GregorianCalendar(2022, Calendar.MARCH, 1).time)
        )
        assertEquals(
            "15/04/2025",
            formatNumericDate(GregorianCalendar(2025, Calendar.APRIL, 15).time)
        )
        assertEquals(
            "29/10/2199",
            formatNumericDate(GregorianCalendar(2199, Calendar.OCTOBER, 29).time)
        )
        assertEquals(
            "31/12/1949",
            formatNumericDate(GregorianCalendar(1949, Calendar.DECEMBER, 31).time)
        )
        assertEquals(
            "08/01/1934",
            formatNumericDate(GregorianCalendar(1934, Calendar.JANUARY, 8).time)
        )
    }

    @Test
    fun formatYear() {
        assertEquals(
            "2022",
            formatYear(GregorianCalendar(2022, Calendar.MARCH, 1).time)
        )
        assertEquals(
            "2025",
            formatYear(GregorianCalendar(2025, Calendar.APRIL, 15).time)
        )
        assertEquals(
            "2199",
            formatYear(GregorianCalendar(2199, Calendar.OCTOBER, 29).time)
        )
        assertEquals(
            "1949",
            formatYear(GregorianCalendar(1949, Calendar.DECEMBER, 31).time)
        )
        assertEquals(
            "1934",
            formatYear(GregorianCalendar(1934, Calendar.JANUARY, 8).time)
        )
    }
}
