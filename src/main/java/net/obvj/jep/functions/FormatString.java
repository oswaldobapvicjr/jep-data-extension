package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * A command that formats strings based on a pattern and variable arguments
 *
 * @author oswaldo.bapvic.jr
 */
public class FormatString extends PostfixMathCommand
{
    /**
     * Builds this function with unlimited number of parameters
     */
    public FormatString()
    {
        numberOfParameters = -1;
    }

    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);

        // The curNumberOfParameters applies to functions with variable number of arguments
        if (curNumberOfParameters < 2) throw new ParseException("A format and at least one argument is required");

        // The objects from the second to the last parameter must be the format arguments
        int numberOfFormatArguments = curNumberOfParameters - 1;
        Object[] arguments = new Object[numberOfFormatArguments];
        for (int i = numberOfFormatArguments - 1; i >= 0; i--)
        {
            arguments[i] = stack.pop();
        }

        // The first argument must be the format
        String format = stack.pop().toString();

        stack.push(String.format(format, arguments));
    }

}
