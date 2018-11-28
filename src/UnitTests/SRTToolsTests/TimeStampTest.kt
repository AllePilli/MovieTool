package UnitTests.SRTToolsTests

import SRTTools.TimeStamp
import kotlin.test.Asserter
import kotlin.test.assertEquals

internal class TimeStampTest {

    @org.junit.jupiter.api.Test
    fun minus() {
        var first = TimeStamp(23, 59, 59, 999)
        var second = TimeStamp(23, 59, 59, 999)
        var expected = TimeStamp(0, 0, 0, 0)

        assertEquals(expected, first - second)

        first = TimeStamp(5, 30, 0, 0)
        second = TimeStamp(2, 0, 0, 0)
        expected = TimeStamp(3, 30, 0, 0)

        assertEquals(expected, first - second)

        first = TimeStamp(5, 30, 0, 0)
        second = TimeStamp(2, 40, 20, 0)
        expected = TimeStamp(2, 49, 40, 0)

        assertEquals(expected, first - second)
    }
}