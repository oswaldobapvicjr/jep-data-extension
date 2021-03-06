package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.UnarySystemFunction.Strategy;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link UnarySystemFunction} function
 *
 * @author oswaldo.bapvic.jr
 */
public class UnarySystemFunctionTest
{
    // Test data
    private static final String KEY_OS_NAME = "os.name";
    private static final String EXPECTED_OS_NAME = System.getProperty(KEY_OS_NAME);

    private static UnarySystemFunction getSystemPropertiesfunction = new UnarySystemFunction(Strategy.GET_SYSTEM_PROPERTY);

    /**
     * Tests the getSystemProperty function retrieving a core system property
     */
    @Test
    public void testGetSystemProperty() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(KEY_OS_NAME);
        getSystemPropertiesfunction.run(parameters);
        assertEquals(EXPECTED_OS_NAME, parameters.pop());
    }

}
