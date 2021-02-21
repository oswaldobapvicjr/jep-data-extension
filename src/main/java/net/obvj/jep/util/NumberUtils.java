package net.obvj.jep.util;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

/**
 * A utility class for working with numbers.
 *
 * @author oswaldo.bapvic.jr
 */
public class NumberUtils
{
    private NumberUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets an integer value from a {@code String}. If the string contains a representation of
     * a double value, the decimal places are truncated.
     *
     * @param stringValue the string to get the integer value
     * @return the integer value or null if the string is null or empty
     */
    public static Integer getIntegerFromString(String stringValue)
    {
        if (StringUtils.isEmpty(stringValue))
        {
            return null;
        }
        return Integer.valueOf(Double.valueOf(stringValue).intValue());
    }

    /**
     * Checks if the specified Object represents an integer.
     *
     * @param object the Object to be checked
     * @return true if is integer otherwise false
     */
    public static boolean isInteger(Object object)
    {
        if (isNumber(object))
        {
            Double doubleValue = parseDouble(object);
            return isInteger(doubleValue);
        }
        return false;
    }

    /**
     * Checks if a {@code String} represents an integer.
     *
     * @param stringValue the string to be checked
     * @return true if is integer otherwise false
     */
    public static boolean isInteger(String stringValue)
    {
        try
        {
            return isInteger(Double.valueOf(stringValue));
        }
        catch (NumberFormatException exception)
        {
            return false;
        }
    }

    /**
     * Checks if a {@code String} represents a number with decimal.
     *
     * @param stringValue the string to be checked
     * @return true if is decimal otherwise false
     */
    public static boolean isDecimal(String stringValue)
    {
        try
        {
            return isDecimal(Double.valueOf(stringValue));
        }
        catch (NumberFormatException exception)
        {
            return false;
        }
    }

    /**
     * Checks if a {@code Number} is an integer.
     *
     * @param numberValue the number to be checked
     * @return true if is integer otherwise false
     */
    public static boolean isInteger(Number numberValue)
    {
        return numberValue != null && numberValue.doubleValue() % 1 == 0;
    }

    /**
     * Checks if a {@code Number} contains a decimal.
     *
     * @param numberValue the number to be checked
     * @return true if is decimal otherwise false
     */
    public static boolean isDecimal(Number numberValue)
    {
        return numberValue != null && numberValue.doubleValue() % 1 != 0;
    }

    /**
     * Converts an Object to a integer. If the object is a Number or String representation of
     * Number containing decimal places, the decimal places will be truncated.
     *
     * @param object the object to be parsed
     * @return an integer number or null if the string is null or empty
     * @throws NumberFormatException if the object cannot be converted into integer
     */
    public static int parseInt(Object object)
    {
        if (object instanceof Number)
        {
            return ((Number) object).intValue();
        }
        if (object instanceof String)
        {
            return Integer.valueOf(Double.valueOf((String) object).intValue());
        }
        throw new NumberFormatException(
                "parseInt() does not support " + (object == null ? "null" : object.getClass().getSimpleName()));
    }

    /**
     * Converts an Object to a double.
     *
     * @param object the object to be parsed
     * @return the integer value or null if the string is null or empty
     * @throws NumberFormatException if the object can not be converted into double
     */
    public static double parseDouble(Object object)
    {
        if (object instanceof Number)
        {
            return ((Number) object).doubleValue();
        }
        if (object instanceof String)
        {
            return Double.parseDouble(object.toString());
        }
        throw new NumberFormatException(
                "parseDouble() does not support " + (object == null ? "null" : object.getClass().getSimpleName()));
    }

    /**
     * Checks whether the Object is a valid Java number.
     *
     * @param object the object to be evaluated
     * @return true if pObject is a Number or String representation of number
     */
    public static boolean isNumber(Object object)
    {
        if (object == null || StringUtils.isEmpty(object.toString()))
        {
            return false;
        }
        if (object instanceof Number)
        {
            return true;
        }
        try
        {
            Double.valueOf(String.valueOf(object));
            return true;
        }
        catch (NumberFormatException exception)
        {
            return false;
        }
    }

    /**
     * Checks if all the elements are parsable number.
     *
     * @param iterable the iterable to be evaluated
     * @return true if all elements are parsable number
     */
    public static boolean containsParsableNumbers(Iterable<?> iterable)
    {
        Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext())
        {
            Object element = iterator.next();
            if (!isNumber(element))
            {
                return false;
            }
        }
        return true;
    }
}
