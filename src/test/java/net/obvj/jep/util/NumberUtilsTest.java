package net.obvj.jep.util;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class NumberUtilsTest
{
    private static final String STRING_A = "A";
    private static final String STRING_10 = "10";
    private static final String STRING_10_2 = "10.2";
    private static final Integer INT_10 = Integer.valueOf(10);
    private static final Double DOUBLE_10 = Double.valueOf(10.0);
    private static final Double DOUBLE_10_2 = Double.valueOf(10.2);

    /**
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test(expected = InvocationTargetException.class)
    public void testNoInstancesAllowed() throws Exception
    {
        try
        {
            Constructor<NumberUtils> constructor = NumberUtils.class.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));

            constructor.setAccessible(true);
            constructor.newInstance();
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(IllegalStateException.class, cause.getClass());
            assertEquals("Utility class", cause.getMessage());
            throw ite;
        }
    }

    /**
     * Tests valid string conversion to Integer
     */
    @Test
    public void testGetIntegerFromStringWithValidString()
    {
        assertEquals(INT_10, NumberUtils.getIntegerFromString(STRING_10));
    }

    /**
     * This test secures that no error is thrown by the NumberUtils class when
     * getIntegerFromString is called with an empty string
     */
    @Test
    public void testGetIntegerFromStringWithEmptyString()
    {
        assertNull(NumberUtils.getIntegerFromString(StringUtils.EMPTY));
    }

    /**
     * This test secures that an specific exception is thrown by the NumberUtils class when
     * getIntegerFromString is called with a string that is not convertible
     */
    @Test(expected = NumberFormatException.class)
    public void testGetIntegerFromStringWithInvalidString()
    {
        NumberUtils.getIntegerFromString(STRING_A);
    }

    /**
     * Tests the isInteger method with an integer parameter
     */
    @Test
    public void testIsIntegerWithValidInteger()
    {
        assertTrue(NumberUtils.isInteger(INT_10));
    }

    /**
     * Tests the isInteger method with a valid string as parameter
     */
    @Test
    public void testIsIntegerWithValidString()
    {
        assertTrue(NumberUtils.isInteger(STRING_10));
    }

    /**
     * Tests the isInteger method with an empty string as parameter
     */
    @Test
    public void testIsIntegerWithEmptyString()
    {
        assertFalse(NumberUtils.isInteger(StringUtils.EMPTY));
    }

    /**
     * Tests the isInteger method with a string that is not convertible to integer as
     * parameter
     */
    @Test
    public void testIsIntegerWithInvalidString()
    {
        assertFalse(NumberUtils.isInteger(STRING_A));
    }

    /**
     * Tests the isInteger method with a Double object that contains no decimal places
     */
    @Test
    public void testIsIntegerWithADoubleObjectWithoutDecimalPlaces()
    {
        assertTrue(NumberUtils.isInteger(DOUBLE_10));
    }

    /**
     * Tests the isInteger method with a Double object containing decimal places
     */
    @Test
    public void testIsIntegerWithADoubleObjectWithDecimalPlaces()
    {
        assertFalse(NumberUtils.isInteger(DOUBLE_10_2));
    }

    /**
     * Tests the isInteger method with a String object representing a double containing
     * decimal places
     */
    @Test
    public void testIsIntegerWithAStringRepresentationOfDoubleWithDecimalPlaces()
    {
        assertFalse(NumberUtils.isInteger(STRING_10_2));
    }

    /**
     * Tests the parseInt method with an integer parameter
     */
    @Test
    public void testParseIntWithValidInteger()
    {
        assertEquals(INT_10.intValue(), NumberUtils.parseInt(INT_10));
    }

    /**
     * Tests the parseInt method with a valid string as parameter
     */
    @Test
    public void testParseIntWithValidString()
    {
        assertEquals(INT_10.intValue(), NumberUtils.parseInt(STRING_10));
    }

    /**
     * Tests the parseInt method with an empty string as parameter
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseIntWithEmptyString()
    {
        NumberUtils.parseInt(StringUtils.EMPTY);
    }

    /**
     * Tests the parseInt method with a string that is not convertible to integer as parameter
     */
    @Test(expected = NumberFormatException.class)
    public void testParseIntWithInvalidString()
    {
        NumberUtils.parseInt(STRING_A);
    }

    /**
     * Tests the isInteger method with a Double object that contains no decimal places
     */
    @Test
    public void testParseIntWithADoubleObjectWithoutDecimalPlaces()
    {
        assertEquals(INT_10.intValue(), NumberUtils.parseInt(DOUBLE_10));
    }

    /**
     * Tests the isInteger method with a Double object containing decimal places
     */
    @Test
    public void testParseIntWithADoubleObjectWithDecimalPlaces()
    {
        assertEquals(INT_10.intValue(), NumberUtils.parseInt(DOUBLE_10_2));
    }

    /**
     * Tests the parseInt method with a String object representing a double containing decimal
     * places
     */
    @Test
    public void testParseIntWithAStringRepresentationOfDoubleWithDecimalPlaces()
    {
        assertEquals(INT_10.intValue(), NumberUtils.parseInt(STRING_10_2));
    }

    /**
     * Tests the isNumber method with an integer parameter
     */
    @Test
    public void testIsNumber()
    {
        assertTrue(NumberUtils.isNumber(INT_10));
    }

    /**
     * Tests the isNumber method with string representing an integer as parameter
     */
    @Test
    public void testIsNumberWithStringRepresentingInteger()
    {
        assertTrue(NumberUtils.isNumber(STRING_10));
    }

    /**
     * Tests the isNumber method with an empty string as parameter
     */
    @Test
    public void testIsNumberWithEmptyString()
    {
        assertFalse(NumberUtils.isNumber(StringUtils.EMPTY));
    }

    /**
     * Tests the isNumber method with a string that is not convertible
     */
    @Test
    public void testIsNumberWithInvalidString()
    {
        assertFalse(NumberUtils.isNumber(STRING_A));
    }

    /**
     * Tests the isNumber method with a Double object that contains no decimal places
     */
    @Test
    public void testIsNumberWithADoubleObjectWithoutDecimalPlaces()
    {
        assertTrue(NumberUtils.isNumber(DOUBLE_10));
    }

    /**
     * Tests the isNumber method with a Double object containing decimal places
     */
    @Test
    public void testIsNumberWithADoubleObjectWithDecimalPlaces()
    {
        assertTrue(NumberUtils.isNumber(DOUBLE_10_2));
    }

    /**
     * Tests the isNumber method with a String object representing a double containing decimal
     * places
     */
    @Test
    public void testIsNumberWithAStringRepresentationOfDoubleWithDecimalPlaces()
    {
        assertTrue(NumberUtils.isNumber(STRING_10_2));
    }

}
