package net.obvj.jep.functions;

import java.util.Collection;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function that counts elements.
 *
 * @author oswaldo.bapvic.jr
 */
@Function({ "count", "length" })
public class Count extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of one parameter
     */
    public Count()
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
        stack.push(count(stack.pop()));
    }

    /**
     * @param collection the Collection whose size is to the counted
     * @return The count of elements inside the given Collection.
     */
    protected int count(Collection<?> collection)
    {
        return collection == null ? 0 : collection.size();
    }

    /**
     * @param jsonArray the JSON array whose length is to the counted
     * @return The count of elements inside the given JSON Array.
     */
    protected int count(JSONArray jsonArray)
    {
        return jsonArray == null ? 0 : jsonArray.length();
    }

    /**
     * @param string a string representation of JSON array whose length is to be returned
     * @return The count of elements inside the given string.
     */
    private int count(String string)
    {
        try
        {
            return StringUtils.isEmpty(string) ? 0 : new JSONArray(string).length();
        }
        catch (JSONException exception)
        {
            // if it's not a valid JSONArray, it must be handled as a string
            return 1;
        }
    }

    /**
     * Counts the elements of an Object which may be a Collection, array, JSONArray, or string
     * representation of JSONArray.
     *
     * @param object the Object whose length is to the counted
     * @return The count of elements inside the given Object.
     */
    public int count(Object object)
    {
        if (object == null)
        {
            return 0;
        }
        else if (object instanceof Collection)
        {
            return count((Collection<?>) object);
        }
        else if (object instanceof JSONArray)
        {
            return count((JSONArray) object);
        }
        else if (object instanceof Object[])
        {
            return ((Object[]) object).length;
        }
        else if (object instanceof String)
        {
            return count((String) object);
        }
        return 1;
    }

}
