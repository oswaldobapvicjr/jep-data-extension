package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function that converts a string to all upper-case letters.
 * 
 * @author oswaldo.bapvic.jr
 */
public class Upper extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of one parameter
     */
    public Upper()
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
        Object result = param == null ? null : param.toString().toUpperCase();
        stack.push(result);
    }

}
