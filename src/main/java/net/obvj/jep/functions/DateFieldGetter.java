package net.obvj.jep.functions;

import java.util.Calendar;
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
         * The year
         */
        YEAR
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getDateField(date, Calendar.YEAR);
            }
        },

        /**
         * The month, a number from 1 (January) to 12 (December)
         */
        MONTH
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getMonth(date);
            }
        },

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
        },

        /**
         * The day of month (the first day of month is 1)
         */
        DAY
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getDateField(date, Calendar.DAY_OF_MONTH);
            }
        },

        /**
         * The hour as a number from 0 (12:00 AM) to 23 (23:00 PM)
         */
        HOUR
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getDateField(date, Calendar.HOUR_OF_DAY);
            }
        },

        /**
         * The minute, a number from 0 to 59
         */
        MINUTE
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getDateField(date, Calendar.MINUTE);
            }
        },

        /**
         * The second within the minute, a number from 0 to 59
         */
        SECOND
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getDateField(date, Calendar.SECOND);
            }
        },

        /**
         * The millisecond within a second
         */
        MILLISECOND
        {
            @Override
            int getFromDate(Date date)
            {
                return DateUtils.getDateField(date, Calendar.MILLISECOND);
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
