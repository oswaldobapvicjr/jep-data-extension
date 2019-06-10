package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Stack;
import java.util.TimeZone;

import org.junit.Test;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.DateUtils;

/**
 * Unit tests for the {@link EndOfMonth} function
 *
 * @author oswaldo.bapvic.jr
 */
public class EndOfMonthTest
{
    private static final String STR_2019_02_19 = "2019-02-19T08:08:49.102Z";
    private static final Date DATE_2019_02_19_EOM = DateUtils.parseDate("2019-02-28T23:59:59.999Z");

    private static EndOfMonth function = new EndOfMonth();

    static
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Checks the endOfMonth function with a source date
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testEndOfMonthWithSourceDate() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DateUtils.parseDate(STR_2019_02_19));
        function.run(parameters);
        assertEquals(DATE_2019_02_19_EOM, parameters.pop());
    }

    /**
     * Checks the endOfMonth function with a source date as string
     *
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testEndOfMonthWithSourceDateAsString() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_02_19);
        function.run(parameters);
        assertEquals(DATE_2019_02_19_EOM, parameters.pop());
    }

}
