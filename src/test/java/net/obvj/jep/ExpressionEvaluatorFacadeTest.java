package net.obvj.jep;

import static net.obvj.junit.utils.matchers.InstantiationNotAllowedMatcher.instantiationNotAllowed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nfunk.jep.ParseException;

/**
 * Unit tests for the {@link ExpressionEvaluatorFacade} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class ExpressionEvaluatorFacadeTest
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
    private static final String EXPRESSION_IF_DATE2_GREATER_THAN_DATE1 = "if(date2>date1,\"greater\",\"lower\")";

    private static final String USER_JSON_KEY = "userJson";
    private static final String USER_JSON = "{\"id\":1,\"firstName\":\"John\"}";

    private static final String MESSAGE_TEMPLATE_KEY = "messageTemplate";
    private static final String MESSAGE_TEMPLATE = "Hello, %NAME%!";

    private static final String EXPRESSION_HELLO_MESSAGE =
            "if(!isEmpty(userJson),"
                    + "replace(messageTemplate, \"%NAME%\", jsonpath(userJson, \"$.firstName\")),"
                    + "replace(messageTemplate, \"%NAME%\", \"guest\"))";

    private static final String EMPTY_ARRAY = "[]";
    private static final double FALSE = 0d;
    private static final double TRUE = 1d;

    /**
     * Tests that no instances of this facade are created
     */
    @Test
    public void testNoInstancesAllowed()
    {
        assertThat(ExpressionEvaluatorFacade.class, instantiationNotAllowed());
    }

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
                ExpressionEvaluatorFacade.evaluate(EXPRESSION_DATE1_LOWER_THAN_DATE2, VARIABLES_MAP));
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
        assertEquals(FALSE, ExpressionEvaluatorFacade.evaluate(EXPRESSION_DATE1_EQUALS_DATE2, VARIABLES_MAP));
    }

    /**
     * Tests expression evaluation with an expression that performs date comparison with the
     * overloaded "equal" operator
     *
     * @throws ParseException
     */
    @Test
    public void testEvaluateExpressionIfDate2GreaterThanDate1() throws ParseException
    {
        assertEquals("greater",
                ExpressionEvaluatorFacade.evaluate(EXPRESSION_IF_DATE2_GREATER_THAN_DATE1, VARIABLES_MAP));
    }

    /**
     * Tests expression evaluation updateSourceMap flag enabled
     *
     * @throws ParseException
     */
    @Test
    public void testEvaluateExpressionAndUpdateSource() throws ParseException
    {
        Map<String, Object> map = new HashMap<>();
        ExpressionEvaluatorFacade.evaluate("total=1+.5", map, true);
        assertEquals(1.5, map.get("total"));
    }

    @Test
    public void testEvaluateExpressionHelloUser() throws ParseException
    {
        Map<String, Object> map = new HashMap<>();
        map.put(MESSAGE_TEMPLATE_KEY, MESSAGE_TEMPLATE);
        map.put(USER_JSON_KEY, USER_JSON);

        Object result = ExpressionEvaluatorFacade.evaluate(EXPRESSION_HELLO_MESSAGE, map);
        assertEquals("Hello, John!", result);
    }

    @Test
    public void testEvaluateExpressionHelloGuest() throws ParseException
    {
        Map<String, Object> map = new HashMap<>();
        map.put(MESSAGE_TEMPLATE_KEY, MESSAGE_TEMPLATE);
        map.put(USER_JSON_KEY, EMPTY_ARRAY);

        Object result = ExpressionEvaluatorFacade.evaluate(EXPRESSION_HELLO_MESSAGE, map);
        assertEquals("Hello, guest!", result);
    }
}
