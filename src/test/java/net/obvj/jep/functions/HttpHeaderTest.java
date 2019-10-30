package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link HttpHeader} function
 *
 * @author oswaldo.bapvic.jr
 */
public class HttpHeaderTest
{
    private static HttpHeader function = new HttpHeader();

    /**
     * Runs the subject function with the given parameters
     */
    private void run(Stack<Object> parameters) throws ParseException
    {
        function.setCurNumberOfParameters(parameters.size());
        function.run(parameters);
    }

    @Test
    public void runWithAValidHeaderAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("Authorization=Basic dXNlcjpwYXNz");
        run(parameters);
        Map<String, String> map = (Map<String, String>) parameters.pop();
        assertEquals(1, map.size());
        assertEquals("Basic dXNlcjpwYXNz", map.get("Authorization"));
    }

    @Test
    public void runWithTwoValidHeaderAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("Authorization=Basic dXNlcjpwYXNz",
                "Accept=application/json");
        run(parameters);
        Map<String, String> map = (Map<String, String>) parameters.pop();
        assertEquals(2, map.size());
        assertEquals("Basic dXNlcjpwYXNz", map.get("Authorization"));
        assertEquals("application/json", map.get("Accept"));
    }

    @Test
    public void runWithNoHeader() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack();
        run(parameters);
        Map<String, String> map = (Map<String, String>) parameters.pop();
        assertEquals(0, map.size());
    }
}
