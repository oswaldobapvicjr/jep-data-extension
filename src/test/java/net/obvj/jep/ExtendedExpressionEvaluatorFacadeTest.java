package net.obvj.jep;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nfunk.jep.ParseException;

/**
 * Unit tests for the {@link ExtendedExpressionEvaluatorFacade} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class ExtendedExpressionEvaluatorFacadeTest
{
    private static final String DATE1_KEY = "date1";
    private static final String DATE2_KEY = "date2";
    private static final String DATE1_VALUE = "2018-12-20T09:10:00.123456789Z";
    private static final String DATE2_VALUE = "2018-12-20T12:10:00.987654321Z";

    private static final Map<String, Object> VARIABLES_MAP = new HashMap<>();
    static
    {
        VARIABLES_MAP.put(DATE1_KEY, DATE1_VALUE);
        VARIABLES_MAP.put(DATE2_KEY, DATE2_VALUE);
    }

    private static final String EXPRESSION_DATE1_LOWER_THAN_DATE2 = "date1<date2";
    private static final String EXPRESSION_DATE1_EQUALS_DATE2 = "date1==date2";
    private static final String EXPRESSION_IF_DATE2_GREATER_THAN_DATE1 = "if(date2>date1,\"true\",\"false\")";

    private static final double FALSE = 0d;
    private static final double TRUE = 1d;

    /**
     * Tests expression evaluation with an expression that performs date comparison with the
     * overloaded "lower-than" operator
     *
     * @throws ParseException
     */
    @Test
    public void testEvaluateExpressionComparingStringDatesLowerThan() throws ParseException
    {
        assertEquals(TRUE,
                ExtendedExpressionEvaluatorFacade.evaluate(EXPRESSION_DATE1_LOWER_THAN_DATE2, VARIABLES_MAP));
    }

    /**
     * Tests expression evaluation with an expression that performs date comparison with the
     * overloaded "equal" operator
     *
     * @throws ParseException
     */
    @Test
    public void testEvaluateExpressionComparingStringDatesEqual() throws ParseException
    {
        assertEquals(FALSE, ExtendedExpressionEvaluatorFacade.evaluate(EXPRESSION_DATE1_EQUALS_DATE2, VARIABLES_MAP));
    }

    @Test
    public void testEvaluateExpressionIfDate2GreaterThanDate1() throws ParseException
    {
        assertEquals("true",
                ExtendedExpressionEvaluatorFacade.evaluate(EXPRESSION_IF_DATE2_GREATER_THAN_DATE1, VARIABLES_MAP));
    }
}
