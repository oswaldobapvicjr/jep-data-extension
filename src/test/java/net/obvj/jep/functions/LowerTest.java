package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link Lower} function
 *
 * @author oswaldo.bapvic.jr
 */
public class LowerTest
{
    // Test data
    private static final String STRING_ABC = "ABC123";
    private static final String EXPECTED_RESULT_STRING_ABC = "abc123";

    private Lower _function = new Lower();

    /**
     * Tests the function for a valid string
     */
    @Test
    public void testLoweCaseWithValidString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STRING_ABC);
        _function.run(parameters);
        assertEquals(EXPECTED_RESULT_STRING_ABC, parameters.pop());
    }

    /**
     * Tests the function with a null object. Shall not throw NullPointerException.
     */
    @Test
    public void testLowerCaseWithNull() throws ParseException
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
    public void testLowerCaseWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(StringUtils.EMPTY);
        _function.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

}
