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
    private static final String REGEX_FILE_EXTENSION_FINDER = "(\\.\\w+$)";
    private static final String REGEX_TWITTER_LINKS_FINDER = "([@][A-z]+)|([#][A-z]+)";

    private static final String FILE1_JSON = "file1.json";
    private static final String TWITTER_TWEET = "Sample tweet. #hashtag1 @author1";

    private static BinaryBooleanFunction matchesFunction = new BinaryBooleanFunction(Operation.STRING_MATCHES);

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

}
