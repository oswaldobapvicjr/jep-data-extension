package net.obvj.jep.functions;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.function.ToIntFunction;

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
        YEAR(date -> DateUtils.getDateField(date, Calendar.YEAR)),

        /**
         * The quarter of the year, a number from 1 to 4
         */
        QUARTER(DateUtils::getQuarter),

        /**
         * The month, a number from 1 (January) to 12 (December)
         */
        MONTH(DateUtils::getMonth),

        /**
         * The week number of the year, according to ISO 8601 rules
         */
        ISO_WEEK_NUMBER(DateUtils::getIsoWeekNumber),

        /**
         * The day of month (the first day of month is 1)
         */
        DAY(date -> DateUtils.getDateField(date, Calendar.DAY_OF_MONTH)),

        /**
         * The hour as a number from 0 (12:00 AM) to 23 (11:00 PM)
         */
        HOUR(date -> DateUtils.getDateField(date, Calendar.HOUR_OF_DAY)),

        /**
         * The minute, a number from 0 to 59
         */
        MINUTE(date -> DateUtils.getDateField(date, Calendar.MINUTE)),

        /**
         * The second within the minute, a number from 0 to 59
         */
        SECOND(date -> DateUtils.getDateField(date, Calendar.SECOND)),

        /**
         * The millisecond within a second
         */
        MILLISECOND(date -> DateUtils.getDateField(date, Calendar.MILLISECOND));

        private ToIntFunction<Date> function;

        /*
         * Builds each enum object with a mandatory function to be applied
         */
        private DateField(ToIntFunction<Date> function)
        {
            this.function = function;
        }

        int getFromDate(Date date)
        {
            return function.applyAsInt(date);
        }
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
