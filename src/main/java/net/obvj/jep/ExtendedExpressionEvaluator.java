package net.obvj.jep;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.*;

import net.obvj.jep.util.RegexUtils;

/**
 * An object that evaluates an user expression with extended capabilities, such as
 * place-holders translation before expression evaluation.
 *
 * @author oswaldo.bapvic.jr
 */
public class ExtendedExpressionEvaluator
{
    private static final boolean DEFAULT_UPDATE_SOURCE_MAP_FLAG = false;

    private final String expression;

    /**
     * Builds this extended expression evaluator
     *
     * @param expression the expression to be used for evaluation
     */
    public ExtendedExpressionEvaluator(String expression)
    {
        if (StringUtils.isBlank(expression))
        {
            throw new IllegalArgumentException("The expression cannot be empty");
        }
        validateExpression(expression);
        this.expression = expression;
    }

    /**
     * Validates the given expression.
     *
     * @param expression the expression to be validated
     */
    public static void validateExpression(String expression)
    {
        try
        {
            JEPContextFactory.newContext().parse(expression);
        }
        catch (ParseException | TokenMgrError exception)
        {
            throw new IllegalArgumentException("Invalid expression: " + expression, exception);
        }
    }

    /**
     * Evaluates the instance-defined expression with a map of variables.
     *
     * @param variables the map of variables to be used for the evaluation
     * @return the expression evaluation result given the input variables
     * @throws ParseException if for some reason the expression could not be evaluated
     */
    public Object evaluate(Map<String, Object> variables) throws ParseException
    {
        return evaluate(variables, DEFAULT_UPDATE_SOURCE_MAP_FLAG);
    }

    /**
     * Evaluates the instance-defined expression with a map of variables.
     *
     * @param variables the map of variables to be used for the evaluation
     * @param updateSourceMap a flag indicating whether or not the initial variables map shall
     * be updated with the evaluation results (default: false)
     * @return the expression evaluation result given the input variables
     * @throws ParseException if for some reason the expression could not be evaluated
     */
    public Object evaluate(Map<String, Object> variables, boolean updateSourceMap) throws ParseException
    {
        JEP evaluationContext = JEPContextFactory.newContext(variables);
        String localExpression = replacePlaceholders(variables);

        Node node = evaluationContext.parseExpression(localExpression);
        Object result = evaluationContext.evaluate(node);

        if (updateSourceMap) updateExternalMap(variables, evaluationContext.getSymbolTable());

        return result;
    }

    private String replacePlaceholders(Map<String, Object> variables)
    {
        if (RegexUtils.hasUnixLikeVariablePlaceholders(expression))
        {
            return RegexUtils.replacePlaceholdersWithVariables(expression, variables);
        }
        return expression;
    }

    private void updateExternalMap(Map<String, Object> map, SymbolTable internalTable)
    {
        internalTable.forEach((key, value) -> map.put((String) key, ((Variable) value).getValue()));
    }

}
