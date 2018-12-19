package net.obvj.jep.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WorkflowExpressionEvaluatorTest
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
    public ExpectedException _expectedException = ExpectedException.none();

    @Test
    public void testComponentNotCreatedIfExpressionIsNull()
    {
        _expectedException.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(VARIABLE_RESULT, null);
        assertNull(evaluator);
    }

    @Test
    public void testComponentNotCreatedIfExpressionIsBlank()
    {
        _expectedException.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(VARIABLE_RESULT, " ");
        assertNull(evaluator);
    }

    @Test
    public void testComponentNotCreatedIfExpressionIsInvalid()
    {
        _expectedException.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(VARIABLE_RESULT, EXPRESSION_CONCAT_INVALID);
        assertNull(evaluator);
    }

    @Test
    public void testComponentNotCreatedIfTargetIsNull()
    {
        _expectedException.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(null, EXPRESSION_CONCAT_GOOD_PERIOD_SINGLE_QUOTES);
        assertNull(evaluator);
    }

    @Test
    public void testComponentNotCreatedIfTargetIsBlank()
    {
        _expectedException.expect(IllegalArgumentException.class);
        WorkflowEvaluator evaluator = new WorkflowEvaluator(" ", EXPRESSION_CONCAT_GOOD_PERIOD_SINGLE_QUOTES);
        assertNull(evaluator);
    }

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
}
