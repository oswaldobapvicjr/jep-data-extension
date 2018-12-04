package net.obvj.jep;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.nfunk.jep.FunctionTable;
import org.nfunk.jep.JEP;

public class JEPContextFactoryTest
{
    JEP jep = JEPContextFactory.newContext();

    /**
     * Checks that all custom functions are available at JEP
     */
    @Test
    public void testRegisteredFunctions()
    {
        FunctionTable table = jep.getFunctionTable();

        // String
        assertTrue(table.containsKey("concat"));
        assertTrue(table.containsKey("lower"));
        assertTrue(table.containsKey("replace"));
        assertTrue(table.containsKey("trim"));
        assertTrue(table.containsKey("upper"));

        // Date
        assertTrue(table.containsKey("now"));

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

}
