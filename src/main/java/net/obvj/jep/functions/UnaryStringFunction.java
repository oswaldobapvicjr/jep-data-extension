package net.obvj.jep.functions;

import java.util.Stack;
import java.util.function.Function;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * A JEP function that receives a string object and produces another string
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryStringFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    public enum Strategy implements Function<String, String>
    {
        /**
         * Converts a string to all lower-case letters
         */
        LOWER(param -> param.toLowerCase()),

        /**
         * Removes leading and trailing spaces from a string
         */
        TRIM(param -> param.trim()),

        /**
         * Converts a string to all upper-case letters
         */
        UPPER(param -> param.toUpperCase());

        Function<String, String> function;

        private Strategy(Function<String, String> function)
        {
            this.function = function;
        }

        @Override
        public String apply(String string)
        {
            return function.apply(string);
        }
    }

    private final Strategy strategy;

    /**
     * Builds this function with a fixed number of one parameter
     */
    public UnaryStringFunction(Strategy strategy)
    {
        numberOfParameters = 1;
        this.strategy = strategy;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object param = stack.pop();
        Object result = param == null ? null : strategy.apply(param.toString());
        stack.push(result);
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
