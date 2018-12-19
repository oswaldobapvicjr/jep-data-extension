package net.obvj.jep;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.TokenMgrError;

import net.obvj.jep.util.PlaceholderUtils;

/**
 * An object that evaluates an user expression with extended capabilities, such as
 * place-holders translation before expression evaluation.
 *
 * @author oswaldo.bapvic.jr
 */
public class ExtendedExpressionEvaluator
{
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
     * @throws ParseException if for some reason the expression could not be evaluated
     */
    public Object evaluate(Map<String, Object> variables) throws ParseException
    {
        JEP evaluationContext = JEPContextFactory.newContext(variables);
        String localExpression = replacePlaceholders(variables);

        Node node = evaluationContext.parseExpression(localExpression);
        return evaluationContext.evaluate(node);
    }

    private String replacePlaceholders(Map<String, Object> variables)
    {
        if (PlaceholderUtils.hasPlaceholders(expression))
        {
            return PlaceholderUtils.replacePlaceholders(expression, variables);
        }
        return expression;
    }

}
