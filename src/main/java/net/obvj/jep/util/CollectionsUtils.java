package net.obvj.jep.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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

    /**
     * Extracts each object from the stack and populates an array of strings, letting the
     * stack empty.
     *
     * @param stack the stack of parameters to be retrieved
     * @return an array of strings for each element of the stack
     */
    public static String[] getStringVarArgs(final Stack<?> stack)
    {
        String[] arguments = new String[stack.size()];
        int index = arguments.length;
        while (!stack.isEmpty())
        {
            Object arg = stack.pop();
            arguments[--index] = String.valueOf(arg);
        }
        return arguments;
    }

    /**
     * Extracts each object from the second position of the stack until the last one, and
     * populates an array of strings, letting only the first element remaining in the stack.
     *
     * @param stack the stack of parameters to be retrieved
     * @return an array of strings for each element from the second to the last position of
     * the stack
     */
    public static String[] getStringVarArgsExceptFirst(final Stack<?> stack)
    {
        String[] arguments = new String[stack.size() - 1];
        int index = arguments.length;
        while (stack.size() > 1)
        {
            Object arg = stack.pop();
            arguments[--index] = String.valueOf(arg);
        }
        return arguments;
    }

}
