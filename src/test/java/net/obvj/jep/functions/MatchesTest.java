package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Matches} function
 *
 * @author oswaldo.bapvic.jr
 */
public class MatchesTest
{
    private static final String REGEX_FILE_EXTENSION_FINDER = "(\\.\\w+$)";
    private static final String REGEX_TWITTER_LINKS_FINDER = "([@][A-z]+)|([#][A-z]+)";

    private static final String FILE1_JSON = "file1.json";
    private static final String TWITTER_TWEET = "Sample tweet. #hashtag1 @author1";

    private static Matches matchesFunction = new Matches();

    private static final double FALSE = 0d;
    private static final double TRUE = 1d;

    /**
     * Tests with a valid string and a regex that returns a single match with the
     * "true-if-matches" strategy (should return 1 - true)
     */
    @Test
    public void testStringContainsFileExtensionWithTrueIfMatches() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_FILE_EXTENSION_FINDER);
        matchesFunction.run(parameters);
        assertEquals(TRUE, parameters.pop());
    }

    /**
     * Tests with a valid string and a regex that returns no match with the "true-if-matches"
     * strategy (should return 0 - false)
     */
    @Test
    public void testStringDoesNotContainFileExtensionWithTrueIfMatches() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, REGEX_FILE_EXTENSION_FINDER);
        matchesFunction.run(parameters);
        assertEquals(FALSE, parameters.pop());
    }

    /**
     * Tests with a null string
     */
    @Test
    public void testWithNullString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, REGEX_TWITTER_LINKS_FINDER);
        matchesFunction.run(parameters);
        assertEquals(0d, parameters.pop());
    }

    /**
     * Tests with an empty string
     */
    @Test
    public void testWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("", REGEX_TWITTER_LINKS_FINDER);
        matchesFunction.run(parameters);
        assertEquals(0d, parameters.pop());
    }

    /**
     * Tests with a null regex
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWithNullRegex() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, null);
        matchesFunction.run(parameters);
    }

}
