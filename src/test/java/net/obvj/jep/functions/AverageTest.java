package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Average} function
 *
 * @author oswaldo.bapvic.jr
 */
public class AverageTest
{
    private static final Double DOUBLE_1 = Double.valueOf(1);
    private static final Double DOUBLE_2 = Double.valueOf(2);
    private static final Double DOUBLE_3 = Double.valueOf(3);
    private static final Double[] ARRAY_DOUBLES = new Double[] { DOUBLE_1, DOUBLE_2, DOUBLE_3 };
    private static final List<Double> LIST_DOUBLES = Arrays.asList(DOUBLE_1, DOUBLE_2, DOUBLE_3);
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
    private static final List<?> LIST_DATE_AND_NUMBER = Arrays.asList(STR_DATE_1, DOUBLE_1);

    private static Average function = new Average();

    /**
     * Tests the average for an array of doubles
     */
    @Test
    public void testAverageFromArrayOfDoubles() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the average for a list of doubles
     */
    @Test
    public void testAverageFromListOfDoubles() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(LIST_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the average for a JSON array
     */
    @Test
    public void testAverageFromJsonArrayOfDoubles() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the average for an array of valid numbers as string
     */
    @Test
    public void testAverageFromArrayOfNumbersAsStrings() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the average for a list of valid numbers as string
     */
    @Test
    public void testAverageFromListOfNumbersAsStrings() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(LIST_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the average for a JSON array of valid numbers as string
     */
    @Test
    public void testAverageFromJsonArrayOfValidNumbersAsStrings() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the average for a string representation of JSON array of valid numbers as
     * string
     */
    @Test
    public void testAverageFromStringRepresentationOfJsonArrayContainingNumbersAsStrings() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_JSON_ARRAY_STRING_NUMBERS);
        function.run(parameters);
        assertEquals(DOUBLE_2, parameters.pop());
    }

    /**
     * Tests the average for a null object
     */
    @Test
    public void testAverageOfNullObject() throws ParseException
    {
        Object nullObject = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(nullObject);
        function.run(parameters);
        assertEquals(Double.NaN, parameters.pop());
    }

    /**
     * Tests the average for an empty string
     */
    @Test(expected=NumberFormatException.class)
    public void testAverageOfEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(StringUtils.EMPTY);
        function.run(parameters);
    }

    /**
     * Tests the average for a single number returns the same object
     */
    @Test
    public void testAverageOfSingleElementDouble() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DOUBLE_1);
        function.run(parameters);
        assertEquals(DOUBLE_1, parameters.pop());
    }

    /**
     * Tests the average for a single number as string returns the same object
     */
    @Test
    public void testAverageOfSingleElementStringNumber() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_3);
        function.run(parameters);
        assertEquals(DOUBLE_3, parameters.pop());
    }

    /**
     * Tests the average for list of different types
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMaxElementForListOfDifferentTypes() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(LIST_DATE_AND_NUMBER);
        function.run(parameters);
    }
}
