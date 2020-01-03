package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.RegexUtils;

/**
 * A function that accepts two parameters (of any type) and returns 1 (true) or 0 (false),
 * depending on the given evaluation strategy.
 * <p>
 * The function will always return true if both parameters are equal.
 *
 * @author oswaldo.bapvic.jr
 */
public class BinaryBooleanFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    protected static final double FALSE = 0d;
    protected static final double TRUE = 1d;

    public enum Strategy
    {
        /**
         * Returns true if the string in the 1st parameter starts with the string received in the
         * 2nd parameter
         */
        @Function("startsWith")
        STRING_STARTS_WITH
        {
            @Override
            boolean evaluate(Object arg1, Object arg2)
            {
                return StringUtils.startsWith((String) arg1, (String) arg2);
            }
        },

        /**
         * Returns true if the string in the 1st parameter ends with the string received in the
         * 2nd parameter
         */
        @Function("endsWith")
        STRING_ENDS_WITH
        {
            @Override
            boolean evaluate(Object arg1, Object arg2)
            {
                return StringUtils.endsWith((String) arg1, (String) arg2);
            }
        },

        /**
         * Returns true if the string in the 1st parameter matches the regular expression received
         * in the 2nd parameter
         */
        @Function("matches")
        STRING_MATCHES
        {
            @Override
            boolean evaluate(Object arg1, Object arg2)
            {
                if (arg2 == null) throw new IllegalArgumentException("The RegEx cannot be null");
                return RegexUtils.matches((String) arg1, (String) arg2);
            }
        };

        abstract boolean evaluate(Object arg1, Object arg2);
    }

    private final Strategy operation;

    /**
     * Builds this custom command with a fixed number of 2 parameters.
     *
     * @param operation the {@link Strategy} to be set
     */
    public BinaryBooleanFunction(Strategy operation)
    {
        numberOfParameters = 2;
        this.operation = operation;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object arg2 = stack.pop();
        Object arg1 = stack.pop();

        boolean booleanValue = operation.evaluate(arg1, arg2);
        stack.push(booleanValue ? TRUE : FALSE);
    }

    /**
     * @see net.obvj.jep.functions.MultiStrategyCommand#getStrategy()
     */
    @Override
    public Object getStrategy()
    {
        return operation;
    }

}
