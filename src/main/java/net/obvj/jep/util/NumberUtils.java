package net.obvj.jep.util;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

/**
 * A utility class for working with numbers
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
     * Gets an integer value from a {@code String}
     *
     * @param pStringValue the string to get the integer value
     * @return the integer value or null if the string is null or empty
     */
    public static Integer getIntegerFromString(String pStringValue)
    {
        if (StringUtils.isEmpty(pStringValue))
        {
            return null;
        }
        return Integer.valueOf(Double.valueOf(pStringValue).intValue());
    }

    /**
     * Checks if a {@code String} represents an integer
     *
     * @param pStringValue the string to be checked
     * @return true if is integer otherwise false
     */
    public static boolean isInteger(String pStringValue)
    {
        try
        {
            return isInteger(Double.valueOf(pStringValue));
        }
        catch (NumberFormatException exception)
        {
            return false;
        }
    }

    /**
     * Checks if a {@code Number} is an integer
     *
     * @param pNumberValue the number to be checked
     * @return true if is integer otherwise false
     */
    public static boolean isInteger(Number pNumberValue)
    {
        return pNumberValue != null && pNumberValue.doubleValue() % 1 == 0;
    }

    /**
     * Converts an Object to a integer
     *
     * @param pObject the object to be parsed
     * @return an integer number or null if the string is null or empty
     * @throws NumberFormatException    if the object is a string that does not contain a
     *                                  parsable integer.
     * @throws IllegalArgumentException if the object can not be converted into double.
     */
    public static int parseInt(Object pObject)
    {
        if (pObject instanceof Number)
        {
            return ((Number) pObject).intValue();
        }
        if (pObject instanceof String)
        {
            return Integer.parseInt(pObject.toString());
        }
        throw new IllegalArgumentException("parseInt() does not support " + pObject.getClass().getSimpleName());
    }

    /**
     * Converts an Object to a double.
     *
     * @param pObject the object to be parsed
     * @return the integer value or null if the string is null or empty
     * @throws NumberFormatException    if the object is a string that does not contain a
     *                                  parsable double.
     * @throws IllegalArgumentException if the object can not be converted into double.
     */
    public static double parseDouble(Object pObject)
    {
        if (pObject instanceof Number)
        {
            return ((Number) pObject).doubleValue();
        }
        if (pObject instanceof String)
        {
            return Double.parseDouble(pObject.toString());
        }
        throw new IllegalArgumentException("parseDouble() does not support " + pObject.getClass().getSimpleName());
    }

    /**
     * Checks whether the Object is a valid Java number
     *
     * @param pObject the object to be evaluated
     * @return true if pObject is a Number or String representation of number
     */
    public static boolean isNumber(Object pObject)
    {
        if (pObject == null || StringUtils.isEmpty(pObject.toString()))
        {
            return false;
        }
        if (pObject instanceof Number)
        {
            return true;
        }
        try
        {
            Double.valueOf(String.valueOf(pObject));
            return true;
        }
        catch (NumberFormatException exception)
        {
            return false;
        }
    }

    /**
     * Checks if all the elements are parsable number
     *
     * @param pIterable the iterable to be evaluated
     * @return true if all elements are parsable number
     */
    public static boolean containsParsableNumbers(Iterable<?> pIterable)
    {
        Iterator<?> iterator = pIterable.iterator();
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
