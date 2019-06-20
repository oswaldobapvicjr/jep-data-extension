package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Concat} function
 *
 * @author oswaldo.bapvic.jr
 */
public class ConcatTest
{
    private static final String STRING_A = "A";
    private static final Integer INT_1 = Integer.valueOf(10);

    private static Concat function = new Concat();

    /**
     * Runs the subject function with the given parameters
     */
    private void run(Stack<Object> parameters) throws ParseException
    {
        function.setCurNumberOfParameters(parameters.size());
        function.run(parameters);
    }

    /**
     * Tests the successful concatenation of a string and an integer
     */
    @Test
    public void testConcatenateStringAndInt() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_A, INT_1);
        run(parameters);
        assertEquals("A10", parameters.pop());
    }

    /**
     * Tests the successful concatenation of a string and an integer
     */
    @Test
    public void testConcatenateOneString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_A);
        run(parameters);
        assertEquals("A", parameters.pop());
    }

    /**
     * Tests the concatenation function with no arguments
     */
    @Test
    public void testConcatenateNoArgs() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack();
        run(parameters);
        assertEquals("", parameters.pop());
    }
}
