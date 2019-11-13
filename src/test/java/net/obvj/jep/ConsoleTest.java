package net.obvj.jep;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.nfunk.jep.FunctionTable;

/**
 * Unit tests for the {@link Console}.
 * 
 * @author oswaldo.bapvic.jr
 */
public class ConsoleTest
{
    Console console = new Console();
    
    @Before
    public void setup()
    {
        console.initialise();
    }
    
    @Test
    public void testJEPContextContainsKeyCustomFunctions()
    {
        FunctionTable table = console.getJep().getFunctionTable();
        assertTrue("http function not found", table.containsKey("http"));
        assertTrue("jsonpath function not found", table.containsKey("jsonpath"));
        assertTrue("max function not found", table.containsKey("max"));
        assertTrue("str2date function not found", table.containsKey("str2date"));
        assertTrue("uuid", table.containsKey("uuid"));
    }
    
    @Test
    public void testGetAppletInfoContainsCustomInfo()
    {
        String info = console.getAppletInfo();
        assertTrue(info.contains("jep-data-extension"));
    }

}
