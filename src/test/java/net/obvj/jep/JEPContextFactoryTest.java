package net.obvj.jep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nfunk.jep.FunctionTable;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.functions.*;
import net.obvj.jep.functions.DateFieldGetter.DateField;
import net.obvj.jep.functions.UnaryEncryptionFunction.EncryptionAlgorithm;
import net.obvj.jep.util.DateUtils;
import net.obvj.jep.util.UtilitiesCommons;

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
     *                   private constructor via Reflection
     */
    @Test
    public void testNoInstancesAllowed() throws Exception
    {
        UtilitiesCommons.testNoInstancesAllowed(JEPContextFactory.class, IllegalStateException.class,
                "No instances allowed");
    }

    /**
     * Checks that a simple function is duly registered at JEP's Function Table
     */
    private void checkFunction(final FunctionTable table, String functionName, Class<?> clazz)
    {
        assertTrue("Table does not contain function name: " + functionName, table.containsKey(functionName));
        assertEquals(clazz, table.get(functionName).getClass());
    }

    /**
     * Checks that a multi-strategy function is dully registered at JEP's Function Table
     */
    private void checkFunction(final FunctionTable table, String functionName, Class<?> clazz, Object strategy)
    {
        checkFunction(table, functionName, clazz);
        assertEquals(strategy, ((MultiStrategyCommand) table.get(functionName)).getStrategy());
    }

    /**
     * Checks that all custom functions are available at JEP
     */
    @Test
    public void testRegisteredFunctions()
    {
        FunctionTable table = JEPContextFactory.newContext().getFunctionTable();

        // ---------------------
        // String functions
        // ---------------------

        checkFunction(table, "camel", UnaryStringFunction.class, UnaryStringFunction.Strategy.CAMEL);
        checkFunction(table, "concat", Concat.class);
        checkFunction(table, "join", Concat.class);
        checkFunction(table, "endsWith", BinaryBooleanFunction.class, BinaryBooleanFunction.Strategy.STRING_ENDS_WITH);
        checkFunction(table, "findMatch", BinaryStringFunction.class, BinaryStringFunction.Strategy.FIRST_MATCH);
        checkFunction(table, "findMatches", BinaryStringFunction.class, BinaryStringFunction.Strategy.ALL_MATCHES);
        checkFunction(table, "formatString", FormatString.class);
        checkFunction(table, "leftPad", StringPaddingFunction.class, StringPaddingFunction.Strategy.LEFT_PAD);
        checkFunction(table, "lpad", StringPaddingFunction.class, StringPaddingFunction.Strategy.LEFT_PAD);
        checkFunction(table, "lcase", UnaryStringFunction.class, UnaryStringFunction.Strategy.LOWER);
        checkFunction(table, "lower", UnaryStringFunction.class, UnaryStringFunction.Strategy.LOWER);
        checkFunction(table, "matches", BinaryBooleanFunction.class, BinaryBooleanFunction.Strategy.STRING_MATCHES);
        checkFunction(table, "normalizeString", NormalizeString.class);
        checkFunction(table, "proper", UnaryStringFunction.class, UnaryStringFunction.Strategy.PROPER);
        checkFunction(table, "replace", Replace.class, Replace.Strategy.NORMAL);
        checkFunction(table, "replaceRegex", Replace.class, Replace.Strategy.REGEX);
        checkFunction(table, "rightPad", StringPaddingFunction.class, StringPaddingFunction.Strategy.RIGHT_PAD);
        checkFunction(table, "rpad", StringPaddingFunction.class, StringPaddingFunction.Strategy.RIGHT_PAD);
        checkFunction(table, "split", BinaryStringFunction.class, BinaryStringFunction.Strategy.SPLIT);
        checkFunction(table, "startsWith", BinaryBooleanFunction.class, BinaryBooleanFunction.Strategy.STRING_STARTS_WITH);
        checkFunction(table, "trim", UnaryStringFunction.class, UnaryStringFunction.Strategy.TRIM);
        checkFunction(table, "ucase", UnaryStringFunction.class, UnaryStringFunction.Strategy.UPPER);
        checkFunction(table, "upper", UnaryStringFunction.class, UnaryStringFunction.Strategy.UPPER);

        // ---------------------
        // Date functions
        // ---------------------

        checkFunction(table, "sysdate", Now.class);
        checkFunction(table, "now", Now.class);
        checkFunction(table, "date2str", DateToString.class);
        checkFunction(table, "str2date", StringToDate.class);
        checkFunction(table, "daysBetween", DaysBetween.class);
        checkFunction(table, "endOfMonth", EndOfMonth.class);
        checkFunction(table, "isLeapYear", IsLeapYear.class);
        checkFunction(table, "year", DateFieldGetter.class, DateField.YEAR);
        checkFunction(table, "quarter", DateFieldGetter.class, DateField.QUARTER);
        checkFunction(table, "month", DateFieldGetter.class, DateField.MONTH);
        checkFunction(table, "isoWeekNumber", DateFieldGetter.class, DateField.ISO_WEEK_NUMBER);
        checkFunction(table, "weekday", DateFieldGetter.class, DateField.WEEK_DAY);
        checkFunction(table, "day", DateFieldGetter.class, DateField.DAY);
        checkFunction(table, "hour", DateFieldGetter.class, DateField.HOUR);
        checkFunction(table, "minute", DateFieldGetter.class, DateField.MINUTE);
        checkFunction(table, "second", DateFieldGetter.class, DateField.SECOND);
        checkFunction(table, "millisecond", DateFieldGetter.class, DateField.MILLISECOND);

        // ---------------------
        // Data manipulation
        // ---------------------

        checkFunction(table, "xpath", XPath.class);
        checkFunction(table, "jsonpath", JsonPath.class);

        // ---------------------
        // Statistical
        // ---------------------

        checkFunction(table, "average", Average.class);
        checkFunction(table, "avg", Average.class);
        checkFunction(table, "count", Count.class);
        checkFunction(table, "length", Count.class);
        checkFunction(table, "max", Max.class);
        checkFunction(table, "min", Min.class);

        // ---------------------
        // Random
        // ---------------------

        checkFunction(table, "uuid", UUID.class);

        // ---------------------
        // Utility
        // ---------------------

        checkFunction(table, "distinct", Distinct.class);
        checkFunction(table, "getEnv", UnarySystemFunction.class, UnarySystemFunction.Strategy.GET_ENV);
        checkFunction(table, "getSystemProperty", UnarySystemFunction.class, UnarySystemFunction.Strategy.GET_SYSTEM_PROPERTY);
        checkFunction(table, "get", Element.class);
        checkFunction(table, "isDecimal", UnaryBooleanFunction.class, UnaryBooleanFunction.Strategy.IS_DECIMAL);
        checkFunction(table, "isEmpty", IsEmpty.class);
        checkFunction(table, "isInteger", UnaryBooleanFunction.class, UnaryBooleanFunction.Strategy.IS_INTEGER);
        checkFunction(table, "readFile", ReadFile.class);
        checkFunction(table, "typeOf", TypeOf.class);
        checkFunction(table, "class", TypeOf.class);

        // ---------------------
        // Cryptography
        // ---------------------

        checkFunction(table, "md5", UnaryEncryptionFunction.class, EncryptionAlgorithm.MD5);
        checkFunction(table, "sha1", UnaryEncryptionFunction.class, EncryptionAlgorithm.SHA1);
        checkFunction(table, "sha256", UnaryEncryptionFunction.class, EncryptionAlgorithm.SHA256);
        checkFunction(table, "toBase64", UnaryEncryptionFunction.class, EncryptionAlgorithm.TO_BASE64);
        checkFunction(table, "fromBase64", UnaryEncryptionFunction.class, EncryptionAlgorithm.FROM_BASE64);

        // ---------------------
        // Web Services
        // ---------------------

        checkFunction(table, "basicAuthorizationHeader", BasicAuthorizationHeader.class);
        checkFunction(table, "httpGet", HttpGet.class);
        checkFunction(table, "http", Http.class);
        checkFunction(table, "httpHeader", HttpHeader.class);
        checkFunction(table, "httpHeaders", HttpHeader.class);
        checkFunction(table, "httpStatusCode", HttpResponseHandler.class, HttpResponseHandler.Strategy.GET_STATUS_CODE);
        checkFunction(table, "httpResponse", HttpResponseHandler.class, HttpResponseHandler.Strategy.GET_RESPONSE);

        // ---------------------
        // Math
        // ---------------------

        checkFunction(table, "arabic", Arabic.class);
        checkFunction(table, "roman", Roman.class);
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
    public void testExpressionReplaceWithInitialVariable() throws ParseException
    {
        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("myText", "call");

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("replace(myText, \"c\", \"f\")");
        String result = (String) jep.evaluate(node);
        assertEquals("fall", result.toString());
    }

    /**
     * Tests the JEP context can evaluate an expression with the replace() function with
     * assignment operator and the result can be retrieved when required
     *
     * @throws ParseException
     */
    @Test
    public void testExpressionReplaceWithAssignmentOperator() throws ParseException
    {
        JEP jep = JEPContextFactory.newContext();
        Node node = jep.parseExpression("myText = replace(\"fear\", \"f\", \"d\")");
        jep.evaluate(node);
        String result = (String) jep.getVarValue("myText");
        assertEquals("dear", result.toString());
    }

    /**
     * Tests the JEP context can evaluate an expression with the replace() function with
     * initial variable and assignment operator. The result shall be retrieved when required
     *
     * @throws ParseException
     */
    @Test
    public void testExpressionReplaceWithInitialVariableAndAssignmentOperator() throws ParseException
    {
        Map<String, Object> myVariables = new HashMap<>();
        myVariables.put("sourceText", "bare");

        JEP jep = JEPContextFactory.newContext(myVariables);
        Node node = jep.parseExpression("newText = replace(sourceText, \"b\", \"c\")");
        jep.evaluate(node);
        String result = (String) jep.getVarValue("newText");
        assertEquals("care", result.toString());
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
    public void testExpressionAverageForStringRepresentationOfArrayWithTwoNumbers()
            throws ParseException, java.text.ParseException
    {
        JEP jep = JEPContextFactory.newContext();
        Node node = jep.parseExpression("average(\"[2,3]\")");
        assertEquals(2.5, jep.evaluate(node));
    }

    /**
     * Tests that a proper exception is thrown when a non-annotated function is passed to the
     * addAnnotatedFunction() method
     */
    @Test(expected = IllegalStateException.class)
    public void testAddAnnotatedFunctionWithoutNoFunctionAnnotation()
    {
        // A dummy function without annotation
        PostfixMathCommand function = new PostfixMathCommand();
        JEPContextFactory.addAnnotatedFunction(new JEP(), function);
    }

    /**
     * Tests that a proper exception is thrown when a non-annotated multi-strategy function is
     * passed to the addAnnotatedFunction() method
     */
    @Test(expected = IllegalStateException.class)
    public void testAddAnnotatedMultiStrategyFunctionWithoutNoFunctionAnnotation()
    {
        // A dummy function without annotation
        JEPContextFactory.addAnnotatedFunction(new JEP(),
                new DummyMultiStrategyCommand(DummyMultiStrategyCommand.Strategy.TYPE1));
    }

}
