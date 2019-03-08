package net.obvj.jep.util;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class RegexUtilsTest
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
            Constructor<RegexUtils> constructor = RegexUtils.class.getDeclaredConstructor();
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
        assertFalse(RegexUtils.hasUnixLikeVariablePlaceholders(STRING_NO_PLACEHOLDER));
    }

    @Test
    public void testHasPlaceholdersForAStringWithOneMatch()
    {
        assertTrue(RegexUtils.hasUnixLikeVariablePlaceholders(STRING_ONE_PLACEHOLDER));
    }

    @Test
    public void testFindNumberOfPlaceholdersForAStringWithNoMatches()
    {
        List<String> matches = RegexUtils.findUnixLikeVariablePlaceholders(STRING_NO_PLACEHOLDER);
        assertTrue(matches.isEmpty());
    }

    @Test
    public void testFindNumberOfPlaceholdersForAStringWithOneMatch()
    {
        List<String> matches = RegexUtils.findUnixLikeVariablePlaceholders(STRING_ONE_PLACEHOLDER);
        assertEquals(1, matches.size());
    }

    @Test
    public void testFindNumberOfPlaceholdersForAStringWithTwoMatches()
    {
        List<String> matches = RegexUtils.findUnixLikeVariablePlaceholders(STRING_TWO_PLACEHOLDERS);
        assertEquals(2, matches.size());
    }

    @Test
    public void testReplacePlaceholdersForAStringWithNoMatch()
    {
        String newString = RegexUtils.replacePlaceholdersWithVariables(STRING_NO_PLACEHOLDER, VARIABLES_MAP);
        assertEquals(STRING_NO_PLACEHOLDER, newString);
    }

    @Test
    public void testReplacePlaceholdersForAStringWithOneMatch()
    {
        String newString = RegexUtils.replacePlaceholdersWithVariables(STRING_ONE_PLACEHOLDER, VARIABLES_MAP);
        assertEquals(EXPECTED_STRING_ONE_PLACEHOLDER, newString);
    }

    @Test
    public void testReplacePlaceholdersForAStringWithTwoMatches()
    {
        String newString = RegexUtils.replacePlaceholdersWithVariables(STRING_TWO_PLACEHOLDERS, VARIABLES_MAP);
        assertEquals(EXPECTED_STRING_TWO_PLACEHOLDERS, newString);
    }

    @Test
    public void testMatchesForAPatternAndStringContainingOneMatch()
    {
        assertTrue(RegexUtils.matches("${var}", RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME));
    }

    @Test
    public void testMatchesForAPatternAndStringContainingNoMatch()
    {
        assertFalse(RegexUtils.matches("var", RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME));
    }

    @Test
    public void testMatchesForAPatternAndNullString()
    {
        assertFalse(RegexUtils.matches(null, RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME));
    }

    @Test
    public void testMatchesForAPatternAndEmptyString()
    {
        assertFalse(RegexUtils.matches(STR_EMPTY, RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME));
    }

    @Test
    public void testFindMatchesForAPatternAndStringContainingOneMatch()
    {
        assertEquals("test", RegexUtils.findMatches("${test}", RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME).get(0));
    }

    @Test
    public void testFindMatchesForAPatternAndStringContainingNoMatch()
    {
        assertEquals(0, RegexUtils.findMatches("test", RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME).size());
    }

    @Test
    public void testFindMatchesForAPatternAndAnEmptyString()
    {
        assertEquals(0, RegexUtils.findMatches(STR_EMPTY, RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME).size());
    }

    @Test
    public void testFindMatchForAPatternAndStringContainingOneMatch()
    {
        assertEquals("test", RegexUtils.firstMatch("${test}", RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME));
    }

    @Test
    public void testFindMatchForAPatternAndStringContainingNoMatch()
    {
        assertEquals(STR_EMPTY, RegexUtils.firstMatch("test", RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME));
    }

    @Test
    public void testFindMatchForAPatternAndAnEmptyString()
    {
        assertEquals(STR_EMPTY, RegexUtils.firstMatch(STR_EMPTY, RegexUtils.PATTERN_UNIX_LIKE_VARIABLE_NAME));
    }

}
