package net.obvj.jep.functions;

import java.util.Stack;

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
        String pattern = String.valueOf(stack.pop());
        String date = String.valueOf(stack.pop());
        try
        {
            stack.push(DateUtils.parseDate(date, pattern));
        }
        catch (java.text.ParseException parseException)
        {
            throw new IllegalArgumentException(parseException);
        }
	}

}
