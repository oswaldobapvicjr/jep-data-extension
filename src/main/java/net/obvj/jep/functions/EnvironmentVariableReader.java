package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function that retrieves OS environment variables.
 *
 * @author oswaldo.bapvic.jr
 */
public class EnvironmentVariableReader extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of one parameter
     */
    public EnvironmentVariableReader()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object key = stack.pop();
        Object result = key == null ? null : System.getenv(key.toString());
        stack.push(result);
    }

}
