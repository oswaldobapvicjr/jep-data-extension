package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;

/**
 * A function that returns the date corresponding to the last day of the month given a
 * source date
 *
 * @author oswaldo.bapvic.jr
 */
public class EndOfMonth extends PostfixMathCommand
{
    /**
     * Builds this custom command with a fixed number of 1 parameter
     */
    public EndOfMonth()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Date date = DateUtils.parseDate(stack.pop());
        stack.push(DateUtils.endOfMonth(date));
    }

}
