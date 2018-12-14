package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;

/**
 * A command that parses a string into date in a specified pattern
 *
 * @author oswaldo.bapvic.jr
 */
public class StringToDate extends PostfixMathCommand
{
	/**
	 * Builds this custom command with a fixed number of 2 parameters
	 */
	public StringToDate()
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
        validateInput(pattern, date);
        try
        {
            stack.push(DateUtils.parseDate(date.toString(), pattern.toString()));
        }
        catch (java.text.ParseException parseException)
        {
            throw new IllegalArgumentException(parseException);
        }
	}

    private void validateInput(Object pattern, Object date)
    {
        if (date == null || StringUtils.isEmpty(date.toString()))
        {
            throw new IllegalArgumentException("A string is required");
        }
        if (pattern == null || StringUtils.isEmpty(pattern.toString()))
        {
            throw new IllegalArgumentException("A pattern is required");
        }
    }

}
