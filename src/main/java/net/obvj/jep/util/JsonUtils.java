package net.obvj.jep.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JettisonProvider;

/**
 * Utility methods for working with JSON and JSONPath
 *
 * @author oswaldo.bapvic.jr
 */
public class JsonUtils
{
    private static final Configuration JSON_PATH_CONFIGURATION = Configuration.builder()
            .jsonProvider(new JettisonProvider()).build();

    private JsonUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param jsonObject the JSON object to be validated
     * @return {@code true} if the JSON object is null or empty
     */
    private static boolean isEmpty(JSONObject jsonObject)
    {
        return jsonObject == null || jsonObject.length() == 0;
    }

    /**
     * @param jsonArray the JSON array to be validated
     * @return {@code true} if the JSON array is null or empty
     */
    private static boolean isEmpty(JSONArray jsonArray)
    {
        return jsonArray == null || jsonArray.length() == 0;
    }

    /**
     * @param object the Object to be validated
     * @return {@code true} if the Object is null or empty
     */
    public static boolean isEmpty(Object object)
    {
        if (object instanceof JSONObject)
        {
            return isEmpty((JSONObject) object);
        }
        if (object instanceof JSONArray)
        {
            return isEmpty((JSONArray) object);
        }
        return object == null || JSONObject.NULL.equals(object);
    }

    /**
     * Compiles the given JSONPath.
     *
     * @param jsonPath the JSONPath to be validated
     * @return a {@code JsonPath} object
     */
    public static JsonPath compileJsonPath(String jsonPath)
    {
        return JsonPath.compile(jsonPath);
    }

    /**
     * Gets a value that matches the given JSONPath.
     *
     * @param json a JSON object input data
     * @param jsonPath the JSONPath to be evaluated
     * @return The value that matches the given JSONPath string or {@code null} if no match
     * was found. If the JSON is empty or null, the same object will be returned.
     */
    public static Object readJsonPath(JSONObject json, String jsonPath)
    {
        if (isEmpty(json))
        {
            return json;
        }
        return JsonPath.parse(json, JSON_PATH_CONFIGURATION).read(jsonPath);
    }

    /**
     * Gets a value that matches the given JSONPath.
     *
     * @param jsonPath             the JSONPath to be validated
     * @param json                 a JSON object input data
     * @param extractSingleElement a flag indicating whether the method should return the
     *                             single value of a JSONArray instead of the array itself,
     *                             provided that the length of the returnable array is equal
     *                             to one.
     * @return The evaluation results the given {@code jsonpath} over the {@code json} object;
     */
    public static Object readJsonPath(JSONObject json, String jsonPath, boolean extractSingleElement)
    {
        Object evaluationResult = readJsonPath(json, jsonPath);
        if (extractSingleElement)
        {
            return getSingleValueFromJSONArray(evaluationResult);
        }
        return evaluationResult;
    }

    /**
     * Returns the single element of a JSONArray provided that a singleton array is received.
     * The whole JSONArray is returned if its length is greater than one.
     *
     * @param evaluationResult the JSONPath evaluation result, which may be either a Java
     *                         object or a JSONArray containing one or more elements
     * @return a single element or the original JSONArray
     */
    protected static Object getSingleValueFromJSONArray(Object evaluationResult)
    {
        if (evaluationResult instanceof JSONArray)
        {
            JSONArray pJSONArray = (JSONArray) evaluationResult;
            if (pJSONArray.length() == 1)
            {
                return pJSONArray.opt(0);
            }
        }
        return evaluationResult;
    }

    /**
     * Converts an Object to JSONObject
     *
     * @param inputObject the object to be converted in JSONObject
     * @return the object converted in JSONObject
     * @throws JSONException in case of errors converting into JSONObject
     */
    public static JSONObject convertToJSONObject(Object inputObject) throws JSONException
    {
        if (inputObject == null)
        {
            return new JSONObject();
        }
        if (inputObject instanceof JSONObject)
        {
            return (JSONObject) inputObject;
        }
        return new JSONObject(inputObject.toString());
    }

    /**
     * Converts a JSONArray into a Java-standard List
     *
     * @param jsonArray the JSONArray object to be converted
     * @return the list converted from a JSONArray object
     * @throws JSONException in case of errors handling the JSONArray
     */
    public static List<Object> convertJSONArrayToList(JSONArray jsonArray)
    {
        try
        {
            if (jsonArray == null || jsonArray.length() == 0)
            {
                return Collections.emptyList();
            }
            List<Object> sourceListConverted = new ArrayList<>(jsonArray.length());
            for (int i = 0, length = jsonArray.length(); i < length; i++)
            {
                if (isEmpty(jsonArray.get(i)))
                {
                    return Collections.emptyList();
                }
                sourceListConverted.add(jsonArray.get(i));
            }
            return sourceListConverted;
        }
        catch (JSONException exception)
        {
            throw new IllegalArgumentException("Invalid JSONArray", exception);
        }
    }
}
