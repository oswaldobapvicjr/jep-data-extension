package net.obvj.jep.util;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class DateUtilsTest
{
    // Test data
    private static final int YEAR = 2017;
    private static final int MONTH = Calendar.MARCH;
    private static final int DATE = 11;
    private static final int HOUR = 13;
    private static final int MINUTE = 15;
    private static final int SECOND = 0;
    private static final int MILLIS = 999;

    private static final String STR_ISO_PATTERN_YYYY_MM_DD_T_HH_MM_SS_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private static final String STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00 = "2017-03-11T10:15:00.999-03:00";
    private static final String STR_DATE_ISO_8601_2018_03_11_10_15_00_999_MINUS_03_00 = "2018-03-11T10:15:00.999-03:00";
    private static final String STR_DATE_RFC_822_2017_03_11_10_15_00_999_MINUS_3 = "2017-03-11T10:15:00.999-0300";

    private static final String STR_DATE_RFC_3339_MILLIS_2017_03_11_13_15_00_999_Z = "2017-03-11T13:15:00.999Z";
    private static final String STR_DATE_RFC_3339_NANOS_2017_03_11_13_15_00_999_Z = "2017-03-11T13:15:00.999876543Z";

    public static final Instant INSTANT_2017_03_11_13_15_00_999 = Instant
            .parse(STR_DATE_RFC_3339_MILLIS_2017_03_11_13_15_00_999_Z);

    private static final Date DATE_2017_03_11_13_15_00_999;

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
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test(expected = InvocationTargetException.class)
    public void testNoInstancesAllowed() throws Exception
    {
        try
        {
            Constructor<DateUtils> constructor = DateUtils.class.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));

            constructor.setAccessible(true);
            constructor.newInstance();
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(IllegalStateException.class, cause.getClass());
            assertEquals("Utility class", cause.getMessage());
            throw ite;
        }
    }

    /**
     * Tests that a valid string in ISO-8601, full, with time zone, is parsable
     */
    @Test
    public void testIsParsableISO8601FullWithTimeZone()
    {
        assertTrue(DateUtils.isParsable(STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00));
    }

    /**
     * Tests that a valid string in RFC-822, full, with time zone, is parsable
     */
    @Test
    public void testIsParsableRFC822FullWithTimeZone()
    {
        assertTrue(DateUtils.isParsable(STR_DATE_RFC_822_2017_03_11_10_15_00_999_MINUS_3));
    }

    /**
     * Tests that a valid string in RFC-3339 with milliseconds is parsable
     */
    @Test
    public void testIsParsableRFC3339WithMilliseconds()
    {
        assertTrue(DateUtils.isParsable(STR_DATE_RFC_3339_MILLIS_2017_03_11_13_15_00_999_Z));
    }

    /**
     * Tests that a valid string in RFC-3339 with nanoseconds is parsable
     */
    @Test
    public void testIsParsableRFC3339WithNanosecods()
    {
        assertTrue(DateUtils.isParsable(STR_DATE_RFC_3339_NANOS_2017_03_11_13_15_00_999_Z));
    }

    /**
     * Tests that a Date is parsable
     */
    @Test
    public void testIsParsableDate()
    {
        assertTrue(DateUtils.isParsable(new Date()));
    }

    /**
     * Tests that an Instant is parsable
     */
    @Test
    public void testIsParsableInstant()
    {
        assertTrue(DateUtils.isParsable(INSTANT_2017_03_11_13_15_00_999));
    }

    /**
     * Tests that a null object is not parsable. No exception should be thrown.
     */
    @Test
    public void testIsParsableNull()
    {
        assertFalse(DateUtils.isParsable(null));
    }

    /**
     * Tests that an empty string is not parsable. No exception should be thrown.
     */
    @Test
    public void testIsParsableEmptyString()
    {
        assertFalse(DateUtils.isParsable(""));
    }

    /**
     * Tests that an empty string is not parsable. No exception should be thrown.
     */
    @Test
    public void testIsParsableInvalidString()
    {
        assertFalse(DateUtils.isParsable("test"));
    }

    /**
     * Tests the successful parsing of a string in ISO-8601, full, with time zone. The pattern
     * provided as parameter.
     *
     * @throws ParseException
     */
    @Test
    public void testParseDateISO8601FullWithTimeZoneWithPatternParam() throws ParseException
    {
        Date date = DateUtils.parseDate(STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00,
                STR_ISO_PATTERN_YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        assertDate(date);
    }

    /**
     * Tests the successful parsing of a string in ISO-8601, full, with time zone. No pattern
     * parameter provided.
     *
     * @throws ParseException
     */
    @Test
    public void testParseDateISO8601FullWithTimeZoneNoPatternParam() throws ParseException
    {
        assertDate(DateUtils.parseDate(STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00));
    }

    /**
     * Tests the successful parsing of a string in RFC 822. No pattern parameter provided.
     *
     * @throws ParseException
     */
    @Test
    public void testParseDateRFC822NoPatternParam() throws ParseException
    {
        assertDate(DateUtils.parseDate(STR_DATE_RFC_822_2017_03_11_10_15_00_999_MINUS_3));
    }

    /**
     * Tests the successful parsing of a string in RFC 3339 with milliseconds precision. No
     * pattern parameter provided.
     *
     * @throws ParseException
     */
    @Test
    public void testParseDateRFC3339MillisecondsNoPatternParam() throws ParseException
    {
        assertDate(DateUtils.parseDate(STR_DATE_RFC_3339_MILLIS_2017_03_11_13_15_00_999_Z));
    }

    /**
     * Tests the successful parsing of a string in RFC 3339 with nanoseconds precision. No
     * pattern parameter provided.
     *
     * @throws ParseException
     */
    @Test
    public void testParseDateRFC3339NanosecondsNoPatternParam() throws ParseException
    {
        assertDate(DateUtils.parseDate(STR_DATE_RFC_3339_NANOS_2017_03_11_13_15_00_999_Z));
    }

    /**
     * Tests the successful date formatting to ISO 8601, full, with time zone
     */
    @Test
    public void testFormatDateISO8601FullWithTimeZone() throws ParseException
    {
        Date date = DateUtils.parseDate(STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00, STR_ISO_PATTERN_YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        String string = DateUtils.formatDate(date, STR_ISO_PATTERN_YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        assertEquals(STR_DATE_RFC_3339_MILLIS_2017_03_11_13_15_00_999_Z, string);
    }

    /**
     * Tests the parsing of an Instant to Date
     */
    @Test
    public void testInstantParsingToDate() throws ParseException
    {
        Date date = DateUtils.parseDate(INSTANT_2017_03_11_13_15_00_999);
        assertEquals(DATE_2017_03_11_13_15_00_999, date);
    }

    /**
     * Tests the containsParsableDatesRfc3339() method for a list containing two valid strings
     * in RFC-3339 format
     */
    @Test
    public void testContainsParsableDatesForListWithRFC3339Strings()
    {
        List<String> list = Arrays.asList(STR_DATE_RFC_3339_MILLIS_2017_03_11_13_15_00_999_Z,
                STR_DATE_RFC_3339_NANOS_2017_03_11_13_15_00_999_Z);
        assertTrue(DateUtils.containsParsableDatesRfc3339(list));
    }

    /**
     * Tests the containsParsableDatesIso8601() method for a list containing two valid strings
     * in ISO-8601 format
     */
    @Test
    public void testContainsParsableDatesForListWithISO8601Strings()
    {
        List<String> list = Arrays.asList(STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00,
                STR_DATE_ISO_8601_2018_03_11_10_15_00_999_MINUS_03_00);
        assertTrue(DateUtils.containsParsableDatesIso8601(list));
    }

    /**
     * Tests the containsParsableDates() method for a list containing valid strings in
     * different formats (ISO-8601, RFC-822 and RFC-3339)
     */
    @Test
    public void testContainsParsableDatesForListWithDifferentStringFormats()
    {
        List<String> list = Arrays.asList(STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00,
                STR_DATE_RFC_822_2017_03_11_10_15_00_999_MINUS_3, STR_DATE_RFC_3339_NANOS_2017_03_11_13_15_00_999_Z);
        assertTrue(DateUtils.containsParsableDates(list));
    }

    /**
     * Tests the containsParsableDates() method for a list containing valid strings in
     * different formats (ISO-8601, RFC-822 and RFC-3339), an Instant, and a Date.
     */
    @Test
    public void testContainsParsableDatesForListWithDifferentSupportedFormats()
    {
        List<Object> list = Arrays.asList(STR_DATE_ISO_8601_2017_03_11_10_15_00_999_MINUS_03_00,
                STR_DATE_RFC_822_2017_03_11_10_15_00_999_MINUS_3, STR_DATE_RFC_3339_NANOS_2017_03_11_13_15_00_999_Z,
                INSTANT_2017_03_11_13_15_00_999, new Date());
        assertTrue(DateUtils.containsParsableDates(list));
    }

	/**
     * Tests the number of days between two Date objects, being date 1 lower than date 2
     *
     * @throws ParseException if the test date cannot be parsed
     */
    @Test
    public void testNumberOfDaysBetweenTwoDatesBeingDate1LowerThanDate2() throws ParseException
    {
        Date date2018_03_11 = DateUtils.parseDate(STR_DATE_ISO_8601_2018_03_11_10_15_00_999_MINUS_03_00, STR_ISO_PATTERN_YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        assertEquals(365, DateUtils.daysBetween(DATE_2017_03_11_13_15_00_999, date2018_03_11));
    }

	/**
     * Tests the number of days between two Date objects, being date 1 greater than date 2
     *
     * @throws ParseException if the test date cannot be parsed
     */
    @Test
    public void testNumberOfDaysBetweenTwoDatesBeingDate1GreaterThanDate2() throws ParseException
    {
        Date date2018_03_11 = DateUtils.parseDate(STR_DATE_ISO_8601_2018_03_11_10_15_00_999_MINUS_03_00, STR_ISO_PATTERN_YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        assertEquals(365, DateUtils.daysBetween(date2018_03_11, DATE_2017_03_11_13_15_00_999));
    }

	/**
	 * Tests the number of days between two equal Date objects
	 */
    @Test
    public void testNumberOfDaysBetweenTwoEqualDates()
    {
        assertEquals(0, DateUtils.daysBetween(DATE_2017_03_11_13_15_00_999, DATE_2017_03_11_13_15_00_999));
    }

    /**
     * Tests the number of days between, with first date null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNumberOfDaysBetweenWithFirstDateNull()
    {
        DateUtils.daysBetween(null, DATE_2017_03_11_13_15_00_999);
    }

    /**
     * Tests the number of days between, with second date null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNumberOfDaysBetweenWithSecondDateNull()
    {
        DateUtils.daysBetween(DATE_2017_03_11_13_15_00_999, null);
    }

    /**
     * Tests isLeapYear with different input years as integers
     */
    @Test
    public void testIsLeapYearForSeveralYearsAsIntegers()
    {
        assertTrue("1988 should be a leap year", DateUtils.isLeapYear(1988));
        assertTrue("2000 should be a leap year", DateUtils.isLeapYear(2000));
        assertTrue("2016 should be a leap year", DateUtils.isLeapYear(2016));
        assertFalse("1900 should not be a leap year", DateUtils.isLeapYear(1900));
        assertFalse("2018 should not be a leap year", DateUtils.isLeapYear(2018));
    }

    /**
     * Tests isLeapYear with different input as dates
     *
     * @throws ParseException if the test date cannot be parsed
     */
    @Test
    public void testIsLeapYearForSeveralDates() throws ParseException
    {
        assertFalse("2017 should not be a leap year", DateUtils.isLeapYear(DATE_2017_03_11_13_15_00_999));
        assertTrue("2020 should be a leap year", DateUtils.isLeapYear(DateUtils.parseDate("2020", "yyyy")));
    }

    /**
     * Tests isLeapYear with a null date
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsLeapYearForANullDate() throws ParseException
    {
        DateUtils.isLeapYear(null);
    }

}
