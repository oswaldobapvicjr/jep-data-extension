package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.RegexUtils;

/**
 * This class implements a JEP function that returns 1 (true) if a string matches a given
 * regular expression
 *
 * @author oswaldo.bapvic.jr
 */
public class Matches extends PostfixMathCommand
{
    private static final double TRUE = 1d;
    private static final double FALSE = 0d;

    /**
     * Builds this function with a fixed number of two parameters
     */
    public Matches()
    {
        numberOfParameters = 2;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object arg2 = stack.pop();
        Object arg1 = stack.pop();

        if (arg1 == null)
        {
            stack.push(FALSE);
            return;
        }
        if (arg2 == null)
        {
            throw new IllegalArgumentException("The RegEx cannot be null");
        }

        boolean matches = RegexUtils.matches(arg1.toString(), arg2.toString());
        stack.push(matches ? TRUE : FALSE);
    }

}
