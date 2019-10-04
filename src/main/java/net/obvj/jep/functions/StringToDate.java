package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;

/**
 * A function that converts a string into a date. The parse pattern may be specified or
 * not. A different behavior may be expected, depending on the number of parameters. E.g.:
 *
 * <ul>
 *
 * <li><code>str2date("2015-10-03T08:00:01.123Z")</code>
 * <ul>
 * <li>attempts to parse the string by trying a set of different parse patterns,
 * supporting RFC-3339, RFC-822 and a set of common ISO-8601 variations (fastest choice
 * when handling RFC-3339 dates). Also recommended for other formats when the parse
 * pattern is unknown or heterogeneous, but it comes with a cost)</li>
 * </ul>
 *
 * <li><code>str2date("2015-10-03", "yyyy-MM-dd")</code></li>
 * <ul>
 * <li>attempts to parse the given string using the specified parse pattern in the second
 * parameter (fastest approach when possible, but does not support RFC-3339)</li>
 * </ul>
 *
 * <li><code>str2date("2015-10-03", "yyyy-MM-dd'T'HH", "yyyy-MM-dd'T'HH", ...)</code></li>
 * <ul>
 * <li>attempts to parse the given string by evaluating the parse patterns provided in the
 * second and following parameters, until a pattern that fits the date is evaluated</li>
 * </ul>
 *
 * </ul>
 *
 * @author oswaldo.bapvic.jr
 */
@Function("str2date")
public class StringToDate extends PostfixMathCommand
{
    /**
     * Builds this custom command with a variable number of parameters
     */
    public StringToDate()
    {
        numberOfParameters = -1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        if (curNumberOfParameters < 1) throw new ParseException("At least one string is required");

        // The objects from the second to the last parameter may be the format arguments
        int numberOfPatterns = curNumberOfParameters - 1;
        String[] patterns = new String[numberOfPatterns];
        for (int i = numberOfPatterns - 1; i >= 0; i--)
        {
            patterns[i] = String.valueOf(stack.pop());
        }

        // The first argument must be the string to be parsed
        Object date = stack.pop();
        validateInput(date);

        stack.push(parseDate(String.valueOf(date), patterns));
    }

    /**
     * Validates that the source object is not null or empty
     *
     * @param source the source object to be validated
     */
    private void validateInput(Object source)
    {
        if (source == null || StringUtils.isEmpty(source.toString()))
        {
            throw new IllegalArgumentException(String.format("Invalid parameter received: (%s)", source));
        }
    }

    private Date parseDate(String date, String[] patterns)
    {
        try
        {
            return patterns.length == 0 ? DateUtils.parseDate(date) : DateUtils.parseDate(date, patterns);
        }
        catch (java.text.ParseException parseException)
        {
            throw new IllegalArgumentException(parseException);
        }
    }
}
