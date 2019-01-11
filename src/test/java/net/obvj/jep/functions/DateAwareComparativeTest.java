package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.DateUtils;
import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link DateAwareComparative} function
 *
 * @author oswaldo.bapvic.jr
 */
public class DateAwareComparativeTest
{
    // Test data
    private static final String STR_DATE_1 = "2017-03-11T10:15:27.000Z";
    private static final String STR_DATE_2 = "2017-03-11T10:15:27.001Z";
    private static final Date DATE_1 = DateUtils.parseDate(STR_DATE_1);
    private static final String STR_1 = "1";
    private static final int INT_1 = 1;
    private static final int INT_2 = 2;

    // Expected results
    private static final Double FALSE = 0d;
    private static final Double TRUE = 1d;

    /**
     * Tests the comparison of two string dates with the "lower-than" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringDateLowerThan() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.LT);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests the comparison of two string dates with the "greater-than" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringDateGreaterThan() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_2, STR_DATE_1);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.GT);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests the comparison of two string dates with the "lower-than-or-equal" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringDateLowerThanOrEqual() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.LE);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests the comparison of two string dates with the "greater-than-or-equal" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringDateGreaterThanOrEqual() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_2, STR_DATE_1);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.GE);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests the comparison of two string dates with the "equal" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringDateEquals() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.EQ);
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    /**
     * Tests the comparison of two string dates with the "not-equal" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringDateNotEquals() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.NE);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests the comparison of a string a and a date with the "equal" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringEqualsDate() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_1, DATE_1);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.EQ);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests the comparison of between a string representing a valid number and an integer
     *
     * @throws ParseException
     */
    @Test
    public void testStringNumbersEqual() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_1, INT_1);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.EQ);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests the comparison of two integer values by JEP's standard code
     *
     * @throws ParseException
     */
    @Test
    public void testIntegersNotEqual() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(INT_1, INT_2);
        DateAwareComparative function = new DateAwareComparative(DateAwareComparative.NE);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }
}
