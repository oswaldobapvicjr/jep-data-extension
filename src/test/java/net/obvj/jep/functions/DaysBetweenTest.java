package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.DateUtils;

/**
 * Unit tests for the {@link DaysBetween} function
 *
 * @author oswaldo.bapvic.jr
 */
public class DaysBetweenTest
{
	private static final String STR_2019_02_19 = "2019-02-19";
	private static final String STR_2019_01_19 = "2019-01-19";
	
	private static final String STR_2019_01_19_RFC_3339 = "2019-01-19T22:40:38.678912543Z";
	private static final String STR_2019_02_19_RFC_3339 = "2019-02-19T22:41:39.123Z";
	
	private static Date DATE_2019_01_19;
    private static Date DATE_2019_02_19;

    private static final long DIFFERENCE_IN_DAYS = 31L;
    
    private static DaysBetween function = new DaysBetween();

    /**
     * Setup test data
     * 
     * @throws ParseException if the test contains unparseable dates
     */
    @BeforeClass
    public static void setup() throws ParseException
    {
    	DATE_2019_01_19 = DateUtils.parseDate(STR_2019_01_19, "yyyy-MM-dd");
    	DATE_2019_02_19 = DateUtils.parseDate(STR_2019_02_19, "yyyy-MM-dd");
    }

    /**
     * Checks the number of days between to valid dates as input
     * 
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testDaysBetweenWithTwoValidDates() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DATE_2019_01_19, DATE_2019_02_19);
        function.run(parameters);
        assertEquals(DIFFERENCE_IN_DAYS, parameters.pop());
    }
    
    /**
     * Checks the number of days between to valid date representations in RFC-3999 as strings
     * 
     * @throws org.nfunk.jep.ParseException
     */
    @Test
    public void testDaysBetweenWithTwoValidRFC3999Strings() throws org.nfunk.jep.ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_2019_01_19_RFC_3339, STR_2019_02_19_RFC_3339);
        function.run(parameters);
        assertEquals(DIFFERENCE_IN_DAYS, parameters.pop());
    }

}
