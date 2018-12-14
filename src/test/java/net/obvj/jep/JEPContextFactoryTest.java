package net.obvj.jep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nfunk.jep.FunctionTable;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

public class JEPContextFactoryTest
{
    /**
     * Checks that all custom functions are available at JEP
     */
    @Test
    public void testRegisteredFunctions()
    {
        JEP jep = JEPContextFactory.newContext();
        FunctionTable table = jep.getFunctionTable();

        // String
        assertTrue(table.containsKey("concat"));
        assertTrue(table.containsKey("lower"));
        assertTrue(table.containsKey("replace"));
        assertTrue(table.containsKey("trim"));
        assertTrue(table.containsKey("upper"));

        // Date
        assertTrue(table.containsKey("now"));
        assertTrue(table.containsKey("date2str"));
        assertTrue(table.containsKey("str2date"));

        // Data manipulation
        assertTrue(table.containsKey("jsonpath"));

        // Statistical
        assertTrue(table.containsKey("count"));

        // Random
        assertTrue(table.containsKey("uuid"));

        // Utility
        assertTrue(table.containsKey("getEnv"));
        assertTrue(table.containsKey("getSystemProperty"));
    }

    /**
     * Tests the JEP context can evaluate an expression with the replace() function
     *
     * @throws ParseException
     */
    @Test
    public void testExpressionReplace() throws ParseException
    {
        JEP jep = JEPContextFactory.newContext();
        Node node = jep.parseExpression("replace(\"foo\", \"oo\", \"ee\")");
        String result = (String) jep.evaluate(node);
        assertEquals("fee", result.toString());
    }

    /**
     * Tests the JEP context can evaluate an expression with the replace() function
     * and a source variable
     *
     * @throws ParseException
     */
    @Test
    public void testExpressionReplaceWithVariable() throws ParseException
    {
        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("myText", "foo");

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("replace(myText, \"oo\", \"ee\")");
        String result = (String) jep.evaluate(node);
        assertEquals("fee", result.toString());
    }

}
