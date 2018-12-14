package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

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
     * Tests the successful concatenation of a string and an integer
     */
    @Test
    public void testConcatenateStringAndInt() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STRING_A, INT_1);
        function.run(parameters);
        assertEquals("A10", parameters.pop());
    }

}
