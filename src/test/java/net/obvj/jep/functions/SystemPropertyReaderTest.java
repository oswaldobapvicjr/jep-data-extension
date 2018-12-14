package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link SystemPropertyReader} function
 *
 * @author oswaldo.bapvic.jr
 */
public class SystemPropertyReaderTest
{
    // Test data
    private static final String KEY_OS_NAME = "os.name";
    private static final String EXPECTED_OS_NAME = System.getProperty(KEY_OS_NAME);

    private static SystemPropertyReader function = new SystemPropertyReader();

    /**
     * Tests the function retrieving a core system property
     */
    @Test
    public void testGetSystemProperty() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(KEY_OS_NAME);
        function.run(parameters);
        assertEquals(EXPECTED_OS_NAME, parameters.pop());
    }

}
