package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Stack;

import org.junit.Test;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link TypeOf} function
 *
 * @author oswaldo.bapvic.jr
 */
public class TypeOfTest
{
    // Test subject
    private static TypeOf function = new TypeOf();

    /**
     * Checks the function with a string
     */
    @Test
    public void testWithString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("test");
        function.run(parameters);
        assertEquals("java.lang.String", parameters.pop());
    }

    /**
     * Checks the function with an integer
     */
    @Test
    public void testWithInteger() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(1);
        function.run(parameters);
        assertEquals("java.lang.Integer", parameters.pop());
    }

    /**
     * Checks the function with a double
     */
    @Test
    public void testWithDouble() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(1d);
        function.run(parameters);
        assertEquals("java.lang.Double", parameters.pop());
    }

    /**
     * Checks the function with a Date
     */
    @Test
    public void testWithDate() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(new Date());
        function.run(parameters);
        assertEquals("java.util.Date", parameters.pop());
    }

    /**
     * Checks the function with a null object
     */
    @Test
    public void testWithNull() throws org.nfunk.jep.ParseException
    {
        String s = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(s);
        function.run(parameters);
        assertEquals("null", parameters.pop());
    }

}
