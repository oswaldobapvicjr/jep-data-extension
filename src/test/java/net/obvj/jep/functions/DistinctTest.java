package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Distinct} function
 *
 * @author oswaldo.bapvic.jr
 */
public class DistinctTest
{
    private static final Double DOUBLE_1 = Double.valueOf(1);
    private static final Double DOUBLE_2 = Double.valueOf(2);
    private static final Double DOUBLE_3 = Double.valueOf(3);
    private static final Double[] ARRAY_DOUBLES = new Double[] { DOUBLE_1, DOUBLE_2, DOUBLE_3, DOUBLE_1 };
    private static final List<Double> LIST_DOUBLES = Arrays.asList(ARRAY_DOUBLES);
    private static final JSONArray JSON_ARRAY_DOUBLES = new JSONArray(LIST_DOUBLES);

    private static final List<Double> LIST_DOUBLES_DISTINCT = Arrays.asList(DOUBLE_1, DOUBLE_2, DOUBLE_3);

    private static final String STRING_1 = "S1";
    private static final String STRING_2 = "S2";
    private static final String STRING_3 = "S3";
    private static final String[] ARRAY_STRINGS = new String[] { STRING_2, STRING_3, STRING_1, STRING_3 };
    private static final List<String> LIST_STRINGS = Arrays.asList(ARRAY_STRINGS);
    private static final JSONArray JSON_ARRAY_STRING = new JSONArray(LIST_STRINGS);
    private static final String STR_JSON_ARRAY_STRINGS = String.format("[\"%s\",\"%s\",\"%s\",\"%s\"]", STRING_2,
            STRING_3, STRING_1, STRING_3);

    private static final List<String> LIST_STRINGS_DISTINCT = Arrays.asList(STRING_2, STRING_3, STRING_1);

    private static Distinct function = new Distinct();

    /**
     * Tests the Distinct for an array of doubles
     */
    @Test
    public void testDistinctFromArrayOfDoubles() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(LIST_DOUBLES_DISTINCT, parameters.pop());
    }

    /**
     * Tests the Distinct for a list of doubles
     */
    @Test
    public void testDistinctFromListOfDoubles() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(LIST_DOUBLES);
        function.run(parameters);
        assertEquals(LIST_DOUBLES_DISTINCT, parameters.pop());
    }

    /**
     * Tests the Distinct for a JSON array
     */
    @Test
    public void testDistinctFromJsonArrayOfDoubles() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_ARRAY_DOUBLES);
        function.run(parameters);
        assertEquals(LIST_DOUBLES_DISTINCT, parameters.pop());
    }

    /**
     * Tests the Distinct for an array of valid strings
     */
    @Test
    public void testDistinctFromArrayOfStrings() throws ParseException
    {
        Stack<Object> parameters = new Stack<>();
        parameters.push(ARRAY_STRINGS);
        function.run(parameters);
        assertEquals(LIST_STRINGS_DISTINCT, parameters.pop());
    }

    /**
     * Tests the Distinct for a list of valid strings
     */
    @Test
    public void testDistinctFromListOfStrings() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(LIST_STRINGS);
        function.run(parameters);
        assertEquals(LIST_STRINGS_DISTINCT, parameters.pop());
    }

    /**
     * Tests the Distinct for a JSON array of valid strings
     */
    @Test
    public void testDistinctFromJsonArrayOfValidStrings() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_ARRAY_STRING);
        function.run(parameters);
        assertEquals(LIST_STRINGS_DISTINCT, parameters.pop());
    }

    /**
     * Tests the Distinct for a string representation of JSON array of valid strings
     */
    @Test
    public void testDistinctFromStringRepresentationOfJsonArrayContainingNumbersAsStrings() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_JSON_ARRAY_STRINGS);
        function.run(parameters);
        assertEquals(LIST_STRINGS_DISTINCT, parameters.pop());
    }

    /**
     * Tests the Distinct for a null object
     */
    @Test
    public void testDistinctOfNullObject() throws ParseException
    {
        Object nullObject = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(nullObject);
        function.run(parameters);
        assertEquals(Collections.emptyList(), parameters.pop());
    }

    /**
     * Tests the Distinct for an empty string
     */
    @Test
    public void testDistinctOfEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(StringUtils.EMPTY);
        function.run(parameters);
        assertEquals(Arrays.asList(StringUtils.EMPTY), parameters.pop());
    }

    /**
     * Tests the Distinct for a single number returns the same object
     */
    @Test
    public void testDistinctOfSingleElementDouble() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DOUBLE_1);
        function.run(parameters);
        assertEquals(Arrays.asList(DOUBLE_1), parameters.pop());
    }

    /**
     * Tests the Distinct for a single number as string returns the same object
     */
    @Test
    public void testDistinctOfSingleElementStringNumber() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_3);
        function.run(parameters);
        assertEquals(Arrays.asList(STRING_3), parameters.pop());
    }

}
