package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.UnaryStringFunction.Strategy;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link UnaryStringFunction} function
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryStringFunctionTest
{
    // Test data
    private static final String STRING_ABC_UPPER = "ABC123";
    private static final String STRING_ABC_LOWER = "abc123";
    private static final String STRING_TEST = "test";
    private static final String STRING_TEST_SPACES = " test  ";
    private static final String STRING_BLANK = " ";


    /// Test subjects
    private static UnaryStringFunction funcLower = new UnaryStringFunction(Strategy.LOWER);
    private static UnaryStringFunction funcUpper = new UnaryStringFunction(Strategy.UPPER);
    private static UnaryStringFunction funcTrim = new UnaryStringFunction(Strategy.TRIM);

    /**
     * Tests the lower function for a valid string
     */
    @Test
    public void testLowerCaseWithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_ABC_UPPER);
        funcLower.run(parameters);
        assertEquals(STRING_ABC_LOWER, parameters.pop());
    }

    /**
     * Tests the upper function for a valid string
     */
    @Test
    public void testUpperCaseWithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_ABC_LOWER);
        funcUpper.run(parameters);
        assertEquals(STRING_ABC_UPPER, parameters.pop());
    }

    /**
     * Tests the trim function with a blank string
     */
    @Test
    public void testTrimWithBlankString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_BLANK);
        funcTrim.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

    /**
     * Tests the trim function with a valid string
     */
    @Test
    public void testTrimWithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_TEST_SPACES);
        funcTrim.run(parameters);
        assertEquals(STRING_TEST, parameters.pop());
    }

    /**
     * Tests with a null object. Shall not throw NullPointerException.
     */
    @Test
    public void testWithNull() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(null);
        funcLower.run(parameters);
        assertEquals(null, parameters.pop());
    }

    /**
     * Tests with an empty string
     */
    @Test
    public void testWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(StringUtils.EMPTY);
        funcLower.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

}
