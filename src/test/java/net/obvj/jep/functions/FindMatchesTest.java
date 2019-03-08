package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

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
    private static final List<String> EXPECTED_TWITTER_LINKS = Arrays.asList("#InternationalWomensDay", "@pontifex");

    private static FindMatches function = new FindMatches();

    /**
     * Tests the replacement function with a valid string and a regex that returns a single
     * match in a list
     */
    @Test
    public void testExtractFileExtensionFromString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_FILE_EXTENSION_FINDER);
        function.run(parameters);
        List<Object> matchesList = CollectionsUtils.asList(parameters.pop());
        assertEquals(1, matchesList.size());
        assertTrue("The file extension was not found in the returned list", matchesList.contains(EXPECTED_EXTENSION));
    }

    /**
     * Tests the replacement function with a valid string and a regex that returns two matches
     * in a list
     */
    @Test
    public void testExtractTwitterLinksFromString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(TWITTER_TWEET, REGEX_TWITTER_LINKS_FINDER);
        function.run(parameters);
        List<Object> matchesList = CollectionsUtils.asList(parameters.pop());
        assertEquals(2, matchesList.size());
        assertTrue("Not all expressions were found in the returned list",
                matchesList.containsAll(EXPECTED_TWITTER_LINKS));
    }

}
