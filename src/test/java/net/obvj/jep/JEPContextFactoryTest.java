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
import net.obvj.jep.functions.BinaryBooleanFunction.Operation;
import net.obvj.jep.functions.DateFieldGetter.DateField;
import net.obvj.jep.functions.FindMatches.ReturnStrategy;
import net.obvj.jep.functions.HttpResponseHandler.HttpResponseHandlerStrategy;
import net.obvj.jep.functions.Replace.SearchStrategy;
import net.obvj.jep.functions.UnaryEncryptionFunction.EncryptionAlgorithm;
import net.obvj.jep.functions.UnarySystemFunction.Strategy;
import net.obvj.jep.util.DateUtils;

public class JEPContextFactoryTest
{
    // Test data
    private static final double DOUBLE_TRUE = 1d;

    private static final int INTEGER_123 = 123;
    private static final double DOUBLE_4 = 4d;
    private static final Date DATE_1 = new Date();

    /**
     * Tests that no instances of this factory are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     * private constructor via Reflection
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

        assertTrue(table.containsKey("endsWith"));
        assertEquals(BinaryBooleanFunction.class, table.get("endsWith").getClass());
        assertEquals(Operation.STRING_ENDS_WITH, ((BinaryBooleanFunction) table.get("endsWith")).getOperation());

        assertTrue(table.containsKey("findMatch"));
        assertEquals(FindMatches.class, table.get("findMatch").getClass());
        assertEquals(ReturnStrategy.FIRST_MATCH, ((FindMatches) table.get("findMatch")).getReturnStrategy());

        assertTrue(table.containsKey("findMatches"));
        assertEquals(FindMatches.class, table.get("findMatches").getClass());
        assertEquals(ReturnStrategy.ALL_MATCHES, ((FindMatches) table.get("findMatches")).getReturnStrategy());

        assertTrue(table.containsKey("lower"));
        assertEquals(Lower.class, table.get("lower").getClass());

        assertTrue(table.containsKey("matches"));
        assertEquals(BinaryBooleanFunction.class, table.get("matches").getClass());
        assertEquals(Operation.STRING_MATCHES, ((BinaryBooleanFunction) table.get("matches")).getOperation());

        assertTrue(table.containsKey("normalizeString"));
        assertEquals(NormalizeString.class, table.get("normalizeString").getClass());

        assertTrue(table.containsKey("startsWith"));
        assertEquals(BinaryBooleanFunction.class, table.get("startsWith").getClass());
        assertEquals(Operation.STRING_STARTS_WITH, ((BinaryBooleanFunction) table.get("startsWith")).getOperation());

        assertTrue(table.containsKey("replace"));
        assertEquals(Replace.class, table.get("replace").getClass());
        assertEquals(SearchStrategy.NORMAL, ((Replace) table.get("replace")).getSearchStrategy());

        assertTrue(table.containsKey("isLeapYear"));
        assertEquals(IsLeapYear.class, table.get("isLeapYear").getClass());

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

        assertTrue(table.containsKey("endOfMonth"));
        assertEquals(EndOfMonth.class, table.get("endOfMonth").getClass());

        assertTrue(table.containsKey("isLeapYear"));
        assertEquals(IsLeapYear.class, table.get("isLeapYear").getClass());

        assertTrue(table.containsKey("year"));
        assertEquals(DateFieldGetter.class, table.get("year").getClass());
        assertEquals(DateField.YEAR, ((DateFieldGetter) table.get("year")).getDateField());

        assertTrue(table.containsKey("quarter"));
        assertEquals(DateFieldGetter.class, table.get("quarter").getClass());
        assertEquals(DateField.QUARTER, ((DateFieldGetter) table.get("quarter")).getDateField());

        assertTrue(table.containsKey("month"));
        assertEquals(DateFieldGetter.class, table.get("month").getClass());
        assertEquals(DateField.MONTH, ((DateFieldGetter) table.get("month")).getDateField());

        assertTrue(table.containsKey("isoWeekNumber"));
        assertEquals(DateFieldGetter.class, table.get("isoWeekNumber").getClass());
        assertEquals(DateField.ISO_WEEK_NUMBER, ((DateFieldGetter) table.get("isoWeekNumber")).getDateField());

        assertTrue(table.containsKey("day"));
        assertEquals(DateFieldGetter.class, table.get("day").getClass());
        assertEquals(DateField.DAY, ((DateFieldGetter) table.get("day")).getDateField());

        assertTrue(table.containsKey("hour"));
        assertEquals(DateFieldGetter.class, table.get("hour").getClass());
        assertEquals(DateField.HOUR, ((DateFieldGetter) table.get("hour")).getDateField());

        assertTrue(table.containsKey("minute"));
        assertEquals(DateFieldGetter.class, table.get("minute").getClass());
        assertEquals(DateField.MINUTE, ((DateFieldGetter) table.get("minute")).getDateField());

        assertTrue(table.containsKey("second"));
        assertEquals(DateFieldGetter.class, table.get("second").getClass());
        assertEquals(DateField.SECOND, ((DateFieldGetter) table.get("second")).getDateField());

        assertTrue(table.containsKey("millisecond"));
        assertEquals(DateFieldGetter.class, table.get("millisecond").getClass());
        assertEquals(DateField.MILLISECOND, ((DateFieldGetter) table.get("millisecond")).getDateField());

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

        assertTrue(table.containsKey("average"));
        assertEquals(Average.class, table.get("average").getClass());

        assertTrue(table.containsKey("count"));
        assertEquals(Count.class, table.get("count").getClass());

        assertTrue(table.containsKey("length"));
        assertEquals(Count.class, table.get("length").getClass());

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
        assertEquals(UnarySystemFunction.class, table.get("getEnv").getClass());
        assertEquals(Strategy.GET_ENV, ((UnarySystemFunction) table.get("getEnv")).getStrategy());

        assertTrue(table.containsKey("getSystemProperty"));
        assertEquals(UnarySystemFunction.class, table.get("getSystemProperty").getClass());
        assertEquals(Strategy.GET_SYSTEM_PROPERTY, ((UnarySystemFunction) table.get("getSystemProperty")).getStrategy());

        assertTrue(table.containsKey("get"));
        assertEquals(Element.class, table.get("get").getClass());

        assertTrue(table.containsKey("isEmpty"));
        assertEquals(IsEmpty.class, table.get("isEmpty").getClass());

        assertTrue(table.containsKey("readFile"));
        assertEquals(ReadFile.class, table.get("readFile").getClass());

        assertTrue(table.containsKey("typeOf"));
        assertEquals(TypeOf.class, table.get("typeOf").getClass());

        // ---------------------
        // Cryptography
        // ---------------------

        assertTrue(table.containsKey("md5"));
        assertEquals(UnaryEncryptionFunction.class, table.get("md5").getClass());
        assertEquals(EncryptionAlgorithm.MD5, ((UnaryEncryptionFunction) table.get("md5")).getEncryptionAlgorithm());

        assertTrue(table.containsKey("sha1"));
        assertEquals(UnaryEncryptionFunction.class, table.get("sha1").getClass());
        assertEquals(EncryptionAlgorithm.SHA1, ((UnaryEncryptionFunction) table.get("sha1")).getEncryptionAlgorithm());

        assertTrue(table.containsKey("sha256"));
        assertEquals(UnaryEncryptionFunction.class, table.get("sha256").getClass());
        assertEquals(EncryptionAlgorithm.SHA256, ((UnaryEncryptionFunction) table.get("sha256")).getEncryptionAlgorithm());

        // ---------------------
        // Web Services
        // ---------------------
        assertTrue(table.containsKey("httpGet"));
        assertEquals(HttpGet.class, table.get("httpGet").getClass());

        assertTrue(table.containsKey("http"));
        assertEquals(Http.class, table.get("http").getClass());

        assertTrue(table.containsKey("httpStatusCode"));
        assertEquals(HttpResponseHandler.class, table.get("httpStatusCode").getClass());
        assertEquals(HttpResponseHandlerStrategy.GET_STATUS_CODE,
                ((HttpResponseHandler) table.get("httpStatusCode")).getStrategy());

        assertTrue(table.containsKey("httpResponse"));
        assertEquals(HttpResponseHandler.class, table.get("httpResponse").getClass());
        assertEquals(HttpResponseHandlerStrategy.GET_RESPONSE,
                ((HttpResponseHandler) table.get("httpResponse")).getStrategy());
    }

    /**
     * Tests that a new context is created with initial variables
     */
    @Test
    public void testNewContextWithInitialVariables()
    {
        Map<String, Object> variables = new HashMap<>();
        variables.put("integer1", INTEGER_123);
        variables.put("double1", DOUBLE_4);

        JEP jep = JEPContextFactory.newContext(variables);

        assertEquals(2, jep.getSymbolTable().size());
        assertEquals(INTEGER_123, jep.getVarValue("integer1"));
        assertEquals(DOUBLE_4, jep.getVarValue("double1"));
    }

    /**
     * Tests that all input variables are added to JEP symbols table
     */
    @Test
    public void testAddVariables()
    {
        JEP jep = JEPContextFactory.newContext();

        assertEquals(0, jep.getSymbolTable().size());

        Map<String, Object> variables = new HashMap<>();
        variables.put("integer1", INTEGER_123);
        variables.put("double1", DOUBLE_4);
        variables.put("date1", DATE_1);

        JEPContextFactory.addVariables(jep, variables);

        assertEquals(3, jep.getSymbolTable().size());
        assertEquals(INTEGER_123, jep.getVarValue("integer1"));
        assertEquals(DOUBLE_4, jep.getVarValue("double1"));
        assertEquals(DATE_1, jep.getVarValue("date1"));
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

    /**
     * Tests the JEP context can evaluate an expression average("[2,3]")
     *
     * @throws ParseException
     * @throws java.text.ParseException
     */
    @Test
    public void testExpressionAverageForStringRepresentationOfArrayWithTwoNumbers() throws ParseException, java.text.ParseException
    {
        JEP jep = JEPContextFactory.newContext();
        Node node = jep.parseExpression("average(\"[2,3]\")");
        assertEquals(2.5, jep.evaluate(node));
    }

}
