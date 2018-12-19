package net.obvj.jep.command;

import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;

import net.obvj.jep.ExtendedExpressionEvaluator;

/**
 * An object that evaluates an user expression and updates a context map with the
 * evaluation result.
 *
 * @author oswaldo.bapvic.jr
 */
public class ExpressionCommand implements Consumer<Map<String, Object>>
{
    private final ExtendedExpressionEvaluator evaluator;
    private final String targetVariableName;
    private final boolean ignoreErrors;

    /**
     * Builds this extended expression evaluator with default setup.
     *
     * @param expression         the expression to be used for evaluation
     * @param targetVariableName the new variable name to be populated by this evaluator with
     *                           the evaluation result
     */
    public ExpressionCommand(String targetVariableName, String expression)
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
    public ExpressionCommand(String targetVariableName, String expression, boolean ignoreErrors)
    {
        if (StringUtils.isBlank(targetVariableName))
        {
            throw new IllegalArgumentException("The target name cannot be empty");
        }
        this.evaluator = new ExtendedExpressionEvaluator(expression);
        this.targetVariableName = targetVariableName;
        this.ignoreErrors = ignoreErrors;
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
        try
        {
            Object result = evaluator.evaluate(variables);
            update(variables, result);
        }
        catch (ParseException exception)
        {
            if (!ignoreErrors) throw new IllegalArgumentException(exception);
            update(variables, null);
        }
    }

    private void update(Map<String, Object> variables, Object value)
    {
        variables.put(targetVariableName, value);
    }
}
