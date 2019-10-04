package net.obvj.jep.functions;

import java.util.Stack;

import java.util.function.UnaryOperator;

import org.apache.commons.text.WordUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * A JEP function that receives a string object and produces another string
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryStringFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    public enum Strategy implements UnaryOperator<String>
    {
        /**
         * Converts a string to all lower-case letters
         */
        @Function("lower")
        LOWER(param -> param.toLowerCase()),

        /**
         * Converts a string to proper case; the first letter in each word to upper-case, and all
         * other letter to lower-case
         */
        @Function("proper")
        PROPER(WordUtils::capitalizeFully),
        
        /**
         * Removes leading and trailing spaces from a string
         */
        @Function("trim")
        TRIM(param -> param.trim()),

        /**
         * Converts a string to all upper-case letters
         */
        @Function("upper")
        UPPER(param -> param.toUpperCase());

        java.util.function.Function<String, String> function;

        private Strategy(UnaryOperator<String> function)
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
