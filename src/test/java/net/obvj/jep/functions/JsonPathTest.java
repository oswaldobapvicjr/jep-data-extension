package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link JsonPath} function.
 *
 * @author oswaldo.bapvic.jr
 */
public class JsonPathTest
{
    private static final String STRING_MOBILE_NUMBER = "+55 11 98765-4321";
    private static final String STRING_HOME_NUMBER = "+55 11 2345-6789";
    private static final List<String> ALL_NUMBERS = Arrays.asList(STRING_MOBILE_NUMBER, STRING_HOME_NUMBER);

    private static final String JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER = "$.phoneNumbers[?(@.type=='mobile')].number";
    private static final String JSON_PATH_SEARCH_PHONE_NUMBERS = "$.phoneNumbers..number";
    private static final String JSON_PATH_UNKNOWN_FIELD = "$.birthDate";
    private static final String JSON_PATH_NO_DATA = "$..birthDate";
    private static final String JSON_PATH_INVALID = "$.phoneNumber[0*@#.type";

    private static final String JSON_VALID ="{\r\n" +
            "  \"firstName\": \"John\",\r\n" +
            "  \"lastName\" : \"Doe\",\r\n" +
            "  \"age\" : 26,\r\n" +
            "  \"address\" : {\r\n" +
            "    \"streetAddress\": \"Avenida Paulista\",\r\n" +
            "    \"city\" : \"Sao Paulo\",\r\n" +
            "    \"postalCode\" : \"01234-567\"\r\n" +
            "  },\r\n" +
            "  \"phoneNumbers\": [\r\n" +
            "    {\r\n" +
            "      \"type\" : \"mobile\",\r\n" +
            "      \"number\": \"" + STRING_MOBILE_NUMBER + "\"\r\n" +
            "    },\r\n" +
            "    {\r\n" +
            "      \"type\" : \"home\",\r\n" +
            "      \"number\": \"" + STRING_HOME_NUMBER + "\"\r\n" +
            "    }\r\n" +
            "  ]\r\n" +
            "}";

    private static final String JSON_INVALID = "{ a :";

    private static JsonPath function = new JsonPath();

    /**
     * Tests a valid search expression for JSONPath command returning a single result
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithValidSearchExpressionSingleResult() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_VALID, JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
        assertEquals(STRING_MOBILE_NUMBER, parameters.pop());
    }

    /**
     * Tests a valid search expression for JSONPath command returning more than one result
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithValidSearchExpressionSeveralResults() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_VALID, JSON_PATH_SEARCH_PHONE_NUMBERS);
        function.run(parameters);
        List<Object> result = CollectionsUtils.asList(parameters.pop());
        assertEquals(2, result.size());
        assertTrue("Not all expected numbers were returned", result.containsAll(ALL_NUMBERS));
    }

    /**
     * Tests a valid expression for JSONPath command with empty result
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithValidSearchExpressionAndEmptyResult() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_VALID, JSON_PATH_UNKNOWN_FIELD);
        function.run(parameters);
    }

    /**
     * Tests a valid expression for JSONPath command with empty result
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithValidExpressionAndEmptyResult() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_VALID, JSON_PATH_NO_DATA);
        function.run(parameters);
    }

    /**
     * Tests a invalid expression for JSONPath command
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithInvalidExpression() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_VALID, JSON_PATH_INVALID);
        function.run(parameters);
    }

    /**
     * Tests a empty expression for JSONPath command
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithEmptyExpression() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_VALID, "");
        function.run(parameters);
    }

    /**
     * Tests a null expression for JSONPath command
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithNullExpression() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_VALID, null);
        function.run(parameters);
    }

    /**
     * Tests a empty JSON for JSONPath command
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithEmptyJson() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(StringUtils.EMPTY,
                JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
    }

    /**
     * Tests a null JSON for JSONPath command
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithNullJson() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
    }

    /**
     * Tests a invalid JSON for JSONPath command
     *
     * @throws ParseException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testJSONPathCommandWithInvalidJson() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(JSON_INVALID, JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
    }

}
