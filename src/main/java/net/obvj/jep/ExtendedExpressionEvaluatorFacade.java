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
        throw new IllegalStateException("Utility class");
    }

    /**
     * Evaluates the given {@code expression} based on the variables in the
     * {@code contextMap}.
     *
     * @param expression the expression to be parsed
     * @param variables  the map of variables to be used for evaluation
     * @return the evaluation result given the input variables
     * @throws ParseException if for some reason the expression could not be evaluated
     */
    public static Object evaluate(String expression, Map<String, Object> contextMap) throws ParseException
    {
        return new ExtendedExpressionEvaluator(expression).evaluate(contextMap);
    }

}
