package net.obvj.jep.functions;

import org.nfunk.jep.EvaluatorI;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.CallbackEvaluationI;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * A command that formats strings based on a pattern and variable arguments.
 * <p>
 * This function implements {@code CallbackEvaluationI} to secure usage "in-line", i.e.,
 * inside other functions, such as {@code http}, {@code jsonpath} and {@code xpath},
 * without affecting the parameters from the outer functions in the stack, given this
 * function shall support variable number of arguments.
 *
 * @author oswaldo.bapvic.jr
 */
public class FormatString extends PostfixMathCommand implements CallbackEvaluationI
{
    /**
     * Builds this function with unlimited number of parameters
     */
    public FormatString()
    {
        numberOfParameters = -1;
    }

    /**
     * Evaluates the expression
     */
    @Override
    public Object evaluate(Node node, EvaluatorI evaluator) throws ParseException
    {
        int numOfParams = node.jjtGetNumChildren();
        if (numOfParams < 2) throw new ParseException("A format and at least one argument is required");

        // The first child node must be the format
        String format = evaluator.eval(node.jjtGetChild(0)).toString();

        // The objects from the second to the last child node must be the format arguments
        Object[] arguments = new Object[numOfParams - 1];
        for (int i = 0; i < arguments.length; i++)
        {
            arguments[i] = evaluator.eval(node.jjtGetChild(i + 1));
        }

        return String.format(format, arguments);
    }

}
