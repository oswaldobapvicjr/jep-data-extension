package net.obvj.jep.functions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.RegexUtils;

/**
 * An "abstract" function that accepts two strings and returns an object.
 *
 * @author oswaldo.bapvic.jr
 */
public class BinaryStringFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    /**
     * Defines particular concrete behaviors for the {@link BinaryStringFunction}.
     */
    public enum Strategy
    {
        /**
         * Returns all matches found in a string
         */
        @Function("findMatches")
        ALL_MATCHES
        {
            @Override
            void pushResult(Stack stack, String string, String regex)
            {
                List<String> matches = RegexUtils.findMatches(string, regex);
                stack.push(matches);
            }
        },

        /**
         * Returns the first match found in a string
         */
        @Function("findMatch")
        FIRST_MATCH
        {
            @Override
            void pushResult(Stack stack, String string, String regex)
            {
                String matches = RegexUtils.firstMatch(string, regex);
                stack.push(matches);
            }
        },

        /**
         * Splits a string into a string array based on a separating string or regular expression
         */
        @Function("split")
        SPLIT
        {
            @Override
            void pushResult(Stack stack, String string, String regex)
            {
                List<String> strings = Arrays.asList(string.split(regex));
                stack.push(strings);
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

    private final Strategy strategy;

    /**
     * Builds this function with a fixed number of two parameters.
     *
     * @param returnStrategy the {@link Strategy} to be set
     */
    public BinaryStringFunction(Strategy returnStrategy)
    {
        numberOfParameters = 2;
        this.strategy = returnStrategy;
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

        strategy.pushResult(stack, arg1.toString(), arg2.toString());
    }

    /**
     * @see net.obvj.jep.functions.MultiStrategyCommand#getStrategy()
     */
    @Override
    public Object getStrategy()
    {
        return strategy;
    }

}
