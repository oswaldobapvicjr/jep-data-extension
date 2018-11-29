package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

/**
 * Unit tests for the {@link Upper} function
 *
 * @author oswaldo.bapvic.jr
 */
public class UpperTest extends JepFunctionTest
{
    // Test data
    private static final String STRING_ABC = "abc123";
    private static final String EXPECTED_RESULT_STRING_ABC = "ABC123";

    private Upper _function = new Upper();

    /**
     * Tests the function for a valid string
     */
    @Test
    public void testUpperCaseWithValidString() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(STRING_ABC);
        _function.run(parameters);
        assertEquals(EXPECTED_RESULT_STRING_ABC, parameters.pop());
    }

    /**
     * Tests the function with a null object. Shall not throw NullPointerException.
     */
    @Test
    public void testUpperCaseWithNull() throws ParseException
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
    public void testUpperCaseWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(StringUtils.EMPTY);
        _function.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

}
