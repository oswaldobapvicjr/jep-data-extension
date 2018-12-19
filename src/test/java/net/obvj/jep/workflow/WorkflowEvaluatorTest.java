package net.obvj.jep.workflow;

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
 * Unit tests for the {@link WorkflowEvaluator} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class WorkflowEvaluatorTest
{
    // Test variable names
    private static final String VARIABlE_PERIOD = "period";
    private static final String VARIABLE_RESULT = "result";

    // Test values
    private static final String MORNING = "morning";
    private static final String AFTERNOON = "afternoon";

    // Expressions
    private static final String EXPRESSION_CONCAT_GOOD_PERIOD_SINGLE_QUOTES = "concat('Good ', period)";
    private static final String EXPRESSION_CONCAT_GOOD_PERIOD_DOUBLE_QUOTES = "concat(\"Good \", period)";
    private static final String EXPRESSION_CONCAT_INVALID = "concat('Good ', period";
    private static final String EXPRESSION_GOOD_PLUS_PERIOD_SINGLE_QUOTES = "'Good ' + period";
    private static final String EXPRESSION_GOOD_PLUS_PERIOD_DOUBLE_QUOTES = "\"Good \" + period";

    // Expected results
    private static final String GOOD_MORNING = "Good morning";
    private static final String GOOD_AFTERNOON = "Good afternoon";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests that the component is not created with a null expression
     */
    @Test
    public void testComponentNotCreatedIfExpressionIsNull()
    {
        exception.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(VARIABLE_RESULT, null);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a blank expression
     */
    @Test
    public void testComponentNotCreatedIfExpressionIsBlank()
    {
        exception.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(VARIABLE_RESULT, " ");
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with an invalid expression
     */
    @Test
    public void testComponentNotCreatedIfExpressionIsInvalid()
    {
        exception.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(VARIABLE_RESULT, EXPRESSION_CONCAT_INVALID);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a null target variable name
     */
    @Test
    public void testComponentNotCreatedIfTargetIsNull()
    {
        exception.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(null, EXPRESSION_CONCAT_GOOD_PERIOD_SINGLE_QUOTES);
        assertNull(evaluator);
    }

    /**
     * Tests that the component is not created with a blank target name
     */
    @Test
    public void testComponentNotCreatedIfTargetIsBlank()
    {
        exception.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(" ", EXPRESSION_CONCAT_GOOD_PERIOD_SINGLE_QUOTES);
        assertNull(evaluator);
    }

    /**
     * Tests component execution with the concat() function and a string containing literals
     * delimited by single quotes
     */
    @Test
    public void testComponentExecutionWithConcatExpressionAndSingleQuotes()
    {
        Consumer<Map<String, Object>> evaluator = new WorkflowEvaluator(VARIABLE_RESULT,
                EXPRESSION_CONCAT_GOOD_PERIOD_SINGLE_QUOTES);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABlE_PERIOD, MORNING);
        evaluator.accept(map);
        assertEquals(GOOD_MORNING, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with the concat operator and a string containing literals
     * delimited by single quotes
     */
    @Test
    public void testComponentExecutionWithConcatOperatorAndSingleQuotes()
    {
        Consumer<Map<String, Object>> evaluator = new WorkflowEvaluator(VARIABLE_RESULT,
                EXPRESSION_GOOD_PLUS_PERIOD_SINGLE_QUOTES);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABlE_PERIOD, AFTERNOON);
        evaluator.accept(map);
        assertEquals(GOOD_AFTERNOON, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with the concat() function and a string containing literals
     * delimited by double quotes
     */
    @Test
    public void testComponentExecutionWithConcatExpressionAndDoubleQuotes()
    {
        Consumer<Map<String, Object>> evaluator = new WorkflowEvaluator(VARIABLE_RESULT,
                EXPRESSION_CONCAT_GOOD_PERIOD_DOUBLE_QUOTES);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABlE_PERIOD, MORNING);
        evaluator.accept(map);
        assertEquals(GOOD_MORNING, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests component execution with the concat operator and a string containing literals
     * delimited by double quotes
     */
    @Test
    public void testComponentExecutionWithConcatOperatorAndDoubleQuotes()
    {
        Consumer<Map<String, Object>> evaluator = new WorkflowEvaluator(VARIABLE_RESULT,
                EXPRESSION_GOOD_PLUS_PERIOD_DOUBLE_QUOTES);
        Map<String, Object> map = new HashMap<>();
        map.put(VARIABlE_PERIOD, AFTERNOON);
        evaluator.accept(map);
        assertEquals(GOOD_AFTERNOON, map.get(VARIABLE_RESULT));
    }

    /**
     * Tests the component reused several times with different variable map
     */
    @Test
    public void testSubsequentCallsToTheSameComponentWithDifferentVariables()
    {
        Consumer<Map<String, Object>> evaluator = new WorkflowEvaluator(VARIABLE_RESULT,
                EXPRESSION_GOOD_PLUS_PERIOD_SINGLE_QUOTES);
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put(VARIABlE_PERIOD, MORNING);
        map2.put(VARIABlE_PERIOD, AFTERNOON);
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
        Consumer<Map<String, Object>> evaluator = new WorkflowEvaluator(VARIABLE_RESULT,
                EXPRESSION_GOOD_PLUS_PERIOD_DOUBLE_QUOTES);
        evaluator.accept(Collections.emptyMap());
    }

    /**
     * Tests component execution with an expression that evaluates a variable not found and
     * ignore errors option
     */
    @Test
    public void testComponentExecutionWithSourceVariableNotFoundAndIgnoreErrors()
    {
        Consumer<Map<String, Object>> evaluator = new WorkflowEvaluator(VARIABLE_RESULT,
                EXPRESSION_GOOD_PLUS_PERIOD_DOUBLE_QUOTES, true);
        Map<String, Object> map = new HashMap<>();
        evaluator.accept(map);
        assertTrue("Map should be updated with the result variable", map.containsKey(VARIABLE_RESULT));
        assertNull("The new variable should be null", map.get(VARIABLE_RESULT));
    }
}
