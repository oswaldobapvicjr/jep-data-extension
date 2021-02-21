package net.obvj.jep.util;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 * Utility methods for working with Java Collections.
 *
 * @author oswaldo.bapvic.jr
 */
public class CollectionsUtils
{
    private static final String ENTRY_SEPARATORS_REGEX = "=|:";
    private static final String ERROR_UNABLE_TO_PARSE_ENTRY = "Unable to parse entry: %s";

    private CollectionsUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param collection the collection to be evaluated
     * @return {@code true} if the given collection is either null or empty; otherwise, false.
     */
    public static boolean isEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }

    /**
     * Converts the given object into a {@code List}.
     * <p>
     * If the source {@code object} is either an array of Objects (primitive types not
     * supported) or a {@code JSONArray}, it will return a List backed by the specified array.
     * If a non-null Object is received (not a {@code List} or {@code JSONArray}), a singleton
     * list will be returned (containing only the specified object). If a null or empty object
     * is received, then an empty list will be returned. If the input is already a
     * {@code List}, the same object will be returned.
     *
     * @param object the source object to be parsed into a {@code List}
     * @return a {@code List} object
     */
    @SuppressWarnings("unchecked")
    public static List<Object> asList(Object object)
    {
        if (object == null)
        {
            return Collections.emptyList();
        }

        if (object instanceof List)
        {
            // We must support lists of any types
            @SuppressWarnings("rawtypes")
            List list = (List) object;
            return list;
        }

        if (object instanceof JSONArray)
        {
            JSONArray jsonArray = (JSONArray) object;
            return JsonUtils.toList(jsonArray);
        }

        if (object instanceof Object[])
        {
            return Arrays.asList((Object[]) object);
        }

        if (object instanceof String)
        {
            try
            {
                JSONArray jsonArray = new JSONArray(object.toString());
                return JsonUtils.toList(jsonArray);
            }
            catch (JSONException exception)
            {
                // Not a valid JSON array, so handle it as a normal string...
            }
        }

        return Collections.singletonList(object);
    }

    /**
     * Accepts one or more entries as strings in the format recognized by
     * {@link CollectionsUtils#parseMapEntry(String)} and populates a {@link Map} of Strings
     * with these associations.
     *
     * @param entries one or more strings containing, each one, a map entry parseable by
     *                {@link CollectionsUtils#parseMapEntry(String)}
     * @return a {@link Map} containing associations for the given entries
     */
    public static Map<String, String> asMap(String... entries)
    {
        return Arrays.stream(entries).map(CollectionsUtils::parseMapEntry)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /**
     * Parses a string into a {@link Map.Entry}.
     *
     * @param entry a string containing a key, a separator, and a value. A separator can be
     *              either an equal sign ({@code =}) or colon ({@code :}). For example:
     *              {@code "key1=value1"} or {@code "key2:value2"}
     * @return a {@link Map.Entry} of Strings for the given entry string
     */
    public static Map.Entry<String, String> parseMapEntry(String entry)
    {
        if (StringUtils.isEmpty(entry))
        {
            throw new IllegalArgumentException(String.format(ERROR_UNABLE_TO_PARSE_ENTRY, "<EMPTY>"));
        }
        String[] strings = entry.split(ENTRY_SEPARATORS_REGEX, 2);
        if (strings.length < 2)
        {
            throw new IllegalArgumentException(String.format(ERROR_UNABLE_TO_PARSE_ENTRY, entry));
        }
        return new SimpleEntry<>(strings[0].trim(), strings[1].trim());
    }

    /**
     * Converts the given {@link Iterable} into a {@link Map} where each key is an element of
     * the given {@link Iterable}, parsed either as {@link Double} or {@link Date}, and the
     * associated value is the original element without conversion.
     *
     * @param iterable the {@link Iterable} to be converted
     * @return A map contains the parsed objects as keys and the original elements as values
     */
    public static Map<Object, Object> createMapOfParsedObjects(Iterable<?> iterable)
    {
        Map<Object, Object> parsedObjectsMap = new HashMap<>();
        Iterator<?> iterator = iterable.iterator();
        while (iterator.hasNext())
        {
            Object element = iterator.next();
            if (DateUtils.isParsable(element))
            {
                parsedObjectsMap.put(DateUtils.parseDate(element), element);
            }
            else if (NumberUtils.isNumber(element))
            {
                parsedObjectsMap.put(Double.valueOf(NumberUtils.parseDouble(element)), element);
            }
        }
        return parsedObjectsMap;
    }

    /**
     * Returns the maximum element inside the given Iterable
     *
     * @param iterable the {@link Iterable} whose maximum element is to be evaluated
     * @return The maximum element inside the given Iterable.
     */
    public static Object max(Iterable<?> iterable)
    {
        if (DateUtils.containsParsableDates(iterable))
        {
            Map<Object, Object> parsedDateMap = CollectionsUtils.createMapOfParsedObjects(iterable);

            Optional<Date> maxDate = parsedDateMap.keySet().stream().map(DateUtils::parseDate).max(Date::compareTo);
            if (maxDate.isPresent())
            {
                return parsedDateMap.get(maxDate.get());
            }
        }
        if (NumberUtils.containsParsableNumbers(iterable))
        {
            Map<Object, Object> parsedNumberMap = CollectionsUtils.createMapOfParsedObjects(iterable);

            Optional<Double> maxNumber = parsedNumberMap.keySet().stream().map(NumberUtils::parseDouble)
                    .max(Double::compareTo);
            if (maxNumber.isPresent())
            {
                return parsedNumberMap.get(maxNumber.get());
            }
        }

        throw new IllegalArgumentException(
                "Unable to determine the maximum value for the arguments: " + iterable.toString());
    }

    /**
     * Returns the minimum element inside the given Iterable
     *
     * @param iterable the Iterable whose minimum element is to be evaluated
     * @return The minimum element inside the given Iterable.
     */
    public static Object min(Iterable<?> iterable)
    {
        if (DateUtils.containsParsableDates(iterable))
        {
            Map<Object, Object> parsedDateMap = CollectionsUtils.createMapOfParsedObjects(iterable);

            Optional<Date> value = parsedDateMap.keySet().stream().map(DateUtils::parseDate).min(Date::compareTo);
            if (value.isPresent())
            {
                return parsedDateMap.get(value.get());
            }
        }
        if (NumberUtils.containsParsableNumbers(iterable))
        {
            Map<Object, Object> parsedNumberMap = CollectionsUtils.createMapOfParsedObjects(iterable);

            Optional<Double> value = parsedNumberMap.keySet().stream().map(NumberUtils::parseDouble)
                    .min(Double::compareTo);
            if (value.isPresent())
            {
                return parsedNumberMap.get(value.get());
            }
        }

        throw new IllegalArgumentException("Unable to compare minimum value for the arguments: " + iterable.toString());
    }

    /**
     * Returns a list consisting of the distinct elements (according to Object.equals(Object))
     * of a given list.
     *
     * @param list the list to be processed
     * @return a new list containing only distinct elements
     */
    public static List<Object> distinctList(List<Object> list)
    {
        if (isEmpty(list))
        {
            return list;
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Produces a Stack to be processed by JEP functions.
     *
     * @param parameters the parameters to populate the stack
     * @return a Stack with the given parameters.
     */
    public static Stack<Object> newParametersStack(Object... parameters)
    {
        Stack<Object> stack = new Stack<>();
        for (Object param : parameters)
        {
            stack.push(param);
        }
        return stack;
    }

}
