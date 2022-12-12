package net.obvj.jep.functions;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.JsonUtils;

/**
 * This class implements a function that returns the highest element in a collection of
 * elements using Java Expression Parser. The function supports arrays, JSONArrays and
 * Iterables containing Numbers or Dates.
 *
 * @author oswaldo.bapvic.jr
 */
@Function("max")
public class Max extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of parameters
     */
    public Max()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack pStack) throws ParseException
    {
        checkStack(pStack);
        pStack.push(max(pStack.pop()));
    }

    /**
     * Returns the maximum element inside the given JSON Array
     *
     * @param jsonArray the JSON array whose maximum element is to be evaluated
     * @return The maximum element inside the given JSON Array.
     */
    private Object max(JSONArray jsonArray)
    {
        List<Object> convertedList = JsonUtils.toList(jsonArray);
        return max(convertedList);
    }

    /**
     * Returns the maximum element inside the given String that represents a JSONArray
     *
     * @param string a string representation of JSON array whose maximum element is to be
     *               evaluated
     * @return The maximum element inside the given string.
     */
    private Object max(String string)
    {
        if (StringUtils.isEmpty(string))
        {
            return string;
        }
        try
        {
            JSONArray jsonArray = new JSONArray(string);
            return max(jsonArray);
        }
        catch (JSONException exception)
        {
            // if it's not a valid JSONArray, it must be handled as a string
            return string;
        }
    }

    /**
     * Returns the maximum element inside the given Object based on its type
     *
     * @param object the Object whose maximum element is to be evaluated
     * @return The maximum element inside the given Object.
     */
    public Object max(Object object)
    {
        if (object != null)
        {
            if (object instanceof Iterable)
            {
                return CollectionsUtils.max((Iterable<?>) object);
            }
            else if (object instanceof JSONArray)
            {
                return max((JSONArray) object);
            }
            else if (object instanceof Object[])
            {
                return max((Arrays.asList((Object[]) object)));
            }
            else if (object instanceof String)
            {
                return max((String) object);
            }
        }
        return object;
    }

}
