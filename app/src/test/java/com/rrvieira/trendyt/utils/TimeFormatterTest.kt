package com.rrvieira.trendyt.utils

import org.junit.Assert
import org.junit.Test

class TimeFormatterTest {

    @Test
    fun formatMinutesInHours() {
        Assert.assertEquals(
            "0h 0m",
            TimeFormatter.formatMinutesInHours(0)
        )
        Assert.assertEquals(
            "0h 35m",
            TimeFormatter.formatMinutesInHours(35)
        )
        Assert.assertEquals(
            "1h 35m",
            TimeFormatter.formatMinutesInHours(95)
        )
        Assert.assertEquals(
            "2h 20m",
            TimeFormatter.formatMinutesInHours(140)
        )
        Assert.assertEquals(
            "3h 59m",
            TimeFormatter.formatMinutesInHours(239)
        )
    }
}
