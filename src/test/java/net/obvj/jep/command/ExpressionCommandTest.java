package net.obvj.jep.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Test;

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

    // Test values
    private static final String STRING_BLANK = " ";
    private static final String MORNING = "morning";
    private static final String AFTERNOON = "afternoon";

    // Expressions
    private static final String EXPRESSION_CONCAT_GOOD_PERIOD = "concat(\"Good \", period)";
    private static final String EXPRESSION_CONCAT_INVALID = "concat('Good ', period";
    private static final String EXPRESSION_GOOD_PLUS_PERIOD = "\"Good \" + period";

    // Expected results
    private static final String GOOD_MORNING = "Good morning";
    private static final String GOOD_AFTERNOON = "Good afternoon";

    /**
     * Tests that the component is not created with a null expression
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfExpressionIsNull()
    {
        ExpressionCommand evaluator = new ExpressionCommand(VARIABLE_RESULT, null);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a blank expression
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfExpressionIsBlank()
    {
        ExpressionCommand evaluator = new ExpressionCommand(VARIABLE_RESULT, STRING_BLANK);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with an invalid expression
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfExpressionIsInvalid()
    {
        ExpressionCommand evaluator = new ExpressionCommand(VARIABLE_RESULT, EXPRESSION_CONCAT_INVALID);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a null target variable name
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfTargetIsNull()
    {
        ExpressionCommand evaluator = new ExpressionCommand(null, EXPRESSION_CONCAT_GOOD_PERIOD);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a blank target name
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentNotCreatedIfTargetIsBlank()
    {
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
    @Test(expected = IllegalArgumentException.class)
    public void testComponentExecutionWithSourceVariableNotFoundAndDefaultBehavior()
    {
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
