package net.obvj.jep.util;

import static net.obvj.junit.utils.matchers.InstantiationNotAllowedMatcher.instantiationNotAllowed;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

import com.jayway.jsonpath.InvalidPathException;

/**
 * Unit tests for the {@link JsonUtils} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class JsonUtilsTest
{
    private static final String STR_JSON_EMPTY = "{}";

    private static final String STR_ENTRY_1 = "1";
    private static final String STR_ENTRY_2 = "2";

    private static final String JSONPATH_VALID = "$.array[0]";
    private static final String JSONPATH_INVALID = ".array[0";

    private static final String STR_JSON_STORE = "{\r\n"
            + "   \"store\" : {\r\n"
            + "      \"book\" : [\r\n"
            + "         {\r\n"
            + "            \"category\" : \"reference\",\r\n"
            + "            \"author\" : \"Nigel Rees\",\r\n"
            + "            \"title\" : \"Sayings of the Century\",\r\n"
            + "            \"price\" : 8.95\r\n"
            + "         },\r\n"
            + "         {\r\n"
            + "            \"category\" : \"fiction\",\r\n"
            + "            \"author\" : \"Evelyn Waugh\",\r\n"
            + "            \"title\" : \"Sword of Honour\",\r\n"
            + "            \"price\" : 12.99\r\n"
            + "         },\r\n"
            + "         {\r\n"
            + "            \"category\" : \"fiction\",\r\n"
            + "            \"author\" : \"Herman Melville\",\r\n"
            + "            \"title\" : \"Moby Dick\",\r\n"
            + "            \"isbn\" : \"0-553-21311-3\",\r\n"
            + "            \"price\" : 8.99\r\n"
            + "         },\r\n"
            + "         {\r\n"
            + "            \"category\" : \"fiction\",\r\n"
            + "            \"author\" : \"J. R. R. Tolkien\",\r\n"
            + "            \"title\" : \"The Lord of the Rings\",\r\n"
            + "            \"isbn\" : \"0-395-19395-8\",\r\n"
            + "            \"price\" : 22.99\r\n"
            + "         }\r\n"
            + "      ],\r\n"
            + "      \"bicycle\" : {\r\n"
            + "         \"color\" : \"red\",\r\n"
            + "         \"price\" : 19.95\r\n"
            + "      }\r\n"
            + "   },\r\n"
            + "   \"expensive\" : 10\r\n"
            + "}";

    private static JSONObject JSON_STORE;

    private static final String STR_BOOK_SWORD = "Sword of Honour";
    private static final String STR_BOOK_MOBY = "Moby Dick";
    private static final String STR_BOOK_LORD = "The Lord of the Rings";

    private static final String JSONPATH_SEARCH_ALL_FICTION_BOOKS = "$..[?(@.category=='fiction')].title";
    private static final String JSONPATH_SEARCH_CHEAP_FICTION_BOOKS = "$..[?(@.category=='fiction' && @.price < $.expensive)].title";

    private static final List<String> ALL_FICTION_BOOKS = Arrays.asList(STR_BOOK_SWORD, STR_BOOK_MOBY, STR_BOOK_LORD);
    private static final List<String> CHEAP_FICTION_BOOKS = Arrays.asList(STR_BOOK_MOBY);

    static
    {
        try
        {
            JSON_STORE = new JSONObject(STR_JSON_STORE);
        }
        catch (JSONException e)
        {
            fail("Unable to create test data. Please review the test class");
        }
    }

    /**
     * Tests that no instances of this utility class are created
     */
    @Test
    public void testNoInstancesAllowed()
    {
        assertThat(JsonUtils.class, instantiationNotAllowed());
    }

    /**
     * Tests if the empty check succeeds for a null object
     */
    @Test
    public void testEmptyCheckForANullObject()
    {
        assertTrue(JsonUtils.isEmpty(null));
    }

    /**
     * Tests if the empty check succeeds for an empty object
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testEmptyCheckForAnEmptyJSON() throws JSONException
    {
        assertTrue(JsonUtils.isEmpty(new JSONObject(STR_JSON_EMPTY)));
    }

    /**
     * Tests if the empty check succeeds for an empty JSONArray
     *
     * @throws JSONException in case of exceptions handling the JSON array
     */
    @Test
    public void testEmptyCheckForAnEmptyJSONArray() throws JSONException
    {
        assertTrue(JsonUtils.isEmpty(new JSONArray()));
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
        jsonArray.put(STR_ENTRY_1);
        jsonArray.put(STR_ENTRY_2);
        List<Object> convertedList = JsonUtils.toList(jsonArray);
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
        jsonArray1.put(STR_ENTRY_1);
        jsonArray1.put(STR_ENTRY_2);
        JSONArray jsonArray2 = new JSONArray();
        jsonArray2.put(STR_ENTRY_1);
        jsonArray1.put(jsonArray2);
        List<Object> convertedList = JsonUtils.toList(jsonArray1);
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
        jsonArray.put(STR_ENTRY_1);
        jsonArray.put(STR_ENTRY_2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("testKey", "testValue");
        jsonArray.put(jsonObject);
        List<Object> convertedList = JsonUtils.toList(jsonArray);
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
        List<Object> convertedList = JsonUtils.toList(jsonArray);
        assertEquals(1, convertedList.size());
        assertEquals(null, convertedList.get(0));
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
        List<Object> convertedList = JsonUtils.toList(jsonArray);
        assertEquals(Collections.EMPTY_LIST, convertedList);
    }

    /**
     * Tests the conversion of a JSONArray to list with an exception
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test(expected = IllegalStateException.class)
    public void testConvertJSONArrayToListWithException() throws JSONException
    {
        JSONArray jsonArray = Mockito.mock(JSONArray.class);
        Mockito.when(jsonArray.length()).thenReturn(1);
        Mockito.when(jsonArray.get(0)).thenThrow(new JSONException("message1"));
        JsonUtils.toList(jsonArray);
    }

    /**
     * Tests the conversion of a null parameter
     *
     * @throws JSONException in case of exceptions handling the JSONArray object
     */
    @Test
    public void testConvertNullParameter()
    {
        List<Object> convertedList = JsonUtils.toList(null);
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
        assertNotNull(JsonUtils.compileJsonPath(JSONPATH_VALID));
    }

    /**
     * Tests the compile of an invalid JSONArray
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test(expected = InvalidPathException.class)
    public void testCompileInvalidJSONArray() throws JSONException
    {
        JsonUtils.compileJsonPath(JSONPATH_INVALID);
    }

    /**
     * Tests convert String (that represents a valid JSON) to JSONObject
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testConvertValidJSONStringToJSON() throws JSONException
    {
        JSONObject jsonObject = JsonUtils.toJSONObject(STR_JSON_EMPTY);
        assertNotNull(jsonObject);
    }

    /**
     * Tests convert null to JSONObject, an empty JSONObject is returned
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testConvertNullToJSONObject() throws JSONException
    {
        JSONObject jsonObject = JsonUtils.toJSONObject(null);
        assertNotNull(jsonObject);
        assertThat(jsonObject.length(), is(equalTo(0)));
    }

    /**
     * Tests convert null to JSONArrays, an empty JSONArray is returned
     *
     * @throws JSONException in case of exceptions handling the JSON array
     */
    @Test
    public void testConvertNullToJSONArray() throws JSONException
    {
        JSONArray jsonArray = JsonUtils.toJSONArray(null);
        assertNotNull(jsonArray);
        assertThat(jsonArray.length(), is(equalTo(0)));
    }

    /**
     * Tests that a JSONObject is not modified when passed to convertToJsonObject()
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testConvertJSONObject() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        assertEquals(jsonObject, JsonUtils.toJSONObject(jsonObject));
    }

    /**
     * Tests that a JSONArray is not modified when passed to convertToJsonArray()
     *
     * @throws JSONException in case of exceptions handling the JSON array
     */
    @Test
    public void testConvertJSONArray() throws JSONException
    {
        JSONArray jsonArray = new JSONArray();
        assertEquals(jsonArray, JsonUtils.toJSONArray(jsonArray));
    }

    /**
     * Tests retrieval of one element from a JSONArray with one element
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReturnSingleValueFromJSONArrayWithOneElement() throws JSONException
    {
        JSONArray jsonAarray = new JSONArray();
        jsonAarray.put(STR_ENTRY_1);
        Object result = JsonUtils.getSingleValueFromJSONArray(jsonAarray);
        assertTrue(result instanceof String);
        assertEquals(STR_ENTRY_1, result);
    }

    /**
     * Tests retrieval of a JSONArray from a JSONArray with multiple elements
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReturnSingleValueFromJSONArrayWithMultipleElements() throws JSONException
    {
        JSONArray jsonAarray = new JSONArray();
        jsonAarray.put(STR_ENTRY_1);
        jsonAarray.put(STR_ENTRY_2);
        Object result = JsonUtils.getSingleValueFromJSONArray(jsonAarray);
        assertTrue(result instanceof JSONArray);
        assertEquals(jsonAarray, result);
    }

    /**
     * Tests retrieve of a JSONArray returns the same element if the input is not a JSONArray
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReturnSingleValueFromJSONArrayForSingleObject() throws JSONException
    {
        Object result = JsonUtils.getSingleValueFromJSONArray(STR_ENTRY_1);
        assertEquals(STR_ENTRY_1, result);
    }

    /**
     * Tests reading of a JSONPath for a null JSON
     */
    @Test
    public void testReadJsonPathForNullJson() throws JSONException
    {
        assertNull(JsonUtils.readJsonPath(null, StringUtils.EMPTY));
    }

    /**
     * Tests retrieval of a JSONArray for a JSONPath that returns several objects with the
     * "extractSinglElement" option disabled
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReadJsonPathWithoutArrayExtractionDisabledForJsonPathThatReturnsAnArray() throws JSONException
    {
        JSONArray result = (JSONArray) JsonUtils.readJsonPath(JSON_STORE, JSONPATH_SEARCH_ALL_FICTION_BOOKS, false);
        assertEquals(3, result.length());
        assertTrue(CollectionsUtils.asList(result).containsAll(ALL_FICTION_BOOKS));
    }

    /**
     * Tests retrieval of a JSONArray for a JSONPath that returns several objects with the
     * "extractSinglElement" option disabled
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReadJsonPathWithoutArrayExtractionEnabledForJsonPathThatReturnsAnArray() throws JSONException
    {
        JSONArray result = (JSONArray) JsonUtils.readJsonPath(JSON_STORE, JSONPATH_SEARCH_ALL_FICTION_BOOKS, true);
        assertEquals(3, result.length());
        assertTrue(CollectionsUtils.asList(result).containsAll(ALL_FICTION_BOOKS));
    }

    /**
     * Tests retrieval of a JSONArray for a JSONPath that returns a single object with the
     * "extractSinglElement" option disabled. The result shall be a singleton array.
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReadJsonPathWithoutArrayExtractionDisabledForJsonPathThatReturnsASingleObject() throws JSONException
    {
        JSONArray result = (JSONArray) JsonUtils.readJsonPath(JSON_STORE, JSONPATH_SEARCH_CHEAP_FICTION_BOOKS, false);
        assertEquals(1, result.length());
        assertTrue(CollectionsUtils.asList(result).containsAll(CHEAP_FICTION_BOOKS));
    }

    /**
     * Tests retrieval of a JSONArray for a JSONPath that returns a single object with the
     * "extractSinglElement" option enabled. The result shall be a string.
     *
     * @throws JSONException in case of exceptions handling the JSON object
     */
    @Test
    public void testReadJsonPathWithoutArrayExtractionEnabledForJsonPathThatReturnsASingleObject() throws JSONException
    {
        String result = (String) JsonUtils.readJsonPath(JSON_STORE, JSONPATH_SEARCH_CHEAP_FICTION_BOOKS, true);
        assertEquals(STR_BOOK_MOBY, result);
    }

}
