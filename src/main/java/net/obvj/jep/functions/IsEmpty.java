package net.obvj.jep.functions;

import java.util.Collection;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.JsonUtils;

/**
 * A function that returns 1 if the given parameter is either an empty String, JSON or
 * Collection, or 0 if not.
 * <p>
 * Strings that can be parsed as JSON objects or JSON arrays, will be first converted into
 * JSON, then its structure will be evaluated for emptiness. For example, the following
 * strings will be evaluated as empty objects:
 * <ul>
 * <li><code>""</code> (an empty string)</li>
 * <li><code>"{}"</code> (an empty JSON object)</li>
 * <li><code>"[]"</code> (an empty JSON array)</li>
 * </ul>
 *
 * @author oswaldo.bapvic.jr
 */
@Function("isEmpty")
public class IsEmpty extends PostfixMathCommand
{
    private static final double FALSE = 0d;
    private static final double TRUE = 1d;

    /**
     * Builds this custom command with a fixed number of 1 parameter
     */
    public IsEmpty()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object parameter = stack.pop();
        boolean isEmpty = isEmpty(parameter);
        stack.push(isEmpty ? TRUE : FALSE);
    }

    private boolean isEmpty(Object object)
    {
        if (object instanceof String)
        {
            return validateStringWithJsonSupport((String) object);
        }
        else if (object instanceof Collection<?>)
        {
            return CollectionsUtils.isEmpty((Collection<?>) object);
        }
        else if (object instanceof JSONObject || object instanceof JSONArray)
        {
            return JsonUtils.isEmpty(object);
        }
        return object == null;
    }

    private boolean validateStringWithJsonSupport(String string)
    {
        try
        {
            JSONObject json = JsonUtils.convertToJSONObject(string);
            return JsonUtils.isEmpty(json);
        }
        catch (JSONException e)
        {
            // Not a JSON object... Let's try as a JSON array...
            try
            {
                JSONArray json = JsonUtils.convertToJSONArray(string);
                return JsonUtils.isEmpty(json);
            }
            catch (JSONException e1)
            {
                // Neither a JSON object, nor a JSON array. Handle a normal string...
            }
        }
        return StringUtils.isEmpty(string);
    }

}
