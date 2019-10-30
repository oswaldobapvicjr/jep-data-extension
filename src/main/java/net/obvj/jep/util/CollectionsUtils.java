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
            return JsonUtils.convertJSONArrayToList(jsonArray);
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
                return JsonUtils.convertJSONArrayToList(jsonArray);
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
