package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link Replace} function
 *
 * @author oswaldo.bapvic.jr
 */
public class ReplaceTest
{
    private static final String FEE_BEE = "fee-bee";
    private static final String FOO_BOO = "foo-boo";
    private static final String F_B = "f-b";
    private static final String EE = "ee";
    private static final String OO = "oo";

    private static Replace function = new Replace();

    /**
     * Tests the replacement function with a valid string and two replacements
     */
    @Test
    public void testReplaceWithTwoReplacements() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(FEE_BEE, EE, OO);
        function.run(parameters);
        assertEquals(FOO_BOO, parameters.pop());
    }

    /**
     * Tests the replacement function with a null string
     */
    @Test
    public void testReplaceWithNullString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(null, EE, OO);
        function.run(parameters);
        assertNull(parameters.pop());
    }

    /**
     * Tests the replacement function with an empty string
     */
    @Test
    public void testReplaceWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(StringUtils.EMPTY, EE, OO);
        function.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

    /**
     * Tests the replacement function with a null search criteria
     */
    @Test
    public void testReplaceWithNullSearchCriteria() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(FEE_BEE, null, OO);
        function.run(parameters);
        assertEquals(FEE_BEE, parameters.pop());
    }

    /**
     * Tests the replacement function with an empty search criteria
     */
    @Test
    public void testReplaceWithEmptySearchCriteria() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(FEE_BEE, StringUtils.EMPTY, OO);
        function.run(parameters);
        assertEquals(FEE_BEE, parameters.pop());
    }

    /**
     * Tests the replacement function with an null replacement
     */
    @Test
    public void testReplaceWithNullReplacement() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(FEE_BEE, EE, null);
        function.run(parameters);
        assertEquals(F_B, parameters.pop());
    }

    /**
     * Tests the replacement function with an empty replacement
     */
    @Test
    public void testReplaceWithEmptyReplacement() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(FEE_BEE, EE, StringUtils.EMPTY);
        function.run(parameters);
        assertEquals(F_B, parameters.pop());
    }
}
