package net.obvj.jep.functions;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.JsonUtils;

/**
 * This class implements a function that returns the lowest element in a collection of
 * elements using Java Expression Parser. The function supports arrays, JSONArrays and
 * Iterables containing Numbers or Dates.
 *
 * @author oswaldo.bapvic.jr
 */
@Function("min")
public class Min extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of parameters
     */
    public Min()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        stack.push(min(stack.pop()));
    }

    /**
     * Returns the minimum element inside the given JSON Array
     *
     * @param jsonArray the JSON array whose minimum element is to be evaluated
     * @return The minimum element inside the given JSON Array.
     */
    private Object min(JSONArray jsonArray)
    {
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(jsonArray);
        return min(convertedList);
    }

    /**
     * Returns the minimum element inside the given String that represents a JSONArray
     *
     * @param string a string representation of JSON array whose minimum element is to be
     *               evaluated
     * @return The minimum element inside the given string.
     */
    private Object min(String string)
    {
        if (StringUtils.isEmpty(string))
        {
            return string;
        }
        try
        {
            JSONArray jsonArray = new JSONArray(string);
            return min(jsonArray);
        }
        catch (JSONException exception)
        {
            // if it's not a valid JSONArray, it must be handled as a string
            return string;
        }
    }

    /**
     * Returns the minimum element inside the given Object based on its type
     *
     * @param object the Object whose minimum element is to be evaluated
     * @return The minimum element inside the given Object.
     */
    public Object min(Object object)
    {
        if (object != null)
        {
            if (object instanceof Iterable)
            {
                return CollectionsUtils.min((Iterable<?>) object);
            }
            else if (object instanceof JSONArray)
            {
                return min((JSONArray) object);
            }
            else if (object instanceof Object[])
            {
                return min((Arrays.asList((Object[]) object)));
            }
            else if (object instanceof String)
            {
                return min((String) object);
            }
        }
        return object;
    }

}
