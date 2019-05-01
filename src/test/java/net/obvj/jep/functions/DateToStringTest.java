package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.TimeZone;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link DateToString} function
 *
 * @author oswaldo.bapvic.jr
 */
public class DateToStringTest
{
    // Test date
    private static final Date DATE;

    static
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MARCH, 11, 13, 35, 0);
        calendar.set(Calendar.MILLISECOND, 123);
        DATE = calendar.getTime();
    }

    private static final String ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // Expected results
    private static final String EXPECTED_STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE = "2017-03-11T13:35:00Z";
    private static final String EXPECTED_STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE = "2017-03-11T13:35:00.123Z";

    private static DateToString function = new DateToString();

    /**
     * Tests date formatting with null date
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatDateNullDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
    }

    /**
     * Tests date formatting with null format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatDateNullFormat() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DATE, null);
        function.run(parameters);
    }

    /**
     * Tests date formatting with ISO extended date & time format with time zone
     */
    @Test
    public void testFormatDateIsoExtendedDateTimeFormatWithTimeZone() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DATE, ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE, parameters.pop());
    }

    /**
     * Tests date formatting with ISO full date & time format with time zone
     */
    @Test
    public void testFormatDateIsoFullDateTimeFormatWithTimeZone() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DATE, ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE, parameters.pop());
    }

}
