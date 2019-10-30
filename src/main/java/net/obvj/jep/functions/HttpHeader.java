package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;

/**
 * This class implements a JEP function that groups HTTP header values together into a Map
 * os Strings for usage with the {@link Http} function.
 *
 * @author oswaldo.bapvic.jr
 */
@Function("httpHeader")
public class HttpHeader extends PostfixMathCommand
{
    /**
     * Builds this function with any number of parameters
     */
    public HttpHeader()
    {
        numberOfParameters = -1;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);

        // Extracts all objects from the stack, limited to the curNumberOfParameters
        String[] arguments = new String[curNumberOfParameters];
        for (int i = curNumberOfParameters - 1; i >= 0; i--)
        {
            arguments[i] = String.valueOf(stack.pop());
        }

        stack.push(CollectionsUtils.asMap(arguments));
    }

}
