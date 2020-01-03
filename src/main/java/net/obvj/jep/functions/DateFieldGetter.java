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
public class DateFieldGetter extends PostfixMathCommand implements MultiStrategyCommand
{

    public enum DateField
    {
        /**
         * The year
         */
        @Function("year")
        YEAR(date -> DateUtils.getDateField(date, Calendar.YEAR)),

        /**
         * The quarter of the year, a number from 1 to 4
         */
        @Function("quarter")
        QUARTER(DateUtils::getQuarter),

        /**
         * The month, a number from 1 (January) to 12 (December)
         */
        @Function("month")
        MONTH(DateUtils::getMonth),

        /**
         * The week number of the year, according to ISO 8601 rules
         */
        @Function("isoWeekNumber")
        ISO_WEEK_NUMBER(DateUtils::getIsoWeekNumber),

        /**
         * The day of the week of a date, a number from 1 (Sunday) to 7 (Saturday)
         */
        @Function("weekday")
        WEEK_DAY(date -> DateUtils.getDateField(date, Calendar.DAY_OF_WEEK)),

        /**
         * The day of month (the first day of month is 1)
         */
        @Function("day")
        DAY(date -> DateUtils.getDateField(date, Calendar.DAY_OF_MONTH)),

        /**
         * The hour as a number from 0 (12:00 AM) to 23 (11:00 PM)
         */
        @Function("hour")
        HOUR(date -> DateUtils.getDateField(date, Calendar.HOUR_OF_DAY)),

        /**
         * The minute, a number from 0 to 59
         */
        @Function("minute")
        MINUTE(date -> DateUtils.getDateField(date, Calendar.MINUTE)),

        /**
         * The second within the minute, a number from 0 to 59
         */
        @Function("second")
        SECOND(date -> DateUtils.getDateField(date, Calendar.SECOND)),

        /**
         * The millisecond within a second
         */
        @Function("millisecond")
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
     * Builds this custom command with a fixed number of 1 parameter.
     *
     * @param dateField the {@link DateField} strategy to be set
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
     * @return the date field retrieval strategy for this instance
     */
    @Override
    public Object getStrategy()
    {
        return dateField;
    }
}
