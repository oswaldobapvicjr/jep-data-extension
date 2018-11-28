package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

/**
 * Unit tests for the {@link Concat} function
 *
 * @author oswaldo.bapvic.jr
 */
public class ConcatTest extends JepFunctionTest
{
    private static final String STRING_A = "A";
    private static final Integer INT_1 = Integer.valueOf(10);

    private Concat _concat = new Concat();

    /**
     * Tests the successful concatenation of a string and an integer
     */
    @Test
    public void testConcatenateStringAndInt() throws ParseException
    {
        Stack<Object> parameters = getParametersStack(STRING_A, INT_1);
        _concat.run(parameters);
        assertEquals("A10", parameters.pop());
    }

}
