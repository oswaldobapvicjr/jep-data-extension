package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Stack;
import java.util.TimeZone;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.DateFieldGetter.DateField;
import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.DateUtils;

/**
 * Unit tests for the {@link DateFieldGetter} function
 *
 * @author oswaldo.bapvic.jr
 */
public class DateFieldGetterTest
{
    private static final String STR_2019_04_08T14_45_00_123Z = "2019-04-08T14:45:00.123Z";

    private static DateFieldGetter yearGetter = new DateFieldGetter(DateField.YEAR);
    private static DateFieldGetter monthGetter = new DateFieldGetter(DateField.MONTH);
    private static DateFieldGetter isoWeekNumberGetter = new DateFieldGetter(DateField.ISO_WEEK_NUMBER);
    private static DateFieldGetter dayGetter = new DateFieldGetter(DateField.DAY);
    private static DateFieldGetter hourGetter = new DateFieldGetter(DateField.HOUR);
    private static DateFieldGetter minuteGetter = new DateFieldGetter(DateField.MINUTE);
    private static DateFieldGetter secondGetter = new DateFieldGetter(DateField.SECOND);
    private static DateFieldGetter millisecondGetter = new DateFieldGetter(DateField.MILLISECOND);

    static
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Tests the ISO week number retrieval for a valid date
     */
    @Test
    public void testIsoWeekNumberFromValidDate() throws ParseException, java.text.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DateUtils.parseDate("20190406", "yyyyMMdd"));
        isoWeekNumberGetter.run(parameters);
        assertEquals(14, parameters.pop());
    }

    /**
     * Tests the ISO week number retrieval for a valid date as string
     */
    @Test
    public void testIsoWeekNumberFromValidDateAsString() throws ParseException, java.text.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        isoWeekNumberGetter.run(parameters);
        assertEquals(15, parameters.pop());
    }

    /**
     * Tests the ISO week number retrieval for a null date
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsoWeekNumberFromNullDate() throws ParseException, java.text.ParseException
    {
        Date date = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(date);
        isoWeekNumberGetter.run(parameters);
    }

    /**
     * Tests the year from a date
     */
    @Test
    public void testYearFromDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        yearGetter.run(parameters);
        assertEquals(2019, parameters.pop());
    }

    /**
     * Tests the month from a date
     */
    @Test
    public void testMonthFromDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        monthGetter.run(parameters);
        assertEquals(4, parameters.pop());
    }

    /**
     * Tests the day from a date
     */
    @Test
    public void testDayFromDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        dayGetter.run(parameters);
        assertEquals(8, parameters.pop());
    }

    /**
     * Tests the hour from a date
     */
    @Test
    public void testHourFromDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        hourGetter.run(parameters);
        assertEquals(14, parameters.pop());
    }

    /**
     * Tests the minute from a date
     */
    @Test
    public void testMinuteFromDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        minuteGetter.run(parameters);
        assertEquals(45, parameters.pop());
    }

    /**
     * Tests the second from a date
     */
    @Test
    public void testSecondFromDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        secondGetter.run(parameters);
        assertEquals(0, parameters.pop());
    }

    /**
     * Tests the millisecond from a date
     */
    @Test
    public void testMillisecondFromDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_04_08T14_45_00_123Z);
        millisecondGetter.run(parameters);
        assertEquals(123, parameters.pop());
    }
}
