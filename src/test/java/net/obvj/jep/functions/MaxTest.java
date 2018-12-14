package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.Max;
import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link Max} function
 *
 * @author oswaldo.bapvic.jr
 */
public class MaxTest
{
    private static final Double DOUBLE_1 = Double.valueOf(1);
    private static final Double DOUBLE_2 = Double.valueOf(2);
    private static final Double[] ARRAY_DOUBLES = new Double[] { DOUBLE_1, DOUBLE_2 };
    private static final List<Double> LIST_DOUBLES = Arrays.asList(DOUBLE_1, DOUBLE_2);
    private static final JSONArray JSON_ARRAY_DOUBLES = new JSONArray(LIST_DOUBLES);

    private static final String STRING_1 = "1";
    private static final String STRING_2 = "2";
    private static final String STRING_3 = "3";
    private static final String[] ARRAY_STRING_NUMBERS = new String[] { STRING_2, STRING_3, STRING_1 };
    private static final List<String> LIST_STRING_NUMBERS = Arrays.asList(ARRAY_STRING_NUMBERS);
    private static final JSONArray JSON_ARRAY_STRING_NUMBERS = new JSONArray(LIST_STRING_NUMBERS);

    private static final String STR_DATE_1 = "2018-09-27T12:42:27.000Z";
    private static final String STR_DATE_2 = "2018-09-28T13:43:28.000Z";
    private static final List<String> LIST_STR_DATES = Arrays.asList(STR_DATE_1, STR_DATE_2);
    private static final JSONArray JSON_ARRAY_STR_DATES = new JSONArray(LIST_STR_DATES);

    private static final Date DATE_1 = Date.from(Instant.parse(STR_DATE_1));
    private static final Date DATE_2 = Date.from(Instant.parse(STR_DATE_2));
    private static final List<Date> LIST_DATES = Arrays.asList(DATE_1, DATE_2);
    private static final JSONArray JSON_ARRAY_DATES = new JSONArray(LIST_DATES);

    private static final List<?> LIST_DATE_AND_NUMBER = Arrays.asList(STR_DATE_1, DOUBLE_1);

    private static Max function = new Max();

    /**
     * Expected exception
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests the maximum element for an array of doubles
     */
    @Test
    public void testMaxDoubleFromArray() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the maximum element for a list of doubles
     */
    @Test
    public void testMaxDoubleFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the maximum element for a JSON array
     */
    @Test
    public void testMaxDoubleFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the maximum element for an array of valid numbers as string
     */
    @Test
    public void testMaxStringNumberFromArray() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(STRING_3, parameters.pop());
    }

    /**
     * Tests the maximum element for a list of valid numbers as string
     */
    @Test
    public void testMaxStringNumberFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(STRING_3, parameters.pop());
    }

    /**
     * Tests the maximum element for a JSON array of valid numbers as string
     */
    @Test
    public void testMaxStringNumberFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(STRING_3, parameters.pop());
    }

    /**
     * Tests the maximum for a null object returns null
     */
    @Test
    public void testMaxOfNullObject() throws ParseException
    {
        Object nullObject = null;
        Stack<Object> parameters = StackUtils.newParametersStack(nullObject);
        function.run(parameters);
        assertNull(parameters.pop());
    }

    /**
     * Tests the maximum element for a single number returns the same object
     */
    @Test
    public void testMaxOfSingleElementDouble() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(DOUBLE_1);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the maximum element for a single number as string returns the same object
     */
    @Test
    public void testMaxOfSingleElementStringNumber() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STRING_1);
        function.run(parameters);
        assertEquals(STRING_1, parameters.pop());
    }

    /**
     * Tests the maximum element for an array containing a single number returns the single
     * object
     */
    @Test
    public void testMaxOfArrayWithOneElementDouble() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        Double[] array = new Double[] { DOUBLE_1 };
        parameters.push(array);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the maximum element for a list containing a single number returns the single
     * object
     */
    @Test
    public void testMaxOfListWithOneElementDouble() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(Arrays.asList(DOUBLE_1));
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the maximum element for a list of dates
     */
    @Test
    public void testMaxDateFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_DATES);
        function.run(parameters);
        assertEquals(DATE_2, parameters.pop());
    }

    /**
     * Tests the maximum element for a JSON array of dates
     */
    @Test
    public void testMaxDateFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_DATES);
        function.run(parameters);
        assertEquals(DATE_2, parameters.pop());
    }

    /**
     * Tests the maximum element for a single date returns the same object
     */
    @Test
    public void testMaxOfSingleElementDate() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(DATE_1);
        function.run(parameters);
        assertEquals(DATE_1, parameters.pop());
    }

    /**
     * Tests the maximum element for a list of dates as string
     */
    @Test
    public void testMaxStringDateFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_STR_DATES);
        function.run(parameters);
        assertEquals(STR_DATE_2, parameters.pop());
    }

    /**
     * Tests the maximum element for a JSON array of dates as string
     */
    @Test
    public void testMaxStringDateFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_STR_DATES);
        function.run(parameters);
        assertEquals(STR_DATE_2, parameters.pop());
    }

    /**
     * Tests the maximum element for a single date as string returns the same object
     */
    @Test
    public void testMaxOfSingleElementStringDate() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_1);
        function.run(parameters);
        assertEquals(STR_DATE_1, parameters.pop());
    }

    /**
     * Tests the maximum element for list of different types
     */
    @Test
    public void testMaxElementForListOfDifferentTypes() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_DATE_AND_NUMBER);
        exception.expect(IllegalArgumentException.class);
        function.run(parameters);
    }
}
