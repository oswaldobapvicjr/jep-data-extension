package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.Comparative;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.DateUtils;

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

    private DateAwareComparative eq = new DateAwareComparative(Comparative.EQ);
    private DateAwareComparative ge = new DateAwareComparative(Comparative.GE);
    private DateAwareComparative gt = new DateAwareComparative(Comparative.GT);
    private DateAwareComparative le = new DateAwareComparative(Comparative.LE);
    private DateAwareComparative lt = new DateAwareComparative(Comparative.LT);
    private DateAwareComparative ne = new DateAwareComparative(Comparative.NE);

    /**
     * Tests the comparison of two string dates with the "lower-than" operator
     *
     * @throws ParseException
     */
    @Test
    public void testStringDateLowerThan() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        le.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_2, STR_DATE_1);
        gt.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        le.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_2, STR_DATE_1);
        ge.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        eq.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        ne.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_1, DATE_1);
        eq.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_1, INT_1);
        eq.run(parameters);
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
        Stack<Object> parameters = CollectionsUtils.newParametersStack(INT_1, INT_2);
        ne.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests execution with an unsupported operator code must behave similarly to JEP's
     * standard comparator, returning false.
     *
     * @throws ParseException
     */
    @Test
    public void testDateComparisonWithUnsupportedOperator() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_DATE_1, STR_DATE_2);
        DateAwareComparative function = new DateAwareComparative(10);
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    @Test
    public void testWithMultipleDates() throws ParseException
    {
        assertFunction(STR_DATE_1, eq, STR_DATE_2, FALSE);
        assertFunction(STR_DATE_1, eq, STR_DATE_1, TRUE);
        assertFunction(STR_DATE_1, ne, STR_DATE_2, TRUE);
        assertFunction(STR_DATE_2, ne, STR_DATE_2, FALSE);
        assertFunction(STR_DATE_1, ge, STR_DATE_2, FALSE);
        assertFunction(STR_DATE_1, ge, STR_DATE_1, TRUE);
        assertFunction(STR_DATE_2, ge, STR_DATE_1, TRUE);
        assertFunction(STR_DATE_1, gt, STR_DATE_2, FALSE);
        assertFunction(STR_DATE_2, le, STR_DATE_1, FALSE);
        assertFunction(STR_DATE_1, le, STR_DATE_2, TRUE);
        assertFunction(STR_DATE_1, le, STR_DATE_1, TRUE);
        assertFunction(STR_DATE_1, lt, STR_DATE_2, TRUE);
    }

    private void assertFunction(Object o1, DateAwareComparative function, Object o2, double expected) throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(o1, o2);
        function.run(parameters);
        assertEquals(expected, parameters.pop());
    }
}
