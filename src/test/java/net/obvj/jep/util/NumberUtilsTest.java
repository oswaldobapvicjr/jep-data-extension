package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    @Test
    public void testNoInstancesAllowed() throws Exception
    {
        UtilitiesCommons.testNoInstancesAllowed(NumberUtils.class, IllegalStateException.class, "Utility class");
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
     * Tests the parseInt method with an unsupported object as parameter
     */
    @Test(expected = NumberFormatException.class)
    public void testParseIntWithUnsupportedObject()
    {
        NumberUtils.parseInt(new Object());
    }

    /**
     * Tests the parseInt method with a null object as parameter
     */
    @Test(expected = NumberFormatException.class)
    public void testParseIntWithNullObject()
    {
        NumberUtils.parseInt(null);
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

    /**
     * Tests the parseDouble method with an unsupported object as parameter
     */
    @Test(expected = NumberFormatException.class)
    public void testParseDoubleWithUnsupportedObject()
    {
        NumberUtils.parseDouble(new Object());
    }

    /**
     * Tests the parseDouble method with a null object as parameter
     */
    @Test(expected = NumberFormatException.class)
    public void testParseDoubleWithNullObject()
    {
        NumberUtils.parseDouble(null);
    }

}
