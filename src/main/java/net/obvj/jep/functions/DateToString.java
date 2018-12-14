package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;

/**
 * A command that formats dates in a specified pattern
 *
 * @author oswaldo.bapvic.jr
 */
public class DateToString extends PostfixMathCommand
{
    /**
     * Builds this custom command with a fixed number of 2 parameters
     */
    public DateToString()
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
        Object pattern = stack.pop();
        Object date = stack.pop();

        if (pattern == null || StringUtils.isEmpty(pattern.toString()))
        {
            throw new IllegalArgumentException("A pattern is required");
        }

        stack.push(DateUtils.formatDate(toDate(date), pattern.toString()));
    }

    private Date toDate(Object object)
    {
        if (object instanceof Date)
        {
            return (Date) object;
        }
        throw new IllegalArgumentException("Invalid date object received");
    }

}
