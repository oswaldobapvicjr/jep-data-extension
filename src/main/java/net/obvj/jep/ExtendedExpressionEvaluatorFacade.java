package net.obvj.jep;

import java.util.Map;

import org.nfunk.jep.ParseException;

/**
 * A facade class for the {@link ExtendedExpressionEvaluator}
 *
 * @author oswaldo.bapvic.jr
 */
public class ExtendedExpressionEvaluatorFacade
{
    private ExtendedExpressionEvaluatorFacade()
    {
        throw new IllegalStateException("No instances allowed");
    }

    /**
     * Evaluates the given {@code expression} based on the variables in the
     * {@code contextMap}.
     *
     * @param expression the expression to be parsed
     * @param sourceMap the map of variables to be used for evaluation
     * @return the evaluation result given the input variables
     * @throws ParseException if for some reason the expression could not be evaluated
     */
    public static Object evaluate(String expression, Map<String, Object> sourceMap) throws ParseException
    {
        return new ExtendedExpressionEvaluator(expression).evaluate(sourceMap);
    }

    /**
     * Evaluates the given {@code expression} based on the variables in the
     * {@code contextMap}.
     *
     * @param expression the expression to be parsed
     * @param sourceMap the map of variables to be used for evaluation
     * @param updateSourceMap a flag indicating whether or not the initial variables map shall
     * be updated with the evaluation results (default: false)
     * @return the evaluation result given the input variables
     * @throws ParseException if for some reason the expression could not be evaluated
     */
    public static Object evaluate(String expression, Map<String, Object> sourceMap, boolean updateSourceMap)
            throws ParseException
    {
        return new ExtendedExpressionEvaluator(expression).evaluate(sourceMap, updateSourceMap);
    }

}
