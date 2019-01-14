package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link Now} function
 *
 * @author oswaldo.bapvic.jr
 */
public class NowTest
{
    /**
     * Tests that the function pushes a Date object in the parameters stack.
     *
     * @throws ParseException in case of errors parsing the expression
     */
    @Test
    public void testThatFunctionReturnsDate() throws ParseException
    {
        Now function = new Now();
        Stack<Object> stack = StackUtils.newParametersStack();
        function.run(stack);
        assertEquals(Date.class, stack.pop().getClass());
    }

}
