package net.obvj.jep.functions;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommandI;

import net.obvj.jep.util.DateUtils;
import net.obvj.jep.util.JsonUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * This class implements a function that returns the lowest element in a collection of
 * elements using Java Expression Parser. The function supports arrays, JSONArrays and
 * Iterables containing Numbers or Dates.
 *
 * @author oswaldo.bapvic.jr
 */
public class Min extends StatisticsCommandBase implements PostfixMathCommandI
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
     * Returns the minimum element inside the given Iterable
     *
     * @param pCollection the Iterable whose minimum element is to be evaluated
     * @return The minimum element inside the given Iterable.
     */
    private Object min(Iterable<?> iterable)
    {
        if (DateUtils.containsParsableDates(iterable))
        {
            Map<Object, Object> parsedDateMap = createMapOfParsedObjects(iterable);

            Optional<Date> value = parsedDateMap.keySet().stream().map(DateUtils::parseDate).min(Date::compareTo);
            if (value.isPresent())
            {
                return parsedDateMap.get(value.get());
            }
        }
        else if (NumberUtils.containsParsableNumbers(iterable))
        {
            Map<Object, Object> parsedNumberMap = createMapOfParsedObjects(iterable);

            Optional<Double> value = parsedNumberMap.keySet().stream().map(NumberUtils::parseDouble)
                    .min(Double::compareTo);
            if (value.isPresent())
            {
                return parsedNumberMap.get(value.get());
            }
        }

        throw new IllegalArgumentException(
                "Unable to compare minimum value for the arguments: " + iterable.toString());
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
                return min((Iterable<?>) object);
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
