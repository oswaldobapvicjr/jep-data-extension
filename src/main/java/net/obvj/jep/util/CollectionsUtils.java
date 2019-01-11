package net.obvj.jep.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 * Utility methods for working with Java Collections.
 *
 * @author oswaldo.bapvic.jr
 */
public class CollectionsUtils
{
    private CollectionsUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Parses the given {@code pSourceObject} into a {@code List}. The object may be either an
     * array, or an instance of {@code List} or {@code JSONArray}. An object is converted into
     * a singleton list. If a null or empty object is received, an empty list is returned.
     *
     * @param object the source object to be parsed into a {@code List}.
     * @return a {@code List} object
     */
    public static List<Object> asList(Object object)
    {
        if (object == null)
        {
            return Collections.emptyList();
        }

        if (object instanceof List)
        {
            return (List) object;
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
}
