package net.obvj.jep.functions;

import static org.junit.Assert.assertNotNull;

import java.util.Stack;

import org.junit.Test;

/**
 * Unit tests for the {@link UUID} function
 *
 * @author oswaldo.bapvic.jr
 */
public class UUIDTest
{
    private static UUID _function = new UUID();

    /**
     * Tests the function for a valid string
     */
    @Test
    public void testProducedUUIDNotNull()
    {
        Stack stack = new Stack<>();
        _function.run(stack);
        assertNotNull(stack.pop());
    }

}
