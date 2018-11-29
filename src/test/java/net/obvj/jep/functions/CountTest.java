package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;
import org.nfunk.jep.ParseException;

/**
 * Unit tests for the {@link Count} function
 *
 * @author oswaldo.bapvic.jr
 */
public class CountTest extends JepFunctionTest
{
    private static final String STRING_A = "A";
    private static final String STRING_B = "B";
    private static final Integer INT_1 = Integer.valueOf(10);

    // Test collections
    private static final Object[] ARRAY_1 = new String[] { STRING_A, STRING_B };
    private static final List<Object> LIST_1 = Arrays.asList(ARRAY_1);
    private static final JSONArray JSON_ARRAY_1 = new JSONArray(LIST_1);
    private static final String STRING_JSON_ARRAY_1 = "[" + STRING_A + "," + STRING_B + "]";

    // Expected results
    private static final Integer EXPECTED_COUNT_FOR_ARRAY_1 = Integer.valueOf(2);
    private static final Integer EXPECTED_COUNT_FOR_SINGLE_ELEMENT = Integer.valueOf(1);
    private static final Integer EXPECTED_COUNT_FOR_NULL = Integer.valueOf(0);

    private Count _count = new Count();

    /**
     * Tests the count of elements inside an array
     */
    @Test
    public void testCountOfArray() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_1);
        _count.run(parameters);
        assertEquals(EXPECTED_COUNT_FOR_ARRAY_1, parameters.pop());
    }

    /**
     * Tests the count of elements inside a list
     */
    @Test
    public void testCountOfList() throws ParseException
    {
        Stack<Object> parameters = newParametersStack(LIST_1);
        _count.run(parameters);
        assertEquals(EXPECTED_COUNT_FOR_ARRAY_1, parameters.pop());
    }

    /**
     * Tests the count of elements inside a JSON array
     */
    @Test
    public void testCountOfJsonArray() throws ParseException
    {
        Stack<Object> parameters = newParametersStack(JSON_ARRAY_1);
        _count.run(parameters);
        assertEquals(EXPECTED_COUNT_FOR_ARRAY_1, parameters.pop());
    }

    /**
     * Tests the count of elements inside a string representation of JSON array
     */
    @Test
    public void testCountOfStringRepresentationOfJsonArray() throws ParseException
    {
        Stack<Object> parameters = newParametersStack(STRING_JSON_ARRAY_1);
        _count.run(parameters);
        assertEquals(EXPECTED_COUNT_FOR_ARRAY_1, parameters.pop());
    }

    /**
     * Tests the count for a null object returns zero
     */
    @Test
    public void testCountOfNullObject() throws ParseException
    {
        Object nullObject = null;
        Stack<Object> parameters = newParametersStack(nullObject);
        _count.run(parameters);
        assertEquals(EXPECTED_COUNT_FOR_NULL, parameters.pop());
    }

    /**
     * Tests the count for a standard string returns 1
     */
    @Test
    public void testCountOfString() throws ParseException
    {
        Stack<Object> parameters = newParametersStack(STRING_A);
        _count.run(parameters);
        assertEquals(EXPECTED_COUNT_FOR_SINGLE_ELEMENT, parameters.pop());
    }

    /**
     * Tests the count for a standard object returns 1
     */
    @Test
    public void testCountOfInt() throws ParseException
    {
        Stack<Object> parameters = newParametersStack(INT_1);
        _count.run(parameters);
        assertEquals(EXPECTED_COUNT_FOR_SINGLE_ELEMENT, parameters.pop());
    }

}
