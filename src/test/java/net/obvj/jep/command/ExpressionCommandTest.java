package net.obvj.jep.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for the {@link ExpressionCommand} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class ExpressionCommandTest
{
    // Test variable names
    private static final String VARIABLE_PERIOD = "period";
    private static final String VARIABLE_RESULT = "result";
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


    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests that the component is not created with a null expression
     */
    @Test
    public void testComponentNotCreatedIfExpressionIsNull()
    {
        exception.expect(IllegalArgumentException.class);
        ExpressionCommand evaluator = new ExpressionCommand(VARIABLE_RESULT, null);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a blank expression
     */
    @Test
    public void testComponentNotCreatedIfExpressionIsBlank()
    {
        exception.expect(IllegalArgumentException.class);
        ExpressionCommand evaluator = new ExpressionCommand(VARIABLE_RESULT, STRING_BLANK);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with an invalid expression
     */
    @Test
    public void testComponentNotCreatedIfExpressionIsInvalid()
    {
        exception.expect(IllegalArgumentException.class);
        ExpressionCommand evaluator = new ExpressionCommand(VARIABLE_RESULT, EXPRESSION_CONCAT_INVALID);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a null target variable name
     */
    @Test
    public void testComponentNotCreatedIfTargetIsNull()
    {
        exception.expect(IllegalArgumentException.class);
        ExpressionCommand evaluator = new ExpressionCommand(null, EXPRESSION_CONCAT_GOOD_PERIOD);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a blank target name
     */
    @Test
    public void testComponentNotCreatedIfTargetIsBlank()
    {
        exception.expect(IllegalArgumentException.class);
        ExpressionCommand evaluator = new ExpressionCommand(STRING_BLANK, EXPRESSION_CONCAT_GOOD_PERIOD);
        assertNull(evaluator);
    }

    /**
     * Tests component execution with the concat() function and a string containing literals
     * delimited by double quotes
     */
    @Test
    public void testComponentExecutionWithConcatExpression()
    {
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT, EXPRESSION_CONCAT_GOOD_PERIOD);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_PERIOD, MORNING);
        evaluator.accept(map);
        assertEquals(GOOD_MORNING, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with the concat operator
     */
    @Test
    public void testComponentExecutionWithConcatOperator()
    {
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT, EXPRESSION_GOOD_PLUS_PERIOD);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_PERIOD, AFTERNOON);
        evaluator.accept(map);
        assertEquals(GOOD_AFTERNOON, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with the concat operator and a string containing literals and
     * a place-holder inside strings delimiter
     */
    @Test
    public void testComponentExecutionWithConcatOperatorAndPlaceholderString()
    {
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT,
                EXPRESSION_GOOD_PLUS_PERIOD_PLACEHOLDER_STRING);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_PERIOD, AFTERNOON);
        evaluator.accept(map);
        assertEquals(GOOD_AFTERNOON, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with the jsonpath() function and a JSONPath string containing
     * a placeholder not inside strings delimiter
     */
    @Test
    public void testComponentExecutionWithJsonPathAndPlaceholderNumber()
    {
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT,
                EXPRESSION_BOOKS_MORE_EXPENSIVE_THAN_PLACEHOLDER);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_STORE_JSON, JSON_STORE);
        map.put(VARIABLE_MIN_PRICE, MIN_PRICE_10);
        evaluator.accept(map);
        assertEquals(THE_LORD_OF_THE_RINGS, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with the jsonpath() function and a JSONPath string containing
     * a placeholder inside strings delimiter (single-quotes)
     */
    @Test
    public void testComponentExecutionWithJsonPathAndPlaceholderString()
    {
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT,
                EXPRESSION_BOOKS_FROM_AUTHOR_PLACEHOLDER);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABLE_STORE_JSON, JSON_STORE);
        map.put(VARIABLE_AUTHOR, J_R_R_TOLKIEN);
        evaluator.accept(map);
        assertEquals(THE_LORD_OF_THE_RINGS, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests the component reused several times with different variable map
     */
    @Test
    public void testSubsequentCallsToTheSameComponentWithDifferentVariables()
    {
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT, EXPRESSION_GOOD_PLUS_PERIOD);
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put(VARIABLE_PERIOD, MORNING);
        map2.put(VARIABLE_PERIOD, AFTERNOON);
        evaluator.accept(map1);
        evaluator.accept(map2);
        assertEquals(GOOD_MORNING, map1.get(VARIABLE_RESULT));
        assertEquals(GOOD_AFTERNOON, map2.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with an expression that evaluates a variable not found and
     * the default error behavior
     */
    @Test
    public void testComponentExecutionWithSourceVariableNotFoundAndDefaultBehavior()
    {
        exception.expect(IllegalArgumentException.class);
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT, EXPRESSION_GOOD_PLUS_PERIOD);
        evaluator.accept(Collections.emptyMap());
    }

    /**
     * Tests component execution with an expression that evaluates a variable not found and
     * ignore errors option
     */
    @Test
    public void testComponentExecutionWithSourceVariableNotFoundAndIgnoreErrors()
    {
        Consumer<Map<String, Object>> evaluator = new ExpressionCommand(VARIABLE_RESULT, EXPRESSION_GOOD_PLUS_PERIOD,
                true);
        Map<String, Object> map = new HashMap<>();
        evaluator.accept(map);
        assertTrue("Map should be updated with the result variable", map.containsKey(VARIABLE_RESULT));
        assertNull("The new variable should be null", map.get(VARIABLE_RESULT));
    }
}
