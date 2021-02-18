package net.obvj.jep.functions;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.DateUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * An "abstract" function that receives two objects: a Date and an integer, and returns a
 * Date, with concrete behavior defined by {@link BinaryDateFunction.Strategy}.
 *
 * @author oswaldo.bapvic.jr
 */
public class BinaryDateFunction extends PostfixMathCommand implements MultiStrategyCommand
{
    /**
     * Defines particular strategies for the {@link BinaryDateFunction}.
     */
    public enum Strategy implements BiFunction<Date, Integer, Date>
    {
        /**
         * Adds a number of seconds to a date.
         */
        @Function("addSeconds")
        ADD_SECONDS
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return apply(date, calendar -> calendar.add(Calendar.SECOND, amount));
            }
        },

        /**
         * Adds a number of minutes to a date.
         */
        @Function("addMinutes")
        ADD_MINUTES
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return apply(date, calendar -> calendar.add(Calendar.MINUTE, amount));
            }
        },

        /**
         * Adds a number of hours to a date.
         */
        @Function("addHours")
        ADD_HOURS
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return apply(date, calendar -> calendar.add(Calendar.HOUR_OF_DAY, amount));
            }
        },

        /**
         * Adds a number of days to a date.
         */
        @Function("addDays")
        ADD_DAYS
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return apply(date, calendar -> calendar.add(Calendar.DAY_OF_MONTH, amount));
            }
        },

        /**
         * Adds a number of weeks to a date.
         */
        @Function("addWeeks")
        ADD_WEEKS
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return apply(date, calendar -> calendar.add(Calendar.WEEK_OF_MONTH, amount));
            }
        },

        /**
         * Adds a number of months to a date.
         */
        @Function("addMonths")
        ADD_MONTHS
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return apply(date, calendar -> calendar.add(Calendar.MONTH, amount));
            }
        },

        /**
         * Adds a number of quarters to a date.
         */
        @Function("addQuarters")
        ADD_QUARTERS
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return ADD_MONTHS.apply(date, amount * 3);
            }
        },

        /**
         * Adds a number of years to a date.
         */
        @Function("addYears")
        ADD_YEARS
        {
            @Override
            public Date apply(Date date, Integer amount)
            {
                return apply(date, calendar -> calendar.add(Calendar.YEAR, amount));
            }
        };

        public Date apply(Date date, Consumer<Calendar> consumer)
        {
            Calendar calendar = DateUtils.toCalendar(date);
            consumer.accept(calendar);
            return calendar.getTime();
        }
    }

    private final Strategy strategy;

    /**
     * Builds this function with a fixed number of two parameters.
     *
     * @param returnStrategy the {@link Strategy} to be set
     */
    public BinaryDateFunction(Strategy returnStrategy)
    {
        numberOfParameters = 2;
        this.strategy = returnStrategy;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object arg2 = stack.pop();
        Object arg1 = stack.pop();

        Date date = DateUtils.parseDate(arg1);

        if (!NumberUtils.isInteger(arg2))
        {
            throw new IllegalArgumentException("The second argument must be an integer number");
        }

        int amount = NumberUtils.parseInt(arg2);

        Date result = strategy.apply(date, amount);
        stack.push(result);
    }

    /**
     * @see net.obvj.jep.functions.MultiStrategyCommand#getStrategy()
     */
    @Override
    public Object getStrategy()
    {
        return strategy;
    }

}
