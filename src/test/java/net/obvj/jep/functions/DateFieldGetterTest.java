package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.DateFieldGetter.DateField;
import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.DateUtils;

/**
 * Unit tests for the {@link DateFieldGetter} function
 *
 * @author oswaldo.bapvic.jr
 */
public class DateFieldGetterTest
{
    private static DateFieldGetter isoWeekNumberGetter = new DateFieldGetter(DateField.ISO_WEEK_NUMBER);

    /**
     * Tests the ISO week number retrieval for a valid date
     */
    @Test
    public void testIsoWeekNumberFromValidDate() throws ParseException, java.text.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DateUtils.parseDate("20190406", "yyyyMMdd"));
        isoWeekNumberGetter.run(parameters);
        assertEquals(14, parameters.pop());
    }

    /**
     * Tests the ISO week number retrieval for a valid date as string
     */
    @Test
    public void testIsoWeekNumberFromValidDateAsString() throws ParseException, java.text.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("2019-04-08T14:45:00.123Z");
        isoWeekNumberGetter.run(parameters);
        assertEquals(15, parameters.pop());
    }

    /**
     * Tests the ISO week number retrieval for a null date
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsoWeekNumberFromNullDate() throws ParseException, java.text.ParseException
    {
        Date date = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(date);
        isoWeekNumberGetter.run(parameters);
    }

}
