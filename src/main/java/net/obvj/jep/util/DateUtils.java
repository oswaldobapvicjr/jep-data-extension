package net.obvj.jep.util;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Iterator;

/**
 * A utility class for working with dates.
 *
 * @author oswaldo.bapvic.jr
 */
public class DateUtils
{

    private DateUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converts the given {@code pObject} into {@link java.util.Date}. It supports objects of
     * type {@link java.time.Instant} and valid strings in RFC-3339 format.
     *
     * @param pObject the object to be converted
     * @return the {@code pObject} converted to {@code Date}
     * @throws DateTimeParseException if the date can not be parsed
     */
    public static Date parseDate(Object pObject)
    {
        if (pObject instanceof Date)
        {
            return (Date) pObject;
        }
        else if (pObject instanceof Instant)
        {
            return Date.from((Instant) pObject);
        }
        Instant instant = Instant.parse(String.valueOf(pObject));
        return Date.from(instant);
    }

    /**
     * Checks whether the Object is a valid Java date
     *
     * @param pObject the object to be evaluated
     * @return true if pObject is a Date and an Instant or a String representation of date
     */
    public static boolean isParsable(Object pObject)
    {
        if (pObject instanceof Date || pObject instanceof Instant)
        {
            return true;
        }

        try
        {
            Instant.parse(String.valueOf(pObject));
            return true;
        }
        catch (DateTimeParseException exception)
        {
            return false;
        }
    }

    /**
     * Checks if all the elements are parsable date
     *
     * @param pIterable the iterable to be evaluated
     * @return true if all elements are parsable date
     */
    public static boolean containsParsableDates(Iterable<?> pIterable)
    {
        Iterator<?> iterator = pIterable.iterator();
        while (iterator.hasNext())
        {
            Object element = iterator.next();
            if (!isParsable(element))
            {
                return false;
            }
        }
        return true;
    }
}
