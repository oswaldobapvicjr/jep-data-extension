package net.obvj.jep;

import static org.junit.Assert.*;

import org.junit.Test;
import org.nfunk.jep.FunctionTable;

/**
 * Unit tests for the {@link Console} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class ConsoleTest
{
    private Console console = new Console();

    @Test
    public void testInitialize()
    {
        console.initialise();
        FunctionTable functionTable = console.getJEP().getFunctionTable();

        // Test if some custom functions are available
        assertNotNull(functionTable.get("concat"));
        assertNotNull(functionTable.get("http"));
        assertNotNull(functionTable.get("str2date"));
    }

}
