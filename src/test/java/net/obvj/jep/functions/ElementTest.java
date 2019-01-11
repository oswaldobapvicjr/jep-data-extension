package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link Element} function
 *
 * @author oswaldo.bapvic.jr
 */
public class ElementTest
{
    // Test data
    private static final String STR_VALUE1 = "Str01";
    private static final String STR_VALUE2 = "Str02";
    private static final String[] ARRAY1 = new String[] { STR_VALUE1, STR_VALUE2 };
    private static final List<String> LIST1 = Arrays.asList(STR_VALUE1, STR_VALUE2);
    private static final String STR_JSON_ARRAY1 = "[" + STR_VALUE1 + "," + STR_VALUE2 + "]";
    private static final JSONArray JSON_ARRAY1 = new JSONArray(LIST1);

    private static Element function = new Element();

    /**
     * Tests the retrieval of the first element of an array
     *
     * @throws ParseException
     */
    @Test
    public void testGetFirstElementOfArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(ARRAY1, 0);
        function.run(parameters);
        assertEquals(STR_VALUE1, parameters.pop());
    }

    /**
     * Tests the retrieval of the first element of a Vector
     *
     * @throws ParseException
     */
    @Test
    public void testGetFirstElementOfAVector() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(new Vector<>(LIST1), 0);
        function.run(parameters);
        assertEquals(STR_VALUE1, parameters.pop());
    }

    /**
     * Tests the retrieval of the first element of a List
     *
     * @throws ParseException
     */
    @Test
    public void testGetFirstElementOfAList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST1, 0);
        function.run(parameters);
        assertEquals(STR_VALUE1, parameters.pop());
    }

    /**
     * Tests the retrieval of the first element of a JSONArray
     *
     * @throws ParseException
     */
    @Test
    public void testGetFirstElementOfAJSONArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY1, 0);
        function.run(parameters);
        assertEquals(STR_VALUE1, parameters.pop());
    }

    /**
     * Tests the retrieval of the first element of a string representation of JSONArray
     *
     * @throws ParseException
     */
    @Test
    public void testGetFirstElementOfAJSONArrayString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_JSON_ARRAY1, 0);
        function.run(parameters);
        assertEquals(STR_VALUE1, parameters.pop());
    }

    /**
     * Tests the retrieval of the first element for a string
     *
     * @throws ParseException
     */
    @Test
    public void testGetFirstElementOfAString() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_VALUE1, 0);
        function.run(parameters);
        assertEquals(STR_VALUE1, parameters.pop());
    }

    /**
     * Tests the retrieval of the first element of a null object
     *
     * @throws ParseException
     */
    @Test
    public void testGetFirstElementOfANullObject() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(null, 0);
        function.run(parameters);
        assertNull(parameters.pop());
    }

}
