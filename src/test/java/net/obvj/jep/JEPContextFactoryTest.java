package net.obvj.jep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nfunk.jep.FunctionTable;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.*;
import net.obvj.jep.functions.FindMatches.ReturnStrategy;
import net.obvj.jep.functions.Replace.SearchStrategy;
import net.obvj.jep.util.DateUtils;

public class JEPContextFactoryTest
{
    private static final double DOUBLE_TRUE = 1d;

    /**
     * Tests that no instances of this factory are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test(expected = InvocationTargetException.class)
    public void testNoInstancesAllowed() throws Exception
    {
        try
        {
            Constructor<JEPContextFactory> constructor = JEPContextFactory.class.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));

            constructor.setAccessible(true);
            constructor.newInstance();
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(IllegalStateException.class, cause.getClass());
            assertEquals("No instances allowed", cause.getMessage());
            throw ite;
        }
    }

    /**
     * Checks that all custom functions are available at JEP
     */
    @Test
    public void testRegisteredFunctions()
    {
        JEP jep = JEPContextFactory.newContext();
        FunctionTable table = jep.getFunctionTable();

        // ---------------------
        // String functions
        // ---------------------

        assertTrue(table.containsKey("concat"));
        assertEquals(Concat.class, table.get("concat").getClass());

        assertTrue(table.containsKey("findMatch"));
        assertEquals(FindMatches.class, table.get("findMatch").getClass());
        assertEquals(ReturnStrategy.FIRST_MATCH, ((FindMatches) table.get("findMatch")).getReturnStrategy());

        assertTrue(table.containsKey("findMatches"));
        assertEquals(FindMatches.class, table.get("findMatches").getClass());
        assertEquals(ReturnStrategy.ALL_MATCHES, ((FindMatches) table.get("findMatches")).getReturnStrategy());

        assertTrue(table.containsKey("lower"));
        assertEquals(Lower.class, table.get("lower").getClass());

        assertTrue(table.containsKey("matches"));
        assertEquals(FindMatches.class, table.get("matches").getClass());
        assertEquals(ReturnStrategy.TRUE_IF_MATCHES, ((FindMatches) table.get("matches")).getReturnStrategy());

        assertTrue(table.containsKey("normalizeString"));
        assertEquals(NormalizeString.class, table.get("normalizeString").getClass());

        assertTrue(table.containsKey("replace"));
        assertEquals(Replace.class, table.get("replace").getClass());
        assertEquals(SearchStrategy.NORMAL, ((Replace) table.get("replace")).getSearchStrategy());

        assertTrue(table.containsKey("replaceRegex"));
        assertEquals(Replace.class, table.get("replaceRegex").getClass());
        assertEquals(SearchStrategy.REGEX, ((Replace) table.get("replaceRegex")).getSearchStrategy());

        assertTrue(table.containsKey("trim"));
        assertEquals(Trim.class, table.get("trim").getClass());

        assertTrue(table.containsKey("upper"));
        assertEquals(Upper.class, table.get("upper").getClass());

        // ---------------------
        // Date functions
        // ---------------------

        assertTrue(table.containsKey("now"));
        assertEquals(Now.class, table.get("now").getClass());

        assertTrue(table.containsKey("date2str"));
        assertEquals(DateToString.class, table.get("date2str").getClass());

        assertTrue(table.containsKey("str2date"));
        assertEquals(StringToDate.class, table.get("str2date").getClass());

        assertTrue(table.containsKey("daysBetween"));
        assertEquals(DaysBetween.class, table.get("daysBetween").getClass());

        assertTrue(table.containsKey("isLeapYear"));
        assertEquals(IsLeapYear.class, table.get("isLeapYear").getClass());

        // ---------------------
        // Data manipulation
        // ---------------------

        assertTrue(table.containsKey("xpath"));
        assertEquals(XPath.class, table.get("xpath").getClass());

        assertTrue(table.containsKey("jsonpath"));
        assertEquals(JsonPath.class, table.get("jsonpath").getClass());

        // ---------------------
        // Statistical
        // ---------------------

        assertTrue(table.containsKey("count"));
        assertEquals(Count.class, table.get("count").getClass());

        assertTrue(table.containsKey("max"));
        assertEquals(Max.class, table.get("max").getClass());

        assertTrue(table.containsKey("min"));
        assertEquals(Min.class, table.get("min").getClass());

        // ---------------------
        // Random
        // ---------------------

        assertTrue(table.containsKey("uuid"));
        assertEquals(UUID.class, table.get("uuid").getClass());

        // ---------------------
        // Utility
        // ---------------------

        assertTrue(table.containsKey("getEnv"));
        assertEquals(EnvironmentVariableReader.class, table.get("getEnv").getClass());

        assertTrue(table.containsKey("getSystemProperty"));
        assertEquals(SystemPropertyReader.class, table.get("getSystemProperty").getClass());

        assertTrue(table.containsKey("getElement"));
        assertEquals(Element.class, table.get("getElement").getClass());

        assertTrue(table.containsKey("isEmpty"));
        assertEquals(IsEmpty.class, table.get("isEmpty").getClass());
    }

