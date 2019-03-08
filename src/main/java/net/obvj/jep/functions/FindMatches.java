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
    public enum ReturnStrategy
    {
        /**
         * Returns all matches found in a string
         */
        ALL_MATCHES
        {
            @Override
            void pushResult(Stack stack, String string, String pattern)
            {
                List<String> matches = RegexUtils.findMatches(string, pattern);
                stack.push(matches);
            }
        },

        /**
         * Returns the first match found in a string
         */
        FIRST_MATCH
        {
            @Override
            void pushResult(Stack stack, String string, String pattern)
            {
                String matches = RegexUtils.firstMatch(string, pattern);
                stack.push(matches);
            }
        };

        /**
         * Pushes the function result depending on the strategy.
         *
         * @param stack the stack to which the result will be pushed
         * @param string the string to be evaluated
         * @param pattern the pattern to be used
         */
        abstract void pushResult(Stack stack, String string, String pattern);
    }

    private ReturnStrategy returnStrategy;

    /**
     * Builds this function with a fixed number of two parameters
     */
    public FindMatches(ReturnStrategy returnStrategy)
    {
        numberOfParameters = 2;
        this.returnStrategy = returnStrategy;
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

        returnStrategy.pushResult(stack, arg1.toString(), arg2.toString());
    }

}
