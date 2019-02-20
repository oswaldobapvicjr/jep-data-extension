package net.obvj.jep.util;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    private static final String STR_DATE_2017_03_11_10_15_00_999_MINUS_03_00 = "2017-03-11T10:15:00.999-03:00";
    private static final String STR_DATE_2018_03_11_10_15_00_999_MINUS_03_00 = "2018-03-11T10:15:00.999-03:00";
    private static final String YYYY_MM_DD_T_HH_MM_SS_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String STR_DATE_2017_03_11_13_15_00_999_Z = "2017-03-11T13:15:00.999Z";

    public static final Instant INSTANT_DATE_2017_03_11_13_15_00_999 = Instant
            .parse(STR_DATE_2017_03_11_13_15_00_999_Z);

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
        Date date = DateUtils.parseDate(INSTANT_DATE_2017_03_11_13_15_00_999);
        assertEquals(DATE_2017_03_11_13_15_00_999, date);
    }

	/**
	 * Tests the number of days between two Date objects, being date 1 lower than date 2
	 *
	 * @throws ParseException
	 */
    @Test
    public void testNumberOfDaysBetweenTwoDatesBeingDate1LowerThanDate2() throws ParseException
    {
        Date date2018_03_11 = DateUtils.parseDate(STR_DATE_2018_03_11_10_15_00_999_MINUS_03_00, YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
        assertEquals(365, DateUtils.daysBetween(DATE_2017_03_11_13_15_00_999, date2018_03_11));
    }

	/**
	 * Tests the number of days between two Date objects, being date 1 greater than date 2
	 *
	 * @throws ParseException
	 */
    @Test
    public void testNumberOfDaysBetweenTwoDatesBeingDate1GreaterThanDate2() throws ParseException
    {
        Date date2018_03_11 = DateUtils.parseDate(STR_DATE_2018_03_11_10_15_00_999_MINUS_03_00, YYYY_MM_DD_T_HH_MM_SS_SSSXXX);
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
    public void testIsLeapYearForSeveralYearsAsIntegers() throws ParseException
    {
        assertTrue("1988 should be a leap year", DateUtils.isLeapYear(1988));
        assertTrue("2000 should be a leap year", DateUtils.isLeapYear(2000));
        assertTrue("2016 should be a leap year", DateUtils.isLeapYear(2016));
        assertFalse("1900 should not be a leap year", DateUtils.isLeapYear(1900));
        assertFalse("2018 should not be a leap year", DateUtils.isLeapYear(2018));
    }

    /**
     * Tests isLeapYear with different input as dates
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
