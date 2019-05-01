package net.obvj.jep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nfunk.jep.ParseException;

/**
 * Unit tests for the {@link ExtendedExpressionEvaluator} class.
 *
 * @author oswaldo.bapvic.jr
 */
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
    private static final String EXPRESSION_GOOD_PLUS_PERIOD_PLACEHOLDER_STRING = "\"Good \" + \"${period}\"";
    private static final String EXPRESSION_BOOKS_MORE_EXPENSIVE_THAN_PLACEHOLDER = "jsonpath(storeJson, \"$..book[?(@.price>${minPrice})].title\")";
    private static final String EXPRESSION_BOOKS_FROM_AUTHOR_PLACEHOLDER = "jsonpath(storeJson, \"$..book[?(@.author=='${author}')].title\")";

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
     * Tests component execution with the concat operator and a string containing literals and
     * a place-holder inside strings delimiter
     *
     * @throws ParseException
     */
    @Test
    public void testComponentExecutionWithConcatOperatorAndPlaceholderString() throws ParseException
    {
        ExtendedExpressionEvaluator evaluator = new ExtendedExpressionEvaluator(
                EXPRESSION_GOOD_PLUS_PERIOD_PLACEHOLDER_STRING);
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

}
