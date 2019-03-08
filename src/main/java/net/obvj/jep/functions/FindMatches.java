package net.obvj.jep.functions;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.RegexUtils;

/**
 * This class implements a JEP function that finds matches of a given regular expression
 * in the input string.
 *
 * @author oswaldo.bapvic.jr
 */
public class FindMatches extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of two parameters
     */
    public FindMatches()
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
            stack.push(Collections.emptyList());
            return;
        }
        if (arg2 == null)
        {
            throw new IllegalArgumentException("The RegEx cannot be null");
        }

        List<String> matches = RegexUtils.findMatches(arg1.toString(), arg2.toString());
        stack.push(matches);
    }

}
