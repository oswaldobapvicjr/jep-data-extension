package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.jayway.jsonpath.InvalidPathException;

public class JsonUtilsTest
{
    private static final String STR_JSON_EMPTY = "{}";
    private static final String ENTRY_1 = "1";
    private static final String ENTRY_2 = "2";
    private static final String JSONPATH_VALID = "$.partyNames[0]";
    private static final String JSONPATH_INVALID = ".partyNames[0";

    /**
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test(expected = InvocationTargetException.class)
    public void testNoInstancesAllowed() throws Exception
    {
        try
        {
            Constructor<JsonUtils> constructor = JsonUtils.class.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));

            constructor.setAccessible(true);
            constructor.newInstance();
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(IllegalStateException.class, cause.getClass());
            assertEquals("Utility class", cause.getMessage());
            throw ite;
        }
    }

    /**
     * Tests if the null check succeeds for a null object
     */
    @Test
    public void testNullCheckForANullObject()
    {
        assertTrue(JsonUtils.isNull(null));
    }

    /**
     * Tests if the null check succeeds for an empty object
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testNullCheckForAnEmptyJSON() throws JSONException
    {
        assertTrue(JsonUtils.isNull(new JSONObject(STR_JSON_EMPTY)));
    }

    /**
     * Tests the conversion of a simple JSONArray to List
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test
    public void testConvertJSONArrayToList()
    {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(ENTRY_1);
        jsonArray.put(ENTRY_2);
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(jsonArray);
        assertEquals(jsonArray.length(), convertedList.size());
    }

    /**
     * Tests the conversion of a JSONArray with inner JSONArray to List
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test
    public void testConvertJSONArrayWithInnerJSONArray()
    {
        JSONArray jsonArray1 = new JSONArray();
        jsonArray1.put(ENTRY_1);
        jsonArray1.put(ENTRY_2);
        JSONArray jsonArray2 = new JSONArray();
        jsonArray2.put(ENTRY_1);
        jsonArray1.put(jsonArray2);
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(jsonArray1);
        assertEquals(jsonArray1.length(), convertedList.size());
    }

    /**
     * Tests the conversion of a JSONArray with inner JSONObject
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test
    public void testConvertJSONArrayWithInnerJSONObject() throws JSONException
    {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(ENTRY_1);
        jsonArray.put(ENTRY_2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("testKey", "testValue");
        jsonArray.put(jsonObject);
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(jsonArray);
        assertEquals(jsonArray.length(), convertedList.size());
    }

    /**
     * Tests the conversion of a JSONArray with null entry
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test
    public void testConvertJSONArrayWithNullEntry()
    {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(JSONObject.NULL);
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(jsonArray);
        assertEquals(Collections.EMPTY_LIST, convertedList);
    }

    /**
     * Tests the conversion of a JSONArray with empty entry
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test
    public void testConvertJSONArrayWithEmptyEntry()
    {
        JSONArray jsonArray = new JSONArray();
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(jsonArray);
        assertEquals(Collections.EMPTY_LIST, convertedList);
    }

    /**
     * Tests the conversion of a null parameter
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test
    public void testConvertNullParameter()
    {
        List<Object> convertedList = JsonUtils.convertJSONArrayToList(null);
        assertEquals(Collections.EMPTY_LIST, convertedList);
    }

    /**
     * Tests the compile of a valid JSONArray
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testCompileValidJSONArray() throws JSONException
    {
        JsonUtils.compileJsonPath(JSONPATH_VALID);
    }

    /**
     * Tests the compile of an invalid JSONArray
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test(expected = InvalidPathException.class)
    public void testCompileInvalidJSONArray() throws JSONException
    {
        assertNull(JsonUtils.compileJsonPath(JSONPATH_INVALID));
    }

    /**
     * Tests convert String (that represents a valid JSON) to JSONObject
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testConvertValidJSONStringToJSON() throws JSONException
    {
        JSONObject jsonObject = JsonUtils.convertToJSONObject(STR_JSON_EMPTY);
        assertNotNull(jsonObject);
    }

    /**
     * Tests convert null to JSONObject, an empty JSONObject is returned
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testConvertNullToJSON() throws JSONException
    {
        JSONObject jsonObject = JsonUtils.convertToJSONObject(null);
        assertNotNull(jsonObject);
        assertTrue(jsonObject.length() == 0);
    }

    /**
     * Tests return of one element from a JSONArray with one element
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReturnFromJSONArrayWithOneElement() throws JSONException
    {
        JSONArray jsonAarray = new JSONArray();
        jsonAarray.put(ENTRY_1);
        Object result = JsonUtils.getSingleValueFromJSONArray(jsonAarray);
        assertTrue(result instanceof String);
        assertEquals(ENTRY_1, result);
    }

    /**
     * Tests retrieve of a JSONArray from a JSONArray with multiple elements
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReturnFromJSONArrayWithMultipleElements() throws JSONException
    {
        JSONArray jsonAarray = new JSONArray();
        jsonAarray.put(ENTRY_1);
        jsonAarray.put(ENTRY_2);
        Object result = JsonUtils.getSingleValueFromJSONArray(jsonAarray);
        assertTrue(result instanceof JSONArray);
        assertEquals(jsonAarray, result);
    }

    /**
     * Tests reading of a JSONPath for a null JSON
     */
    @Test
    public void testReadJsonPathForNullJson() throws JSONException
    {
        assertNull(JsonUtils.readJsonPath(null, StringUtils.EMPTY));
    }

}
