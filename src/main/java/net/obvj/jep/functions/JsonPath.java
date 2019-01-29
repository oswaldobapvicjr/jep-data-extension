package net.obvj.jep.functions;

import java.util.Stack;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.PathNotFoundException;

import net.obvj.jep.util.JsonUtils;

/**
 * This class implements a function that evaluates JSONPaths.
 *
 * @author oswaldo.bapvic.jr
 */
public class JsonPath extends PostfixMathCommand
{
    private static final String ERROR_JSON_PATH_ARGUMENT_MISSING = "JSONPath argument missing";
    private static final String ERROR_JSON_OBJECT_NOT_FOUND = "JSON object not found: %s";
    private static final String ERROR_NO_RESULTS_FOR_PATH = "No results for path: %s";
    private static final String ERROR_INVALID_JSONPATH = "Invalid JSONPath: %s";
    private static final String ERROR_INVALID_JSON = "Invalid JSON";

    /**
     * Builds this function with two parameters
     */
    public JsonPath()
    {
        numberOfParameters = 2;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommandI#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);

        Object jsonPathArg = stack.peek();
        if (jsonPathArg == null || jsonPathArg.toString().isEmpty())
        {
            throw new IllegalArgumentException(ERROR_JSON_PATH_ARGUMENT_MISSING);
        }

        String jsonPathString = stack.pop().toString();
        Object jsonVariable = stack.pop();
        stack.push(executeJsonPath(jsonPathString, jsonVariable));
    }

    private Object executeJsonPath(String jsonPathString, Object jsonVariable)
    {
        try
        {
            JSONObject jsonObject = JsonUtils.convertToJSONObject(jsonVariable);
            if (JsonUtils.isEmpty(jsonObject))
            {
                throw new IllegalArgumentException(String.format(ERROR_JSON_OBJECT_NOT_FOUND, jsonVariable));
            }
            Object result = JsonUtils.readJsonPath(jsonObject, jsonPathString, true);
            if (JsonUtils.isEmpty(result))
            {
                throw new IllegalArgumentException(String.format(ERROR_NO_RESULTS_FOR_PATH, jsonPathString));
            }
            return result;
        }
        catch (JSONException jsonException)
        {
            throw new IllegalArgumentException(ERROR_INVALID_JSON, jsonException);
        }
        catch (PathNotFoundException pathNotFoundException)
        {
            throw new IllegalArgumentException(
                    String.format(ERROR_NO_RESULTS_FOR_PATH, pathNotFoundException, jsonPathString),
                    pathNotFoundException);
        }
        catch (JsonPathException jsonPathException)
        {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_JSONPATH, jsonPathException, jsonPathString),
                    jsonPathException);
        }
    }

}
