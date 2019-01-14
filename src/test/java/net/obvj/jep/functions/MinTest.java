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

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link Min} function
 *
 * @author oswaldo.bapvic.jr
 */
public class MinTest
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
    private static final String STR_JSON_ARRAY_STRING_NUMBERS = String.format("[\"%s\",\"%s\",\"%s\"]", STRING_2,
            STRING_3, STRING_1);

    private static final String STR_DATE_1 = "2018-09-27T12:42:27.000Z";
    private static final String STR_DATE_2 = "2018-09-28T13:43:28.000Z";
    private static final List<String> LIST_STR_DATES = Arrays.asList(STR_DATE_1, STR_DATE_2);
    private static final JSONArray JSON_ARRAY_STR_DATES = new JSONArray(LIST_STR_DATES);

    private static final Date DATE_1 = Date.from(Instant.parse(STR_DATE_1));
    private static final Date DATE_2 = Date.from(Instant.parse(STR_DATE_2));
    private static final List<Date> LIST_DATES = Arrays.asList(DATE_1, DATE_2);
    private static final JSONArray JSON_ARRAY_DATES = new JSONArray(LIST_DATES);

    private static final List<?> LIST_DATE_AND_NUMBER = Arrays.asList(STR_DATE_1, DOUBLE_1);

    private static Min function = new Min();

    /**
     * Expected exception
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests the minimum element for an array of doubles
     *
     * @throws ParseException
     */
    @Test
    public void testMinDoubleFromArray() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a list of doubles
     *
     * @throws ParseException
     */
    @Test
    public void testMinDoubleFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a JSON array
     *
     * @throws ParseException
     */
    @Test
    public void testMinDoubleFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for an array of valid numbers as string
     *
     * @throws ParseException
     */
    @Test
    public void testMinStringNumberFromArray() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(STRING_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a list of valid numbers as string
     *
     * @throws ParseException
     */
    @Test
    public void testMinStringNumberFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(STRING_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a JSON array of valid numbers as string
     *
     * @throws ParseException
     */
    @Test
    public void testMinStringNumberFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_JSON_ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(STRING_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a string representation of JSON array of valid numbers as
     * string
     *
     * @throws ParseException
     */
    @Test
    public void testMinStringNumberFromStringRepresentationJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(STRING_1, parameters.pop());
    }

    /**
     * Tests the minimum for a null object returns null
     *
     * @throws ParseException
     */
    @Test
    public void testMinOfNullObject() throws ParseException
    {
        Object nullObject = null;
        Stack<Object> parameters = StackUtils.newParametersStack(nullObject);
        function.run(parameters);
        assertNull(parameters.pop());
    }

    /**
     * Tests the minimum element for a single number returns the same object
     *
     * @throws ParseException
     */
    @Test
    public void testMinOfSingleElementDouble() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(DOUBLE_1);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a single number as string returns the same object
     *
     * @throws ParseException
     */
    @Test
    public void testMinOfSingleElementStringNumber() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STRING_1);
        function.run(parameters);
        assertEquals(STRING_1, parameters.pop());
    }

    /**
     * Tests the minimum element for an array containing a single number returns the single
     * object
     *
     * @throws ParseException
     */
    @Test
    public void testMinOfArrayWithOneElementDouble() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        Double[] array = new Double[] { DOUBLE_1 };
        parameters.push(array);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a list containing a single number returns the single
     * object
     *
     * @throws ParseException
     */
    @Test
    public void testMinOfListWithOneElementDouble() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(Arrays.asList(DOUBLE_1));
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a list of dates
     *
     * @throws ParseException
     */
    @Test
    public void testMinDateFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_DATES);
        function.run(parameters);
        assertEquals(DATE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a JSON array of dates
     *
     * @throws ParseException
     */
    @Test
    public void testMinDateFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_DATES);
        function.run(parameters);
        assertEquals(DATE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a single date returns the same object
     *
     * @throws ParseException
     */
    @Test
    public void testMinOfSingleElementDate() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(DATE_1);
        function.run(parameters);
        assertEquals(DATE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a list of dates as string
     *
     * @throws ParseException
     */
    @Test
    public void testMinStringDateFromList() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_STR_DATES);
        function.run(parameters);
        assertEquals(STR_DATE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a JSON array of dates as string
     *
     * @throws ParseException
     */
    @Test
    public void testMinStringDateFromJsonArray() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_ARRAY_STR_DATES);
        function.run(parameters);
        assertEquals(STR_DATE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for a single date as string returns the same object
     *
     * @throws ParseException
     */
    @Test
    public void testMinOfSingleElementStringDate() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STR_DATE_1);
        function.run(parameters);
        assertEquals(STR_DATE_1, parameters.pop());
    }

    /**
     * Tests the minimum element for list of different types
     *
     * @throws ParseException
     */
    @Test
    public void testMinElementForListOfDifferentTypes() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(LIST_DATE_AND_NUMBER);
        exception.expect(IllegalArgumentException.class);
        function.run(parameters);
    }
}
