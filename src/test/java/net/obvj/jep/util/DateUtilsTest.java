package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class DateUtilsTest
{
    // Test data
    public static final int YEAR = 2017;
    public static final int MONTH = Calendar.MARCH;
    public static final int DATE = 11;
    public static final int HOUR = 13;
    public static final int MINUTE = 15;
    public static final int SECOND = 0;
    public static final int MILLIS = 999;

    public static final String STR_DATE_2017_03_11_10_15_00_999_MINUS_03_00 = "2017-03-11T10:15:00.999-03:00";
    public static final String YYYY_MM_DD_T_HH_MM_SS_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String STR_DATE_2017_03_11_13_15_00_999_Z = "2017-03-11T13:15:00.999Z";

    public static final Instant INSTANT_DATE_2017_03_11_13_15_00_999 = Instant
            .parse(STR_DATE_2017_03_11_13_15_00_999_Z);

    public static final Date DATE_2017_03_11_13_15_00_999;

    static
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, MONTH, DATE, HOUR, MINUTE, SECOND);
        calendar.set(Calendar.MILLISECOND, MILLIS);
        DATE_2017_03_11_13_15_00_999 = calendar.getTime();
    }

    private void assertDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        assertEquals(YEAR, calendar.get(Calendar.YEAR));
        assertEquals(MONTH, calendar.get(Calendar.MONTH));
        assertEquals(DATE, calendar.get(Calendar.DATE));
        assertEquals(HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(MINUTE, calendar.get(Calendar.MINUTE));
        assertEquals(SECOND, calendar.get(Calendar.SECOND));
        assertEquals(MILLIS, calendar.get(Calendar.MILLISECOND));
    }

    /**
     * Tests the successful parsing of a string in ISO 8601, full, with time zone.
     *
     * @throws ParseException
     */
    @Test
    public void testParseDateISO8601FullWithTimeZone() throws ParseException
    {
        Date date = DateUtils.parseDate(STR_DATE_2017_03_11_10_15_00_999_MINUS_03_00, YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        assertDate(date);
    }

    /**
     * Tests the successful date formatting to ISO 8601, full, with time zone
     */
    @Test
    public void testFormatDateISO8601FullWithTimeZone() throws ParseException
    {
        Date date = DateUtils.parseDate(STR_DATE_2017_03_11_10_15_00_999_MINUS_03_00, YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        String string = DateUtils.formatDate(date, YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        assertEquals(STR_DATE_2017_03_11_13_15_00_999_Z, string);
    }

    /**
     * Tests the parsing of an Instant to Date
     */
    @Test
    public void testInstantParsingToDate() throws ParseException
    {
        Date string = DateUtils.parseDate(INSTANT_DATE_2017_03_11_13_15_00_999);
        assertEquals(DATE_2017_03_11_13_15_00_999, string);
    }
}
