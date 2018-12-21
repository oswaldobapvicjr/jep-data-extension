package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function that converts a string to all lower-case letters.
 *
 * @author oswaldo.bapvic.jr
 */
public class Replace extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of three parameters
     */
    public Replace()
    {
        numberOfParameters = 3;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object arg3 = stack.pop();
        Object arg2 = stack.pop();
        Object arg1 = stack.pop();

        if (arg1 == null || arg2 == null)
        {
            stack.push(arg1);
            return;
        }

        String sourceString = arg1.toString();
        String searchString = arg2.toString();
        String replacementString = arg3 == null ? StringUtils.EMPTY : arg3.toString();

        Object result = StringUtils.replace(sourceString, searchString, replacementString);
        stack.push(result);
    }

}
