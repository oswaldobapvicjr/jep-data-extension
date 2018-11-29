package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function that converts a string to all lower-case letters.
 * 
 * @author oswaldo.bapvic.jr
 */
public class Lower extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of one parameter
     */
    public Lower()
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
        Object param = stack.pop();
        Object result = param == null ? null : param.toString().toLowerCase();
        stack.push(result);
    }

}
