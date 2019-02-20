package net.obvj.jep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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
     * Tests that no instances of this factory are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test(expected = InvocationTargetException.class)
    public void testNoInstancesAllowed() throws Exception
    {
        try
        {
            Constructor<JEPContextFactory> constructor = JEPContextFactory.class.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));

            constructor.setAccessible(true);
            constructor.newInstance();
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(IllegalStateException.class, cause.getClass());
            assertEquals("No instances allowed", cause.getMessage());
            throw ite;
        }
    }

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
        assertTrue(table.containsKey("normalizeString"));
        assertTrue(table.containsKey("replace"));
        assertTrue(table.containsKey("trim"));
        assertTrue(table.containsKey("upper"));

        // Date
        assertTrue(table.containsKey("now"));
        assertTrue(table.containsKey("date2str"));
        assertTrue(table.containsKey("str2date"));
        assertTrue(table.containsKey("daysBetween"));

        // Data manipulation
        assertTrue(table.containsKey("xpath"));
        assertTrue(table.containsKey("jsonpath"));

        // Statistical
        assertTrue(table.containsKey("count"));

        // Random
        assertTrue(table.containsKey("uuid"));

        // Utility
        assertTrue(table.containsKey("getEnv"));
        assertTrue(table.containsKey("getSystemProperty"));
        assertTrue(table.containsKey("getElement"));
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
     * Tests the JEP context can evaluate an expression with the replace() function and a
     * source variable
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
