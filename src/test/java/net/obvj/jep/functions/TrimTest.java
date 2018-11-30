package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link Trim} function
 *
 * @author oswaldo.bapvic.jr
 */
public class TrimTest
{
    // Test data
    private static final String STRING_TEST = " test  ";
    private static final String STRING_BLANK = " ";

    private static final String EXPECTED_RESULT_STRING_TEST = "test";

    private Trim _function = new Trim();

    /**
     * Tests the function for a valid string
     */
    @Test
    public void testTrimWithValidString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STRING_TEST);
        _function.run(parameters);
        assertEquals(EXPECTED_RESULT_STRING_TEST, parameters.pop());
    }

    /**
     * Tests the function with a null object. Shall not throw NullPointerException.
     */
    @Test
    public void testTrimCaseWithNull() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(null);
        _function.run(parameters);
        assertEquals(null, parameters.pop());
    }

    /**
     * Tests the function with an empty string
     */
    @Test
    public void testTrimWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(StringUtils.EMPTY);
        _function.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

    /**
     * Tests the function with a blank string
     */
    @Test
    public void testTrimWithBlankString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STRING_BLANK);
        _function.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

}
