package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.JsonPath;
import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link JsonPath} function.
 *
 * @author oswaldo.bapvic.jr
 */
public class JsonPathTest
{
    private static final String STRING_MOBILE_NUMBER = "+55 11 98765-4321";

    private static final String JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER = "$.phoneNumbers[?(@.type=='mobile')].number";
    private static final String JSON_PATH_UNKNOWN_FIELD = "$..birthDate";
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
            "      \"number\": \"+55 11 2345-6789\"\r\n" +
            "    }\r\n" +
            "  ]\r\n" +
            "}";

    private static final String JSON_INVALID = "{ a :";

    private static JsonPath function = new JsonPath();

    /**
     * Expected exception
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests a valid expression for JSONPath command
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithValidExpression() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_VALID, JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
        assertEquals(STRING_MOBILE_NUMBER, parameters.pop());
    }

    /**
     * Tests a valid expression for JSONPath command with empty result
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithValidExpressionAndEmptyResult() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_VALID, JSON_PATH_UNKNOWN_FIELD);
        function.run(parameters);
    }

    /**
     * Tests a invalid expression for JSONPath command
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithInvalidExpression() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_VALID, JSON_PATH_INVALID);
        function.run(parameters);
    }

    /**
     * Tests a empty expression for JSONPath command
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithEmptyExpression() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_VALID, "");
        function.run(parameters);
    }

    /**
     * Tests a null expression for JSONPath command
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithNullExpression() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_VALID, null);
        function.run(parameters);
    }

    /**
     * Tests a empty JSON for JSONPath command
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithEmptyJson() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(StringUtils.EMPTY,
                JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
    }

    /**
     * Tests a null JSON for JSONPath command
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithNullJson() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(null, JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
    }

    /**
     * Tests a invalid JSON for JSONPath command
     *
     * @throws ParseException
     */
    @Test
    public void testJSONPathCommandWithInvalidJson() throws ParseException
    {
        exception.expect(IllegalArgumentException.class);
        Stack<Object> parameters = StackUtils.newParametersStack(JSON_INVALID, JSON_PATH_SEARCH_MOBILE_PHONE_NUMBER);
        function.run(parameters);
    }

}
