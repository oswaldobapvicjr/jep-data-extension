package net.obvj.jep.util;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class PlaceholderUtilsTest
{
    private static final String STR_EMPTY = StringUtils.EMPTY;
    private static final String STRING_NO_PLACEHOLDER = "$..book[?(@.price<10)]";
    private static final String STRING_ONE_PLACEHOLDER = "$..book[?(@.price<${myPrice})]";
    private static final String STRING_TWO_PLACEHOLDERS = "$..book[?(@.price<${myPrice}&&@.category=='${myCategory}')]";

    private static final String VARIABLE_NAME_MY_PRICE = "myPrice";
    private static final String VARIABLE_NAME_MY_CATEGORY = "myCategory";

    private static final int INT_PRICE_10 = 10;
    private static final String STRING_CATEGORY_FICTION = "Fiction";

    private static final String EXPECTED_STRING_ONE_PLACEHOLDER = STRING_NO_PLACEHOLDER;
    private static final String EXPECTED_STRING_TWO_PLACEHOLDERS = "$..book[?(@.price<10&&@.category=='Fiction')]";

    private static final Map<String, Object> VARIABLES_MAP = new HashMap<>(2);

    static
    {
        VARIABLES_MAP.put(VARIABLE_NAME_MY_PRICE, INT_PRICE_10);
        VARIABLES_MAP.put(VARIABLE_NAME_MY_CATEGORY, STRING_CATEGORY_FICTION);
    }

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
            Constructor<PlaceholderUtils> constructor = PlaceholderUtils.class.getDeclaredConstructor();
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

    @Test
    public void testHasPlaceholdersForAStringWithNoMatches()
    {
        assertFalse(PlaceholderUtils.hasPlaceholders(STRING_NO_PLACEHOLDER));
    }

    @Test
    public void testHasPlaceholdersForAStringWithOneMatch()
    {
        assertTrue(PlaceholderUtils.hasPlaceholders(STRING_ONE_PLACEHOLDER));
    }

    @Test
    public void testFindNumberOfPlaceholdersForAStringWithNoMatches()
    {
        List<String> matches = PlaceholderUtils.findPlaceholders(STRING_NO_PLACEHOLDER);
        assertTrue(matches.isEmpty());
    }

    @Test
    public void testFindNumberOfPlaceholdersForAStringWithOneMatch()
    {
        List<String> matches = PlaceholderUtils.findPlaceholders(STRING_ONE_PLACEHOLDER);
        assertEquals(1, matches.size());
    }

    @Test
    public void testFindNumberOfPlaceholdersForAStringWithTwoMatches()
    {
        List<String> matches = PlaceholderUtils.findPlaceholders(STRING_TWO_PLACEHOLDERS);
        assertEquals(2, matches.size());
    }

    @Test
    public void testReplacePlaceholdersForAStringWithNoMatch()
    {
        String newString = PlaceholderUtils.replacePlaceholders(STRING_NO_PLACEHOLDER, VARIABLES_MAP);
        assertEquals(STRING_NO_PLACEHOLDER, newString);
    }

    @Test
    public void testReplacePlaceholdersForAStringWithOneMatch()
    {
        String newString = PlaceholderUtils.replacePlaceholders(STRING_ONE_PLACEHOLDER, VARIABLES_MAP);
        assertEquals(EXPECTED_STRING_ONE_PLACEHOLDER, newString);
    }

    @Test
    public void testReplacePlaceholdersForAStringWithTwoMatches()
    {
        String newString = PlaceholderUtils.replacePlaceholders(STRING_TWO_PLACEHOLDERS, VARIABLES_MAP);
        assertEquals(EXPECTED_STRING_TWO_PLACEHOLDERS, newString);
    }

    @Test
    public void testMatchesForAPatternAndStringContainingOneMatch()
    {
        assertTrue(PlaceholderUtils.matches("${var}", PlaceholderUtils.PATTERN_VARIABLE_NAME));
    }

    @Test
    public void testMatchesForAPatternAndStringContainingNoMatch()
    {
        assertFalse(PlaceholderUtils.matches("var", PlaceholderUtils.PATTERN_VARIABLE_NAME));
    }

    @Test
    public void testMatchesForAPatternAndNullString()
    {
        assertFalse(PlaceholderUtils.matches(null, PlaceholderUtils.PATTERN_VARIABLE_NAME));
    }

    @Test
    public void testMatchesForAPatternAndEmptyString()
    {
        assertFalse(PlaceholderUtils.matches(STR_EMPTY, PlaceholderUtils.PATTERN_VARIABLE_NAME));
    }

    @Test
    public void testFindMatchesForAPatternAndStringContainingOneMatch()
    {
        assertEquals("test", PlaceholderUtils.findMatches("${test}", PlaceholderUtils.PATTERN_VARIABLE_NAME).get(0));
    }

    @Test
    public void testFindMatchesForAPatternAndStringContainingNoMatch()
    {
        assertEquals(0, PlaceholderUtils.findMatches("test", PlaceholderUtils.PATTERN_VARIABLE_NAME).size());
    }

    @Test
    public void testFindMatchesForAPatternAndAnEmptyString()
    {
        assertEquals(0, PlaceholderUtils.findMatches(STR_EMPTY, PlaceholderUtils.PATTERN_VARIABLE_NAME).size());
    }

    @Test
    public void testFindMatchForAPatternAndStringContainingOneMatch()
    {
        assertEquals("test", PlaceholderUtils.findMatch("${test}", PlaceholderUtils.PATTERN_VARIABLE_NAME));
    }

    @Test
    public void testFindMatchForAPatternAndStringContainingNoMatch()
    {
        assertEquals(STR_EMPTY, PlaceholderUtils.findMatch("test", PlaceholderUtils.PATTERN_VARIABLE_NAME));
    }

    @Test
    public void testFindMatchForAPatternAndAnEmptyString()
    {
        assertEquals(STR_EMPTY, PlaceholderUtils.findMatch(STR_EMPTY, PlaceholderUtils.PATTERN_VARIABLE_NAME));
    }

}
