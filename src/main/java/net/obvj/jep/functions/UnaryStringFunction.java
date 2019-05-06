package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * A JEP function that receives a single parameter and returns a string.
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryStringFunction extends PostfixMathCommand
{
    public enum Strategy
    {
        /**
         * Retrieves environment variables
         */
        GET_ENV
        {
            @Override
            String execute(String parameter)
            {
                return System.getenv(parameter);
            }
        },

        /**
         * Retrieves system properties
         */
        GET_SYSTEM_PROPERTY
        {
            @Override
            String execute(String parameter)
            {
                return System.getProperty(parameter);
            }
        };

        abstract String execute(String parameter);
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
        Object key = stack.pop();
        Object result = key == null ? null : strategy.execute(key.toString());
        stack.push(result);
    }

    public Strategy getStrategy()
    {
        return strategy;
    }

}
