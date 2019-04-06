package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;

/**
 * A function that returns a field from a Date, depending on the date field strategy
 *
 * @author oswaldo.bapvic.jr
 */
public class DateFieldGetter extends PostfixMathCommand
{

    public enum DateField
    {
        /**
         * The week number of the year, according to ISO 8601 rules
         */
        ISO_WEEK_NUMBER
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getIsoWeekNumber(date);
            }
        };

        abstract int getFromDate(Date date);
    }

    private final DateField dateField;

    /**
     * Builds this custom command with a fixed number of 1 parameter
     */
    public DateFieldGetter(DateField dateField)
    {
        numberOfParameters = 1;
        this.dateField = dateField;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object parameter = stack.pop();
        Date date = DateUtils.parseDate(parameter);
        stack.push(dateField.getFromDate(date));
    }

    /**
     * @return the date field setup for this instance
     */
    public DateField getDateField()
    {
        return dateField;
    }
}
