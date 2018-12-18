package net.obvj.jep;

import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.TokenMgrError;

/**
 * An object that evaluates an user expression and updates a context map with the
 * evaluation result.
 *
 * @author oswaldo.bapvic.jr
 */
public class ExtendedExpressionEvaluator implements Consumer<Map<String, Object>>
{
    private static final Pattern PATTERN_PART_REGEX = Pattern.compile("'([^']*[^,]*)?'");
    private static final String FIRST_GROUP = "\"$1\"";

    private final String targetVariableName;
    private final String expression;
    private final boolean ignoreErrors;

    /**
     * Builds this extended expression evaluator with default setup.
     *
     * @param expression         the expression to be used for evaluation
     * @param targetVariableName the new variable name to be populated by this evaluator with
     *                           the evaluation result
     */
    public ExtendedExpressionEvaluator(String targetVariableName, String expression)
    {
        this(targetVariableName, expression, false);
    }

    /**
     * Builds this extended expression evaluator
     *
     * @param expression         the expression to be used for evaluation
     * @param targetVariableName the new variable name to be populated by this evaluator with
     *                           the evaluation result
     * @param ignoreErrors       a flag indicating whether or not this evaluator should ignore
     *                           an errors. If set to {@code true}, in case of exceptions
     *                           during the evaluation, a new variable will be created with a
     *                           {@code null} value assigned to it. If this flag is set to
     *                           {@code false}, then a {@link ParseException} may be thrown.
     */
    public ExtendedExpressionEvaluator(String targetVariableName, String expression, boolean ignoreErrors)
    {
        if (StringUtils.isBlank(expression))
        {
            throw new IllegalArgumentException("The expression cannot be empty");
        }
        this.targetVariableName = targetVariableName;

        if (StringUtils.isBlank(targetVariableName))
        {
            throw new IllegalArgumentException("The target name cannot be empty");
        }
        this.expression = removeSpecialCharacters(expression);
        validateExpression(this.expression);

        this.ignoreErrors = ignoreErrors;
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

    private String removeSpecialCharacters(String expression)
    {
        return expression.replaceAll(PATTERN_PART_REGEX.pattern(), FIRST_GROUP);
    }

    /**
     * Evaluates the instance-defined expression with a map of variables passed to as
     * arguments. This map is updated with a new variable containing the evaluation result.
     *
     * @param variables the map of variables to be used for the evaluation and to be updated
     */
    @Override
    public void accept(Map<String, Object> variables)
    {
        JEP evaluationContext = JEPContextFactory.newContext(variables);
        String expression = replacePlaceholders(variables);

        Node node = evaluationContext.parseExpression(expression);
        try
        {
            Object value = evaluationContext.evaluate(node);
            update(variables, value);
        }
        catch (ParseException exception)
        {
            if (!ignoreErrors) throw new IllegalArgumentException(exception);
            update(variables, null);
        }
    }

    private String replacePlaceholders(Map<String, Object> variables)
    {
        if (PlaceholderUtils.hasPlaceHolders(expression))
        {
            return PlaceholderUtils.replacePlaceholders(expression, variables);
        }
        return expression;
    }

    private void update(Map<String, Object> variables, Object value)
    {
        variables.put(targetVariableName, value);
    }
}
