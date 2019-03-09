package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * A JEP function that replaces occurrences of a string with another string
 *
 * @author oswaldo.bapvic.jr
 */
public class Replace extends PostfixMathCommand
{
    public enum SearchStrategy
    {
        NORMAL
        {
            @Override
            String replace(String sourceString, String searchString, String replacementString)
            {
                return StringUtils.replace(sourceString, searchString, replacementString);
            }
        };

        abstract String replace(String sourceString, String searchString, String replacementString);
    }

    private SearchStrategy searchStrategy;

    /**
     * Builds this function with a fixed number of three parameters and the given search
     * strategy
     */
    public Replace(SearchStrategy searchStrategy)
    {
        numberOfParameters = 3;
        this.searchStrategy = searchStrategy;
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

        String result = searchStrategy.replace(sourceString, searchString, replacementString);
        stack.push(result);
    }

}