    /**
     * Tests the JEP context can evaluate an expression with the replace() function
     *
     * @throws ParseException
     */
    @Test
    public void testExpressionReplace() throws ParseException
    {
        JEP jep = JEPContextFactory.newContext();
        Node node = jep.parseExpression("replace(\"foo\", \"oo\", \"ee\")");
        String result = (String) jep.evaluate(node);
        assertEquals("fee", result.toString());
    }

    /**
     * Tests the JEP context can evaluate an expression with the replace() function and a
     * source variable
     *
     * @throws ParseException
     */
    @Test
    public void testExpressionReplaceWithVariable() throws ParseException
    {
        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("myText", "foo");

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("replace(myText, \"oo\", \"ee\")");
        String result = (String) jep.evaluate(node);
        assertEquals("fee", result.toString());
    }

    /**
     * Tests the JEP context can evaluate an expression for the str2date() function with a
     * single paramater
     *
     * @throws ParseException
     */
    @Test
    public void testExpressionStringToDate() throws ParseException
    {
        String inputDate = "2015-10-03T08:00:01.123Z";
        Date expectedDate = DateUtils.parseDate(inputDate);

        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("strDate", inputDate);

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("str2date(strDate)");
        Date result = (Date) jep.evaluate(node);
        assertEquals(expectedDate, result);
    }

    /**
     * Tests the JEP context can evaluate an expression for the str2date() function with two
     * paramaters
     *
     * @throws ParseException
     * @throws java.text.ParseException
     */
    @Test
    public void testExpressionStringToDateWithTwoParams() throws ParseException, java.text.ParseException
    {
        String inputDate = "2015/10/03";
        String pattern = "yyyy/MM/dd";
        Date expectedDate = DateUtils.parseDate(inputDate, pattern);

        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("strDate", inputDate);

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("str2date(strDate, \"" + pattern + "\")");
        Date result = (Date) jep.evaluate(node);
        assertEquals(expectedDate, result);
    }

    /**
     * Tests the JEP context can evaluate an expression for the isEmpty() function for a
     * non-empty JSON array as string
     *
     * @throws ParseException
     * @throws java.text.ParseException
     */
    @Test
    public void testExpressionIsEmptyForJsonArrayAsString() throws ParseException, java.text.ParseException
    {
        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("myJSONArray", "[1,2]");

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("!isEmpty(myJSONArray)");
        assertEquals(DOUBLE_TRUE, jep.evaluate(node));
    }

    /**
     * Tests the JEP context can evaluate an expression for the isEmpty() function for an
     * empty JSON array as string
     *
     * @throws ParseException
     * @throws java.text.ParseException
     */
    @Test
    public void testExpressionIsEmptyForEmptyJsonArrayAsString() throws ParseException, java.text.ParseException
    {
        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("myJSONArray", "[]");

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("isEmpty(myJSONArray)");
        assertEquals(DOUBLE_TRUE, jep.evaluate(node));
    }

}
