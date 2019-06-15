package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.RegexUtils;

/**
 * A JEP function that replaces occurrences of a string with another string
 *
 * @author oswaldo.bapvic.jr
 */
public class Replace extends PostfixMathCommand implements MultiStrategyCommand
{
    public enum Strategy
    {
        /**
         * Replaces occurrences of a string with another string
         */
    	NORMAL
        {
            @Override
            String execute(String sourceString, String searchString, String replacement)
            {
                return StringUtils.replace(sourceString, searchString, replacement);
            }
        },

        /**
         * Uses regular expressions to find matches for replacement
         */
        REGEX
        {
            @Override
            String execute(String sourceString, String searchString, String replacement)
            {
                return RegexUtils.replaceMatches(sourceString, searchString, replacement);
            }
        };

        abstract String execute(String sourceString, String searchString, String replacement);
    }

    private final Strategy strategy;

    /**
     * Builds this function with a fixed number of three parameters and the given search
     * strategy
     */
    public Replace(Strategy searchStrategy)
    {
        numberOfParameters = 3;
        this.strategy = searchStrategy;
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
        String replacement = arg3 == null ? StringUtils.EMPTY : arg3.toString();

        String result = strategy.execute(sourceString, searchString, replacement);
        stack.push(result);
    }

    /**
     * @see net.obvj.jep.functions.MultiStrategyCommand#getStrategy()
     */
    public Object getStrategy()
    {
        return strategy;
    }

}
