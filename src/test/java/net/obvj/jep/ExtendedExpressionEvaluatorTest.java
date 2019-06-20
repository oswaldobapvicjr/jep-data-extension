package net.obvj.jep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.nfunk.jep.ParseException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import net.obvj.jep.util.JsonUtils;

/**
 * Unit tests for the {@link ExtendedExpressionEvaluator} class.
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Client.class)
public class ExtendedExpressionEvaluatorTest
{
    // Test variable names
    private static final String VARIABLE_PERIOD = "period";
    private static final String VARIABLE_STORE_JSON = "storeJson";
    private static final String VARIABLE_AUTHOR = "author";
    private static final String VARIABLE_MIN_PRICE = "minPrice";

    // Test values
    private static final String STRING_BLANK = " ";
    private static final String MORNING = "morning";
    private static final String AFTERNOON = "afternoon";
    private static final int MIN_PRICE_10 = 10;
    private static final String THE_LORD_OF_THE_RINGS = "The Lord of the Rings";
    private static final String J_R_R_TOLKIEN = "J. R. R. Tolkien";

    // Expressions
    private static final String EXPRESSION_CONCAT_GOOD_PERIOD = "concat(\"Good \", period)";
    private static final String EXPRESSION_CONCAT_INVALID = "concat('Good ', period";
    private static final String EXPRESSION_GOOD_PLUS_PERIOD = "\"Good \" + period";
    private static final String EXPRESSION_BOOKS_MORE_EXPENSIVE_THAN_PLACEHOLDER = "jsonpath(storeJson, formatString(\"$..book[?(@.price>%d)].title\", minPrice))";
    private static final String EXPRESSION_BOOKS_FROM_AUTHOR_PLACEHOLDER = "jsonpath(storeJson, formatString(\"$..book[?(@.author=='%s')].title\", author))";

    // Expected results
    private static final String GOOD_MORNING = "Good morning";
    private static final String GOOD_AFTERNOON = "Good afternoon";

    // Test JSON
    private static final String JSON_STORE = "{\r\n" +
            "   \"store\" : {\r\n" +
            "      \"book\" : [\r\n" +
            "         {\r\n" +
            "            \"author\" : \"Herman Melville\",\r\n" +
            "            \"title\" : \"Moby Dick\",\r\n" +
            "            \"price\" : 8.99\r\n" +
            "         },\r\n" +
            "         {\r\n" +
            "            \"author\" : \"J. R. R. Tolkien\",\r\n" +
            "            \"title\" : \"The Lord of the Rings\",\r\n" +
            "            \"price\" : 22.99\r\n" +
            "         }\r\n" +
            "      ]\r\n" +
            "   }\r\n" +
            "}";

    // ----------------------
    // Web Services test data
    // ----------------------

    private static final String URL_EMPLOYEES = "http://localhost/services/employees";
    private static final String URL_EMPLOYEE1 = "http://localhost/services/employee/1";

    private static final String JSON_EMPLOYEES = "[\r\n" +
            "   {\"id\":1,\"name\":\"Matthew\"},\r\n" +
            "   {\"id\":2,\"name\":\"Mark\"},\r\n" +
            "   {\"id\":3,\"name\":\"Luke\"},\r\n" +
            "   {\"id\":4,\"name\":\"John\"}\r\n" +
            "]";

    private static final String JSON_EMPLOYEE1 = "{\"id\":1,\"name\":\"Matthew\"}";
    private static final String JSON_EMPLOYEE1_NAME = "Matthew";

    // Expressions with Web Services
    private static final String EXPRESSION_COUNT_HTTP_GET = "count(httpGet(\"http://localhost/services/employees\"))";
    private static final String EXPRESSION_GET_HTTP_GET = "get(httpGet(\"http://localhost/services/employees\", \"application/json\"), 1)";
    private static final String EXPRESSION_JSONPATH_HTTP_GET = "jsonpath(httpGet(\"http://localhost/services/employee/1\"), \"$.name\")";
    private static final String EXPRESSION_JSONPATH_HTTP_GET_WITH_PLACEHOLDER = "jsonpath(httpGet(formatString(\"http://localhost/services/employee/%s\", myId)), \"$.name\")";

    @Mock
    private ClientResponse clientResponse;

    @Mock
    private WebResource webResource;

    @Mock
    private Client client;

    /**
     * Utility method to mock the response from an HTTP request
     *
     * @param url the URL to be passed to the mock
     * @param mediaType the media type to be set
     * @param expectedResponse the content to be set
     */
    private void mockGetWebServiceResponse(String url, MediaType mediaType, String expectedResponse)
    {
        PowerMockito.mockStatic(Client.class);
        PowerMockito.when(Client.create()).thenReturn(client);

        when(client.resource(url)).thenReturn(webResource);
        when(webResource.get(ClientResponse.class)).thenReturn(clientResponse);
        when(clientResponse.getClientResponseStatus()).thenReturn(Status.OK);
        when(clientResponse.getType()).thenReturn(mediaType);
        when(clientResponse.getEntity(String.class)).thenReturn(expectedResponse);
    }

    /**
     * Tests that the component is not created with a null expression
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfExpressionIsNull()
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(null);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a blank expression
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfExpressionIsBlank()
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(STRING_BLANK);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with an invalid expression
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfExpressionIsInvalid()
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_CONCAT_INVALID);
        assertNull(evaluator);
    }

    /**
     * Tests component execution with the concat() function and a string containing literals
     * delimited by double quotes
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithConcatExpression() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_CONCAT_GOOD_PERIOD);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_PERIOD, MORNING);
        assertEquals(GOOD_MORNING, evaluator.evaluate(map));
    }

    /**
     * Tests component execution with the concat() function, a string containing literals
     * delimited by double quotes and the assignment operator. The variables map shall be
     * updated
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithConcatExpressionAndAssignmentOperator() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator("message = concat(\"Good \", period)");
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_PERIOD, MORNING);
        evaluator.evaluate(map, true);
        assertEquals(2, map.size());
        assertEquals(GOOD_MORNING, map.get("message"));
    }

    /**
     * Tests component execution with the concat operator
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithConcatOperator() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_GOOD_PLUS_PERIOD);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_PERIOD, AFTERNOON);
        assertEquals(GOOD_AFTERNOON, evaluator.evaluate(map));
    }

    /**
     * Tests component execution with the jsonpath() function and a JSONPath string containing
     * a placeholder not inside strings delimiter
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithJsonPathAndPlaceholderNumber() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(
                EXPRESSION_BOOKS_MORE_EXPENSIVE_THAN_PLACEHOLDER);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_STORE_JSON, JSON_STORE);
        map.put(VARIABLE_MIN_PRICE, MIN_PRICE_10);
        assertEquals(THE_LORD_OF_THE_RINGS, evaluator.evaluate(map));
    }

    /**
     * Tests component execution with the jsonpath() function and a JSONPath string containing
     * a placeholder inside strings delimiter (single-quotes)
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithJsonPathAndPlaceholderString() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(
                EXPRESSION_BOOKS_FROM_AUTHOR_PLACEHOLDER);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_STORE_JSON, JSON_STORE);
        map.put(VARIABLE_AUTHOR, J_R_R_TOLKIEN);
        assertEquals(THE_LORD_OF_THE_RINGS, evaluator.evaluate(map));
    }

    /**
     * Tests the component reused several times with different variable map
     *
     * @throws ParseException
     */
    @Test
    public void testSubsequentCallsToTheSameComponentWithDifferentVariables() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_GOOD_PLUS_PERIOD);
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put(VARIABLE_PERIOD, MORNING);
        map2.put(VARIABLE_PERIOD, AFTERNOON);
        assertEquals(GOOD_MORNING, evaluator.evaluate(map1));
        assertEquals(GOOD_AFTERNOON, evaluator.evaluate(map2));
    }

    /**
     * Tests component execution with an expression that evaluates a variable not found
     *
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void testComponentExecutionWithSourceVariableNotFound() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_GOOD_PLUS_PERIOD);
        evaluator.evaluate(Collections.emptyMap());
    }

    /**
     * Tests component execution with the count() and httpGet() functions nested
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithCountAndHttpGet() throws ParseException
    {
        mockGetWebServiceResponse(URL_EMPLOYEES, MediaType.APPLICATION_JSON_TYPE, JSON_EMPLOYEES);
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_COUNT_HTTP_GET);
        assertEquals(4, evaluator.evaluate(Collections.emptyMap()));
    }

    /**
     * Tests component execution with the get() and httpGet() functions nested
     *
     * @throws ParseException
     * @throws JSONException
     */
    @Test
    public void testComponentExecutionWithGetAndHttpGet() throws ParseException, JSONException
    {
        mockGetWebServiceResponse(URL_EMPLOYEES, MediaType.APPLICATION_JSON_TYPE, JSON_EMPLOYEES);
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_GET_HTTP_GET);
        assertEquals(JsonUtils.convertToJSONObject(JSON_EMPLOYEE1), evaluator.evaluate(Collections.emptyMap()));
    }

    /**
     * Tests component execution with the jsonpath() and httpGet() functions nested
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithJsonpathAndHttpGet() throws ParseException
    {
        mockGetWebServiceResponse(URL_EMPLOYEE1, MediaType.APPLICATION_JSON_TYPE, JSON_EMPLOYEE1);
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(EXPRESSION_JSONPATH_HTTP_GET);
        assertEquals(JSON_EMPLOYEE1_NAME, evaluator.evaluate(Collections.emptyMap()));
    }

    /**
     * Tests component execution with the jsonpath() and httpGet() functions nested, and a
     * placeholder in the URL
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithJsonpathAndHttpGetAndPlaceholderInUrl() throws ParseException
    {
        mockGetWebServiceResponse(URL_EMPLOYEE1, MediaType.APPLICATION_JSON_TYPE, JSON_EMPLOYEE1);
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(
                EXPRESSION_JSONPATH_HTTP_GET_WITH_PLACEHOLDER);

        Map<String, Object> map = new HashMap<>();
        map.put("myId", 1);
        assertEquals(JSON_EMPLOYEE1_NAME, evaluator.evaluate(map));

        // Verify that the URL without place-holders was passed to the mocked HTTP client
        verify(client, times(1)).resource(URL_EMPLOYEE1);
    }

}
