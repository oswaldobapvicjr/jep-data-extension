package net.obvj.jep.functions;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link FormatString} function
 *
 * @author oswaldo.bapvic.jr
 */
public class FormatStringTest
{
    // Test data
    private static final String PATTERN_KEY_S = "key=%s";
    private static final String TEST = "test";
    private static final String KEY_TEST = "key=test";

    private static final String PATTERN_S_TRUNC_FLOAT = "%s=%.0f";
    private static final double TEN = 10.1;
    private static final String TEST_TEN = "test=10";

    private static final String PATTERN_JSONPATH = "$..[?(@.from_user=='%s' && @.iso_language_code=='%s')].text";
    private static final String USER = "anna_gatling";
    private static final String LANGUAGE = "en";
    private static final String FORMATTED_JSONPATH = "$..[?(@.from_user=='anna_gatling' && @.iso_language_code=='en')].text";

    // Test subject
    private static FormatString function = new FormatString();

    /**
     * Test with no argument
     */
    @Test(expected = ParseException.class)
    public void testNoPatternOrArgumentFormat() throws ParseException
    {
        function.run(CollectionsUtils.newParametersStack());
    }

    /**
     * Test with no argument
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNoArgumentFormat() throws ParseException
    {
        function.run(CollectionsUtils.newParametersStack(PATTERN_KEY_S));
    }

    /**
     * Test with valid pattern and one string argument
     */
    @Test
    public void testValidPatternAndOneStringArgument() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(PATTERN_KEY_S, TEST);
        function.run(parameters);
        assertThat(parameters.pop(), is(KEY_TEST));
    }

    /**
     * Test with valid pattern and two string arguments
     */
    @Test
    public void testValidPatternAndTwoStringArguments() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(PATTERN_JSONPATH, USER, LANGUAGE);
        function.run(parameters);
        assertThat(parameters.pop(), is(FORMATTED_JSONPATH));
    }

    /**
     * Test with valid pattern and two arguments being one numeric
     */
    @Test
    public void testValidPatternAndTwoArgumentsBeingOneNumberic() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(PATTERN_S_TRUNC_FLOAT, TEST, TEN);
        function.run(parameters);
        assertThat(parameters.pop(), is(TEST_TEN));
    }
}
