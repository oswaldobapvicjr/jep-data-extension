package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.FindMatches.ReturnStrategy;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Replace} function
 *
 * @author oswaldo.bapvic.jr
 */
public class FindMatchesTest
{
    private static final String REGEX_FILE_EXTENSION_FINDER = "(\\.\\w+$)";
    private static final String REGEX_TWITTER_LINKS_FINDER = "([@][A-z]+)|([#][A-z]+)";

    private static final String FILE1_JSON = "file1.json";
    private static final String EXPECTED_EXTENSION = ".json";

    private static final String TWITTER_TWEET = "Women make the world beautiful, they protect it and keep it alive. They bring the grace of renewal, the embrace of inclusion, and the courage to give of oneself. #InternationalWomensDay @pontifex";
    private static final String HASHTAG_INTERNATIONAL_WOMENS_DAY = "#InternationalWomensDay";
    private static final List<String> EXPECTED_TWITTER_LINKS = Arrays.asList(HASHTAG_INTERNATIONAL_WOMENS_DAY,
            "@pontifex");

    private static FindMatches findMatchesFunction = new FindMatches(ReturnStrategy.ALL_MATCHES);
    private static FindMatches findMatchFunction = new FindMatches(ReturnStrategy.FIRST_MATCH);
    private static FindMatches matchesFunction = new FindMatches(ReturnStrategy.TRUE_IF_MATCHES);

    /**
     * Tests with a valid string and a regex that returns a single match in a list with the
     * "all-matches" strategy
     */
    @Test
    public void testExtractFileExtensionFromStringWithAllMatches() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_FILE_EXTENSION_FINDER);
        findMatchesFunction.run(parameters);
        List<Object> matchesList = CollectionsUtils.asList(parameters.pop());
        assertEquals(1, matchesList.size());
        assertTrue("The file extension was not found in the returned list", matchesList.contains(EXPECTED_EXTENSION));
    }

    /**
     * Tests with a valid string and a regex that returns a single match with the
     * "first-match" strategy
     */
    @Test
    public void testExtractFileExtensionFromStringWithFirstMatch() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_FILE_EXTENSION_FINDER);
        findMatchFunction.run(parameters);
        assertEquals(EXPECTED_EXTENSION, parameters.pop());
    }

    /**
     * Tests with a valid string and a regex that returns two matches in a list with the
     * "all-matches" strategy
     */
    @Test
    public void testExtractTwitterLinksFromStringWithAllMatches() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, REGEX_TWITTER_LINKS_FINDER);
        findMatchesFunction.run(parameters);
        List<Object> matchesList = CollectionsUtils.asList(parameters.pop());
        assertEquals(2, matchesList.size());
        assertTrue("Not all expressions were found in the returned list",
                matchesList.containsAll(EXPECTED_TWITTER_LINKS));
    }

    /**
     * Tests with a valid string and a regex that returns two matches in a list with the
     * "first-match" strategy
     */
    @Test
    public void testExtractTwitterLinksFromStringWithFirstMatch() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, REGEX_TWITTER_LINKS_FINDER);
        findMatchFunction.run(parameters);
        assertEquals(HASHTAG_INTERNATIONAL_WOMENS_DAY, parameters.pop());
    }

    /**
     * Tests with a valid string and a regex that returns a single match with the
     * "true-if-matches" strategy (should return 1 - true)
     */
    @Test
    public void testStringContainsFileExtensionWithTrueIfMatches() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_FILE_EXTENSION_FINDER);
        matchesFunction.run(parameters);
        assertEquals(1d, parameters.pop());
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
        assertEquals(0d, parameters.pop());
    }

    /**
     * Tests with a null string
     */
    @Test
    public void testWithNullString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, REGEX_TWITTER_LINKS_FINDER);
        findMatchesFunction.run(parameters);
        List<Object> matchesList = CollectionsUtils.asList(parameters.pop());
        assertEquals(0, matchesList.size());
    }

    /**
     * Tests with an empty string
     */
    @Test
    public void testWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("", REGEX_TWITTER_LINKS_FINDER);
        findMatchesFunction.run(parameters);
        List<Object> matchesList = CollectionsUtils.asList(parameters.pop());
        assertEquals(0, matchesList.size());
    }

    /**
     * Tests with a null regex
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWithNullRegex() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, null);
        findMatchesFunction.run(parameters);
    }

}
