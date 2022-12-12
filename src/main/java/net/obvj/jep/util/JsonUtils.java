package net.obvj.jep.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JsonOrgJsonProvider;

/**
 * Utility methods for working with JSON and JSONPath.
 *
 * @author oswaldo.bapvic.jr
 */
public class JsonUtils
{
    private static final Configuration JSON_PATH_CONFIGURATION = Configuration.builder()
            .jsonProvider(new JsonOrgJsonProvider()).build();

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
     * Null-safe method that evaluates the specified object for emptiness.
     * <p>
     * For example, an empty {@link JSONObject} ("{ }") or {@link JSONArray} ("[ ]") evaluates
     * to {@code true}.
     * <p>
     * Null references also evaluate to {@code true}.
     *
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
     * @return The evaluation results the given {@code jsonPath} over the {@code json} object;
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
     * Converts an object to a {@link JSONObject}.
     * <p>
     * If the specified input is a String, then it will be parsed as a {@link JSONObject}.
     * <p>
     * If the specified input is {@code null}, then an empty {@link JSONObject} ("{ }") will
     * be returned.
     * <p>
     * If the specified input is already a {@link JSONObject}, then the same object will be
     * returned.
     *
     * @param object the object to be converted
     * @return the converted {@link JSONObject}
     * @throws JSONException in case of errors converting into {@link JSONObject} (for
     *                       example, if there is a syntax error in the source string).
     */
    public static JSONObject toJSONObject(Object object) throws JSONException
    {
        if (object == null)
        {
            return new JSONObject();
        }
        if (object instanceof JSONObject)
        {
            return (JSONObject) object;
        }
        return new JSONObject(object.toString());
    }

    /**
     * Converts an object to a {@link JSONArray}.
     * <p>
     * If the specified input is a String, then it will be parsed as a {@link JSONArray}.
     * <p>
     * If the specified input is {@code null}, then an empty {@link JSONArray} ("[ ]") will be
     * returned.
     * <p>
     * If the specified input is already a {@link JSONArray}, then the same object will be
     * returned.
     *
     * @param object the object to be converted
     * @return the converted {@link JSONArray}
     * @throws JSONException in case of errors converting into JSONArray (for example, if
     *                       there is a syntax error in the source string).
     */
    public static JSONArray toJSONArray(Object object) throws JSONException
    {
        if (object == null)
        {
            return new JSONArray();
        }
        if (object instanceof JSONArray)
        {
            return (JSONArray) object;
        }
        return new JSONArray(object.toString());
    }

    /**
     * Converts a {@link JSONArray} into a Java-standard List.
     *
     * @param jsonArray the JSONArray object to be converted
     * @return the list converted from a JSONArray object
     */
    public static List<Object> toList(JSONArray jsonArray)
    {
        try
        {
            if (isEmpty(jsonArray))
            {
                return Collections.emptyList();
            }
            List<Object> list = new ArrayList<>(jsonArray.length());
            for (int i = 0, length = jsonArray.length(); i < length; i++)
            {
                if (isEmpty(jsonArray.get(i)))
                {
                    list.add(null);
                }
                else
                {
                    list.add(jsonArray.get(i));
                }
            }
            return list;
        }
        catch (JSONException exception)
        {
            throw new IllegalStateException(exception);
        }
    }
}
