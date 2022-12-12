package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link IsEmpty} function
 *
 * @author oswaldo.bapvic.jr
 */
public class IsEmptyTest
{
    // Expected results
    private static final double FALSE = 0d;
    private static final double TRUE = 1d;

    // Test subject
    private static IsEmpty function = new IsEmpty();

    /**
     * Checks the function with an empty string
     */
    @Test
    public void testWithEmptyString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("");
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Checks the function with a null string
     */
    @Test
    public void testWithNullString() throws org.nfunk.jep.ParseException
    {
        String string = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(string);
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Checks the function with an empty JSON object as string
     */
    @Test
    public void testWithEmptyJsonObjectAsString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("{}");
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Checks the function with an empty JSON array as string
     */
    @Test
    public void testWithEmptyJsonArrayAsString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("[]");
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Checks the function with a non-empty string
     */
    @Test
    public void testWithNonEmptyString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("test");
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    /**
     * Checks the function with an empty JSON object
     */
    @Test
    public void testWithEmptyJsonObject() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(new JSONObject());
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Checks the function with an empty JSON object
     */
    @Test
    public void testWithNonEmptyJsonObjectAsString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("{\"test\":1}");
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    /**
     * Checks the function with an empty JSON array
     */
    @Test
    public void testWithEmptyJsonArray() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(new JSONArray());
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Checks the function with a non-empty list
     */
    @Test
    public void testWithNonEmptyJsonArrayAsString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("[\"test\"]");
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    /**
     * Checks the function with an empty list
     */
    @Test
    public void testWithEmptyList() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(new ArrayList<String>());
        function.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Checks the function with a non-empty list
     */
    @Test
    public void testWithNonEmptyList() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(Arrays.asList("test"));
        function.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

}
