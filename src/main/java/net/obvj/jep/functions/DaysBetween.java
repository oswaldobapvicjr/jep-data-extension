package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;

/**
 * A command that returns the number of days between two dates
 *
 * @author oswaldo.bapvic.jr
 */
@Function("daysBetween")
public class DaysBetween extends PostfixMathCommand
{
    /**
     * Builds this custom command with a fixed number of 2 parameters
     */
    public DaysBetween()
    {
        numberOfParameters = 2;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Date date2 = DateUtils.parseDate(stack.pop());
        Date date1 = DateUtils.parseDate(stack.pop());
        stack.push(DateUtils.daysBetween(date1, date2));
    }

}
