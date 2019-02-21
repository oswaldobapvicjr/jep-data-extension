package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.DateUtils;

/**
 * Unit tests for the {@link IsLeapYear} function
 *
 * @author oswaldo.bapvic.jr
 */
public class IsLeapYearTest
{
    // Test data
    private static final String STR_2019_01_19_RFC_3339 = "2019-01-19T22:40:38.678912543Z";
    private static final String STR_2020_02_21_RFC_3339 = "2020-02-20T22:41:39.123Z";
    private static Date DATE_2019_01_19;
    private static Date DATE_2020_02_20;

    // Expected results
    private static final double FALSE = 0d;
    private static final double TRUE = 1d;

    // Test subject
    private static IsLeapYear function = new IsLeapYear();

    // Test messages
    private static final String MSG_2020_SHOULD_BE_A_LEAP_YEAR = "2020 should be a leap year";
    private static final String MSG_2019_SHOULD_NOT_BE_A_LEAP_YEAR = "2019 should not be a leap year";

    /**
     * Setup test data
     *
     * @throws ParseException if the test contains unparseable dates
     */
    @BeforeClass
    public static void setup() throws ParseException
    {
        DATE_2019_01_19 = DateUtils.parseDate(STR_2019_01_19_RFC_3339);
        DATE_2020_02_20 = DateUtils.parseDate(STR_2020_02_21_RFC_3339);
    }

    /**
     * Checks the function with years as doubles
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testIsLeapYearWithYearsAsDoubles() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(2019d);
        function.run(parameters);
        assertEquals(MSG_2019_SHOULD_NOT_BE_A_LEAP_YEAR, FALSE, parameters.pop());

        parameters = CollectionsUtils.newParametersStack(2020d);
        function.run(parameters);
        assertEquals(MSG_2020_SHOULD_BE_A_LEAP_YEAR, TRUE, parameters.pop());
    }

    /**
     * Checks the function with valid dates as strings in RFC-3339 format
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testIsLeapYearWithValidDatesAsStringRfc3339() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_01_19_RFC_3339);
        function.run(parameters);
        assertEquals(MSG_2019_SHOULD_NOT_BE_A_LEAP_YEAR, FALSE, parameters.pop());

        parameters = CollectionsUtils.newParametersStack(STR_2020_02_21_RFC_3339);
        function.run(parameters);
        assertEquals(MSG_2020_SHOULD_BE_A_LEAP_YEAR, TRUE, parameters.pop());
    }

    /**
     * Checks the function with valid Dates objects
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testIsLeapYearWithValidDates() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DATE_2019_01_19);
        function.run(parameters);
        assertEquals(MSG_2019_SHOULD_NOT_BE_A_LEAP_YEAR, FALSE, parameters.pop());

        parameters = CollectionsUtils.newParametersStack(DATE_2020_02_20);
        function.run(parameters);
        assertEquals(MSG_2020_SHOULD_BE_A_LEAP_YEAR, TRUE, parameters.pop());
    }

    /**
     * Checks the function returns false if an invalid string is received
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testIsLeapYearWithInvalidString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("test");
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    /**
     * Checks the function returns false if an empty string is received
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testIsLeapYearWithEmptyString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("");
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    /**
     * Checks the function returns false if a null object is received
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testIsLeapYearWithNullDate() throws org.nfunk.jep.ParseException
    {
        Date date = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(date);
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

}
