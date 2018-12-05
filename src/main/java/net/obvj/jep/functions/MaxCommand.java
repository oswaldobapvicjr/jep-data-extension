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
 * This class implements a function that returns the highest element in a collection of
 * elements using Java Expression Parser. The function supports arrays, JSONArrays and
 * Iterables containing Numbers or Dates.
 *
 * @author oswaldo.bapvic.jr
 */
public class MaxCommand extends StatisticsCommandBase implements PostfixMathCommandI
{
    /**
     * Builds this function with a fixed number of parameters
     */
    public MaxCommand()
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
     * Returns the maximum element inside the given Iterable
     *
     * @param pCollection the Iterable whose maximum element is to be evaluated
     * @return The maximum element inside the given Iterable.
     */
    private Object max(Iterable<?> pIterable)
    {
        if (DateUtils.containsParsableDates(pIterable))
        {
            Map<Object, Object> parsedDateMap = createMapOfParsedObjects(pIterable);

            Optional<Date> value = parsedDateMap.keySet().stream().map(DateUtils::parseDate).max(Date::compareTo);
            if (value.isPresent())
            {
                return parsedDateMap.get(value.get());
            }
        }
        else if (NumberUtils.containsParsableNumbers(pIterable))
        {
            Map<Object, Object> parsedNumberMap = createMapOfParsedObjects(pIterable);

            Optional<Double> value = parsedNumberMap.keySet().stream().map(NumberUtils::parseDouble)
                    .max(Double::compareTo);
            if (value.isPresent())
            {
                return parsedNumberMap.get(value.get());
            }
        }

        throw new IllegalArgumentException(
                "Unable to compare maximum value for the arguments: " + pIterable.toString());
    }

    /**
     * Returns the maximum element inside the given JSON Array
     *
     * @param pJsonArray the JSON array whose maximum element is to be evaluated
     * @return The maximum element inside the given JSON Array.
     */
    private Object max(JSONArray pJsonArray)
    {
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(pJsonArray);
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
            return null;
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
                return max((Iterable<?>) object);
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
