package net.obvj.jep.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateFormatUtils;

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
     * Converts the given string into {@link java.util.Date} by applying the given
     * {@code pattern}
     *
     * @param string the string to be converted
     * @param pattern the date format pattern to be used
     * @return the string converted to {@code Date}
     * @throws IllegalArgumentException if a null string or pattern is received
     * @throws ParseException if the date can not be parsed
     */
    public static Date parseDate(String string, String pattern) throws ParseException
    {
        return org.apache.commons.lang3.time.DateUtils.parseDate(string, pattern);
    }

    /**
     * Converts the given date into string with the the given {@code pattern}
     *
     * @param date the date to be formatted
     * @param pattern the date format pattern to be used
     * @return the string converted to {@code Date}
     */
    public static String formatDate(Date date, String pattern)
    {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * Converts the given {@code pObject} into {@link java.util.Date}. It supports objects of
     * type {@link java.time.Instant} and valid strings in RFC-3339 format.
     *
     * @param object the object to be converted
     * @return the {@code object} converted to {@code Date}
     * @throws DateTimeParseException if the date can not be parsed
     */
    public static Date parseDate(Object object)
    {
        if (object instanceof Date)
        {
            return (Date) object;
        }
        else if (object instanceof Instant)
        {
            return Date.from((Instant) object);
        }
        Instant instant = Instant.parse(String.valueOf(object));
        return Date.from(instant);
    }

    /**
     * Checks whether the Object is a valid Java date
     *
     * @param object the object to be evaluated
     * @return true if object is a Date and an Instant or a String representation of date
     */
    public static boolean isParsable(Object object)
    {
        if (object instanceof Date || object instanceof Instant)
        {
            return true;
        }

        try
        {
            Instant.parse(String.valueOf(object));
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
     * @param iterable the iterable to be evaluated
     * @return true if all elements are parsable date
     */
    public static boolean containsParsableDates(Iterable<?> iterable)
    {
        Iterator<?> iterator = iterable.iterator();
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

    /**
     * Calculates the number of days between two dates
     *
     * @param firstDate the first date for the comparison
     * @param secondDate the seconds date for the comparison
     * @return the number of days between {@code firstDate} and {@code secondDate}
     * @throws IllegalArgumentException if a null date is received
     */
    public static long daysBetween(Date firstDate, Date secondDate)
    {
        if (firstDate == null) throw new IllegalArgumentException("First date cannot be null");
        if (secondDate == null) throw new IllegalArgumentException("Second date cannot be null");

        long differenceInMillis = Math.abs(firstDate.getTime() - secondDate.getTime());
        return TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Returns true if the given date is a leap year, that is, an year with 366 days, the
     * extra day designated as February 29.
     *
     * @param date the date whose year is to be evaluated
     * @return {@code true} if the given year is leap year, {@code false} if not.
     * @throws IllegalArgumentException if a null date is received
     */
    public static boolean isLeapYear(Date date)
    {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return isLeapYear(calendar.get(Calendar.YEAR));
    }

    /**
     * Returns true if the given year is a leap year, that is, an year with 366 days, the
     * extra day designated as February 29.
     *
     * @param year the year to be evaluated
     * @return {@code true} if the given year is leap year, {@code false} if not.
     */
    public static boolean isLeapYear(int year)
    {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }
}
