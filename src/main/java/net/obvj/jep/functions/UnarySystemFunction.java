package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * An "abstract" function that receives a single parameter and retrieves system data.
 *
 * @author oswaldo.bapvic.jr
 */
public class UnarySystemFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    /**
     * Defines particular strategies for the {@link UnarySystemFunction}.
     */
    public enum Strategy
    {
        /**
         * Retrieves environment variables
         */
        @Function("getEnv")
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
        @Function("getSystemProperty")
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
     * Builds this function with a fixed number of one parameter.
     *
     * @param strategy the {@link Strategy} to be set
     */
    public UnarySystemFunction(Strategy strategy)
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

    /**
     * @see net.obvj.jep.functions.MultiStrategyCommand#getStrategy()
     */
    @Override
    public Object getStrategy()
    {
        return strategy;
    }

}
