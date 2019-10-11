package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.StringPaddingFunction.Strategy;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link StringPaddingFunction} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class StringPaddingFunctionTest
{
    /// Test subjects
    private static StringPaddingFunction rightPad = new StringPaddingFunction(Strategy.RIGHT_PAD);
    private static StringPaddingFunction leftPad = new StringPaddingFunction(Strategy.LEFT_PAD);

    /**
     * Runs the function with the given parameters
     */
    private void run(StringPaddingFunction function, Stack<Object> stack) throws ParseException
    {
        function.setCurNumberOfParameters(stack.size());
        function.run(stack);
    }

    /**
     * Tests the rightPad function with one parameter
     */
    @Test(expected = ParseException.class)
    public void testRightPadWithOneParam() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("abc");
        run(rightPad, parameters);
    }

    /**
     * Tests the rightPad function with two parameters
     */
    @Test
    public void testRightPadWithTwoParams() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("abc", 5);
        run(rightPad, parameters);
        assertEquals("abc  ", parameters.pop());
    }

    /**
     * Tests the rightPad function with three parameters
     */
    @Test
    public void testRightPadWithThreeParams() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("abc", 5, "-");
        run(rightPad, parameters);
        assertEquals("abc--", parameters.pop());
    }

    /**
     * Tests the rightPad function with four parameters
     */
    @Test(expected = ParseException.class)
    public void testRightPadWithFourParams() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("abc", 5, " ", 1);
        run(rightPad, parameters);
    }

    /**
     * Tests the rightPad function with empty string
     */
    @Test
    public void testRightPadWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("", 5);
        run(rightPad, parameters);
        assertEquals("     ", parameters.pop());
    }

    /**
     * Tests the rightPad function with larger input string than the size
     */
    @Test
    public void testRightPadWithLargerStringThanSize() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("abc", 1);
        run(rightPad, parameters);
        assertEquals("abc", parameters.pop());
    }

    /**
     * Tests the leftPad function with two parameters
     */
    @Test
    public void testLeftPadWithTwoParams() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("abc", 5);
        run(leftPad, parameters);
        assertEquals("  abc", parameters.pop());
    }

    /**
     * Tests the leftPad function with three parameters
     */
    @Test
    public void testLeftPadWithThreeParams() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("abc", 5, "-");
        run(leftPad, parameters);
        assertEquals("--abc", parameters.pop());
    }

}
