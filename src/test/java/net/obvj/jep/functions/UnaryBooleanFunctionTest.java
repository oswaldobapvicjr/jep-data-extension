package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.UnaryBooleanFunction.Strategy;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link UnaryBooleanFunction} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryBooleanFunctionTest
{
    private static final String STRING_A = "A";
    private static final String STRING_10 = "10";
    private static final String STRING_10_0 = "10.0";
    private static final String STRING_10_2 = "10.2";
    private static final int INT_10 = 10;
    private static final double DOUBLE_10 = 10.0;
    private static final double DOUBLE_10_2 = 10.2;

    /// Test subjects
    private static UnaryBooleanFunction funcIsInteger = new UnaryBooleanFunction(Strategy.IS_INTEGER);
    private static UnaryBooleanFunction funcIsDecimal = new UnaryBooleanFunction(Strategy.IS_DECIMAL);

    @Test
    public void testIsIntegerForAValidInteger() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(INT_10);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.TRUE, parameters.pop());
    }

    @Test
    public void testIsIntegerForADoubleContainingNoDecimal() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DOUBLE_10);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.TRUE, parameters.pop());
    }

    @Test
    public void testIsIntegerForADoubleContainingDecimal() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DOUBLE_10_2);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsIntegerForAValidIntegerAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_10);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.TRUE, parameters.pop());
    }

    @Test
    public void testIsIntegerForADoubleContainingNoDecimalAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_10_0);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.TRUE, parameters.pop());
    }

    @Test
    public void testIsIntegerForADoubleContainingDecimalAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_10_2);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsIntegerForAnInvalidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_A);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsIntegerForANull() throws ParseException
    {
        Object object = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(object);
        funcIsInteger.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }
    
    @Test
    public void testIsDecimalForADoubleContainingDecimal() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DOUBLE_10_2);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.TRUE, parameters.pop());
    }

    @Test
    public void testIsDecimalForADoubleContainingNoDecimal() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(DOUBLE_10);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsDecimalForAnInteger() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(INT_10);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsDecimalForADoubleContainingDecimalAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_10_2);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.TRUE, parameters.pop());
    }

    @Test
    public void testIsDecimalForADoubleContainingNoDecimalAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_10_0);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsDecimalForAnIntegerAsString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_10);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsDecimalForAnInvalidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STRING_A);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

    @Test
    public void testIsDecimalForANull() throws ParseException
    {
        Object object = null;
        Stack<Object> parameters = CollectionsUtils.newParametersStack(object);
        funcIsDecimal.run(parameters);
        assertEquals(UnaryBooleanFunction.FALSE, parameters.pop());
    }

}
