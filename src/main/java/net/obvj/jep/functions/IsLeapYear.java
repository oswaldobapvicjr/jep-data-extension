package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * A function that returns 1 if the given parameter is either a leap year or a Date whose
 * year is a leap year, or 0 if not.
 *
 * @author oswaldo.bapvic.jr
 */
@Function("isLeapYear")
public class IsLeapYear extends PostfixMathCommand
{
    private static final double FALSE = 0d;
    private static final double TRUE = 1d;

    /**
     * Builds this custom command with a fixed number of 1 parameter
     */
    public IsLeapYear()
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
        Object parameter = stack.pop();

        boolean leapYear = false;

        if (NumberUtils.isNumber(parameter))
        {
            int year = NumberUtils.parseInt(parameter);
            leapYear = DateUtils.isLeapYear(year);
        }
        else if (DateUtils.isParsable(parameter))
        {
            Date date = DateUtils.parseDate(parameter);
            leapYear = DateUtils.isLeapYear(date);
        }

        stack.push(leapYear ? TRUE : FALSE);
    }

}
