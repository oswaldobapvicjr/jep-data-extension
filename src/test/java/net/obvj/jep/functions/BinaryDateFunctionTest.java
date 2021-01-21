package net.obvj.jep.functions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.TimeZone;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.BinaryDateFunction.Strategy;
import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.DateUtils;

/**
 * Unit tests for the {@link BinaryDateFunction}.
 *
 * @author oswaldo.bapvic.jr
 */
public class BinaryDateFunctionTest
{
    // Test date
    private static final Date DATE;

    static
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MARCH, 11, 13, 35, 0);
        calendar.set(Calendar.MILLISECOND, 123);
        DATE = calendar.getTime();
    }

    private static final String ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final String STR_DATE_ISO_FULL = "2017-03-11T13:35:00.123Z";
    private static final String STR_DATE_SHORT = "2017-03-11";

    private static final BinaryDateFunction ADD_YEARS = new BinaryDateFunction(Strategy.ADD_YEARS);
    private static final BinaryDateFunction ADD_QUARTERS = new BinaryDateFunction(Strategy.ADD_QUARTERS);
    private static final BinaryDateFunction ADD_MONTHS = new BinaryDateFunction(Strategy.ADD_MONTHS);
    private static final BinaryDateFunction ADD_WEEKS = new BinaryDateFunction(Strategy.ADD_WEEKS);
    private static final BinaryDateFunction ADD_DAYS = new BinaryDateFunction(Strategy.ADD_DAYS);
    private static final BinaryDateFunction ADD_HOURS = new BinaryDateFunction(Strategy.ADD_HOURS);
    private static final BinaryDateFunction ADD_MINUTES = new BinaryDateFunction(Strategy.ADD_MINUTES);
    private static final BinaryDateFunction ADD_SECONDS = new BinaryDateFunction(Strategy.ADD_SECONDS);

    /**
     * Runs the function.
     *
     * @param function the function to run
     * @param date     the base date (1st parameter)
     * @param amount   the amount (2nd parameter)
     * @return the resulting date, formatted
     * @throws ParseException in case of issues running the function
     */
    private String run(BinaryDateFunction function, Object date, Object amount) throws ParseException
    {
        Stack<Object> params = CollectionsUtils.newParametersStack(date, amount);
        function.run(params);
        Date result = (Date) params.pop();
        return formatDate(result);
    }

    private String formatDate(Date result)
    {
        return DateUtils.formatDate(result, ISO_FULL_DATE_TIME_FORMAT_WITH_TIME_ZONE);
    }

    @Test
    public void addDays_validDateAsStringISOFullAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_DAYS, STR_DATE_ISO_FULL, 30), is(equalTo("2017-04-10T13:35:00.123Z")));
    }

    @Test
    public void addDays_validDateAsStringShortAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_DAYS, STR_DATE_SHORT, 30), is(equalTo("2017-04-10T00:00:00.000Z")));
    }

    @Test
    public void addDays_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_DAYS, DATE, 7), is(equalTo("2017-03-18T13:35:00.123Z")));
    }

    @Test
    public void addDays_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_DAYS, DATE, -11), is(equalTo("2017-02-28T13:35:00.123Z")));
    }

    @Test
    public void addDays_validDateAndZeroAmount() throws ParseException
    {
        assertThat(run(ADD_DAYS, DATE, 0), is(equalTo(STR_DATE_ISO_FULL)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDays_nullDate() throws ParseException
    {
        run(ADD_DAYS, null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDays_invalidDateTypeAsString() throws ParseException
    {
        run(ADD_DAYS, 20, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDays_doubleAmount() throws ParseException
    {
        run(ADD_DAYS, DATE, 1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addDays_invalidAmountType() throws ParseException
    {
        run(ADD_DAYS, DATE, "string");
    }

    @Test
    public void addMonths_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_MONTHS, DATE, 1), is(equalTo("2017-04-11T13:35:00.123Z")));
    }

    @Test
    public void addMonths_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_MONTHS, DATE, -13), is(equalTo("2016-02-11T13:35:00.123Z")));
    }

    @Test
    public void addYears_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_YEARS, DATE, 3), is(equalTo("2020-03-11T13:35:00.123Z")));
    }

    @Test
    public void addYears_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_YEARS, DATE, -20), is(equalTo("1997-03-11T13:35:00.123Z")));
    }

    @Test
    public void addSeconds_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_SECONDS, DATE, 3), is(equalTo("2017-03-11T13:35:03.123Z")));
    }

    @Test
    public void addSeconds_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_SECONDS, DATE, -3), is(equalTo("2017-03-11T13:34:57.123Z")));
    }

    @Test
    public void addMinutes_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_MINUTES, DATE, 60), is(equalTo("2017-03-11T14:35:00.123Z")));
    }

    @Test
    public void addMinutes_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_MINUTES, DATE, -3), is(equalTo("2017-03-11T13:32:00.123Z")));
    }

    @Test
    public void addHours_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_HOURS, DATE, 25), is(equalTo("2017-03-12T14:35:00.123Z")));
    }

    @Test
    public void addHours_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_HOURS, DATE, -3), is(equalTo("2017-03-11T10:35:00.123Z")));
    }

    @Test
    public void addWeeks_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_WEEKS, DATE, 5), is(equalTo("2017-04-15T13:35:00.123Z")));
    }

    @Test
    public void addWeeks_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_WEEKS, DATE, -1), is(equalTo("2017-03-04T13:35:00.123Z")));
    }

    @Test
    public void addQuarters_validDateAndPositiveAmount() throws ParseException
    {
        assertThat(run(ADD_QUARTERS, DATE, 4), is(equalTo("2018-03-11T13:35:00.123Z")));
    }

    @Test
    public void addQuarters_validDateAndNegativeAmount() throws ParseException
    {
        assertThat(run(ADD_QUARTERS, DATE, -1), is(equalTo("2016-12-11T13:35:00.123Z")));
    }

}
