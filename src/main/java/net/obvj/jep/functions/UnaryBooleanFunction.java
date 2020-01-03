package net.obvj.jep.functions;

import java.util.Stack;
import java.util.function.Predicate;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.NumberUtils;

/**
 * A function that accepts one argument and returns a boolean value.
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryBooleanFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    protected static final double FALSE = 0d;
    protected static final double TRUE = 1d;

    public enum Strategy
    {
        /**
         * Returns true if the given object is an integer number
         */
        @Function("isInteger")
        IS_INTEGER(object -> NumberUtils.isNumber(object) && NumberUtils.isInteger(NumberUtils.parseDouble(object))),

        /**
         * Returns true if the given object is a number containing a decimal
         */
        @Function("isDecimal")
        IS_DECIMAL(object -> NumberUtils.isNumber(object) && NumberUtils.isDecimal(NumberUtils.parseDouble(object)));

        private Predicate<Object> predicate;

        private Strategy(Predicate<Object> predicate)
        {
            this.predicate = predicate;
        }

        public boolean execute(Object object)
        {
            return predicate.test(object);
        }
    }

    private final Strategy strategy;

    /**
     * Builds this custom command with a fixed number of 1 parameter.
     *
     * @param strategy the {@link Strategy} to be set
     */
    public UnaryBooleanFunction(Strategy strategy)
    {
        numberOfParameters = 1;
        this.strategy = strategy;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object parameter = stack.pop();
        boolean isEmpty = strategy.execute(parameter);
        stack.push(isEmpty ? TRUE : FALSE);
    }

    @Override
    public Object getStrategy()
    {
        return strategy;
    }

}
