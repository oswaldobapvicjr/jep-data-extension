package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link StringToDate} function
 *
 * @author oswaldo.bapvic.jr
 */
public class StringToDateTest
{
    // Test data
    private static final String STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE = "2017-03-11T10:35:00-03:00";
    private static final String STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE = "2017-03-11T10:35:00.123-03:00";

    private static final String ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ssZZ";
    private static final String ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

    private static final Date EXPECTED_DATE_SECONDS;
    private static final Date EXPECTED_DATE_MILLISECONDS;

    static
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2017, Calendar.MARCH, 11, 13, 35, 00);

        calendar.set(Calendar.MILLISECOND, 0);
        EXPECTED_DATE_SECONDS = calendar.getTime();

        calendar.set(Calendar.MILLISECOND, 123);
        EXPECTED_DATE_MILLISECONDS = calendar.getTime();
    }

    private static StringToDate function = new StringToDate();

    /**
     * Tests date parsing with null date
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseDateNullDate() throws ParseException
    {
        String input = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(input);
        function.run(parameters);
    }

    /**
     * Tests date parsing with empty date
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseDateEmptyDate() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(StringUtils.EMPTY);
        function.run(parameters);
    }

    /**
     * Tests date parsing with null format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseDateNullFormat() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE,
                null);
        function.run(parameters);
    }

    /**
     * Tests date parsing with empty format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseDateEmptyFormat() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE,
                StringUtils.EMPTY);
        function.run(parameters);
    }

    /**
     * Tests date parsing with an invalid format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseDateInvalidFormat() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE,
                "Y");
        function.run(parameters);
    }

    /**
     * Tests date parsing with ISO extended date & time format with time zone and a valid
     * pattern parameter
     */
    @Test
    public void testParseDateIsoExtendedDateTimeFormatWithTimeZoneWithParam() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE,
                ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_DATE_SECONDS, parameters.pop());
    }

    /**
     * Tests date parsing with ISO extended date & time format with time zone, no pattern
     * parameter
     */
    @Test
    public void testParseDateIsoExtendedDateTimeFormatWithTimeZoneNoParam() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_DATE_SECONDS, parameters.pop());
    }

    /**
     * Tests date parsing with ISO full date & time format with time zone and valid pattern
     * parameter
     */
    @Test
    public void testParseDateIsoFullDateTimeFormatWithTimeZoneAndParam() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE,
                ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_DATE_MILLISECONDS, parameters.pop());
    }

    /**
     * Tests date parsing with ISO full date & time format with time zone and no pattern
     * parameter
     */
    @Test
    public void testParseDateIsoFullDateTimeFormatWithTimeZoneAndNoParam() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_DATE_MILLISECONDS, parameters.pop());
    }

    /**
     * Tests date parsing with ISO full date & time format with time zone and valid pattern
     * parameter
     */
    @Test
    public void testParseDateWithMoreThanOnePatternParam() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE,
                ISO_EXTENDED_DATE_TIME_FORMAT_WITH_TIME_ZONE, ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE);
        function.run(parameters);
        assertEquals(EXPECTED_DATE_MILLISECONDS, parameters.pop());
    }

}
