package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.TimeZone;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

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
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.MARCH, 11, 13, 35, 00);
        calendar.set(Calendar.MILLISECOND, 123);
        DATE = calendar.getTime();
    }

    private static final String ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ssZZ";
    private static final String ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

    // Expected results
    private static final String EXPECTED_STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE = "2017-03-11T10:35:00-03:00";
    private static final String EXPECTED_STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE = "2017-03-11T10:35:00.123-03:00";

    private static DateToString function = new DateToString();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests date formatting with null date
     */
    @Test
    public void testFormatDateNullDate() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(null, ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
    }

    /**
     * Tests date formatting with null format
     */
    @Test
    public void testFormatDateNullFormat() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(DATE, null);
        function.run(parameters);
    }

    /**
     * Tests date formatting with ISO extended date & time format with time zone
     */
    @Test
    public void testFormatDateIsoExtendedDateTimeFormatWithTimeZone() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(DATE, ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE, parameters.pop());
    }

    /**
     * Tests date formatting with ISO full date & time format with time zone
     */
    @Test
    public void testFormatDateIsoFullDateTimeFormatWithTimeZone() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(DATE, ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE, parameters.pop());
    }

}
