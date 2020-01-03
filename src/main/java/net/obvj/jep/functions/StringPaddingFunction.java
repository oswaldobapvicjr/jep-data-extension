package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.NumberUtils;

/**
 * A JEP function for string padding.
 *
 * @author oswaldo.bapvic.jr
 */
public class StringPaddingFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    private static final String DEFAULT_PADDING_STR = " ";

    public enum Strategy
    {
        /**
         * The input string in right-padded to the given size using the padString
         */
        @Function({ "rightPad", "rpad" })
        RIGHT_PAD
        {
            @Override
            public String execute(String input, Integer size, String padString)
            {
                return StringUtils.rightPad(input, size, padString);
            }
        },

        /**
         * The input string in left-padded to the given size using the padString
         */
        @Function({ "leftPad", "lpad" })
        LEFT_PAD
        {
            @Override
            public String execute(String input, Integer size, String padString)
            {
                return StringUtils.leftPad(input, size, padString);
            }
        };

        public abstract String execute(String input, Integer size, String padString);
    }

    private final Strategy strategy;

    /**
     * Builds this function with a variable number of parameters.
     *
     * @param strategy the {@link Strategy} to be set
     */
    public StringPaddingFunction(Strategy strategy)
    {
        numberOfParameters = -1;
        this.strategy = strategy;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        if (curNumberOfParameters < 2 || curNumberOfParameters > 3)
        {
            throw new ParseException("This funcion accepts two or three arguments");
        }

        String padString = curNumberOfParameters == 3 ? stack.pop().toString() : DEFAULT_PADDING_STR;
        int size = NumberUtils.parseInt(stack.pop());
        String input = stack.pop().toString();

        String result = input == null ? null : strategy.execute(input, size, padString);
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
