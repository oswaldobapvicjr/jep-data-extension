package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.BinaryBooleanFunction.Operation;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link BinaryBooleanFunction} class
 *
 * @author oswaldo.bapvic.jr
 */
public class BinaryBooleanFunctionTest
{
    private static final String REGEX_TWITTER_LINKS_FINDER = "([@][A-z]+)|([#][A-z]+)";
    private static final String REGEX_FILE_EXTENSION_FINDER = "(\\.\\w+$)";

    private static final String FILE1_JSON = "file1.json";
    private static final String TWITTER_TWEET = "Sample tweet. #hashtag1 @author1";

    private static final String ABCDEF = "abcdef";
    private static final String ABC = "abc";
    private static final String DEF = "def";

    private static BinaryBooleanFunction matchesFunction = new BinaryBooleanFunction(Operation.STRING_MATCHES);
    private static BinaryBooleanFunction startsWithFunction = new BinaryBooleanFunction(Operation.STRING_STARTS_WITH);
    private static BinaryBooleanFunction endsWithFunction = new BinaryBooleanFunction(Operation.STRING_ENDS_WITH);

    /**
     * Tests the matches function with a valid string and a regex that returns a single match
     * (should return 1 - true)
     */
    @Test
    public void testMatchesWithStringContainingFileExtensionRegex() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_FILE_EXTENSION_FINDER);
        matchesFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.TRUE, parameters.pop());
    }

    /**
     * Tests the matches function with a valid string and a regex that returns no match
     * (should return 0 - false)
     */
    @Test
    public void testMatchesWithStringNotContainingFileExtensionRegex() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, REGEX_FILE_EXTENSION_FINDER);
        matchesFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the matches function with a null string
     */
    @Test
    public void testMatchesWithNullString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, REGEX_TWITTER_LINKS_FINDER);
        matchesFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the matches function with an empty string
     */
    @Test
    public void testMatchesWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("", REGEX_TWITTER_LINKS_FINDER);
        matchesFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the matches function with a null regex
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMatchesWithNullRegex() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, null);
        matchesFunction.run(parameters);
    }

    /**
     * Tests the startsWith function with a valid string and a satisfying suffix
     */
    @Test
    public void testStartsWithWithSatisfyingSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, ABC);
        startsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.TRUE, parameters.pop());
    }

    /**
     * Tests the startsWith function with a valid string and not-satisfying suffix
     */
    @Test
    public void testStartsWithWithNotSatisfyingSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, DEF);
        startsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the startsWith function with a null string
     */
    @Test
    public void testStartsWithNullString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, ABC);
        startsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the startsWith function with an empty string
     */
    @Test
    public void testStartsWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("", ABC);
        startsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the startsWith function with a null suffix
     */
    @Test
    public void testStartsWithNullSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, null);
        startsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the startsWith function with empty suffix
     */
    @Test
    public void testStartsWithEmptySuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, "");
        startsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.TRUE, parameters.pop());
    }

    /**
     * Tests the startsWith function with null string and suffix
     */
    @Test
    public void testStartsWithNullStringAndSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, null);
        startsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.TRUE, parameters.pop());
    }

    /**
     * Tests the endsWith function with a valid string and a satisfying suffix
     */
    @Test
    public void testEndsWithWithSatisfyingSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, DEF);
        endsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.TRUE, parameters.pop());
    }

    /**
     * Tests the endsWith function with a valid string and not-satisfying suffix
     */
    @Test
    public void testEndsWithWithNotSatisfyingSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, ABC);
        endsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the endsWith function with a null string
     */
    @Test
    public void testEndsWithNullString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, DEF);
        endsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the endsWith function with an empty string
     */
    @Test
    public void testEndsWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("", DEF);
        endsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the endsWith function with a null suffix
     */
    @Test
    public void testEndsWithNullSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, null);
        endsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.FALSE, parameters.pop());
    }

    /**
     * Tests the startsWith function with empty suffix
     */
    @Test
    public void testEndsWithEmptySuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(ABCDEF, "");
        endsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.TRUE, parameters.pop());
    }

    /**
     * Tests the endsWith function with null string and suffix
     */
    @Test
    public void testEndsWithNullStringAndSuffix() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, null);
        endsWithFunction.run(parameters);
        assertEquals(BinaryBooleanFunction.TRUE, parameters.pop());
    }
}
