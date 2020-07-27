package net.obvj.jep.functions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;

import net.obvj.jep.JEPContextFactory;

/**
 * Unit tests for the {@link FormatString} function
 *
 * @author oswaldo.bapvic.jr
 */
public class FormatStringTest
{
    private JEP jep;

    /**
     * Prepare objects before each test execution
     */
    @Before
    public void setup()
    {
        jep = JEPContextFactory.newContext();
    }

    /**
     * Evaluates the given expression
     */
    private Object evaluateExpression(String expression) throws ParseException
    {
        return jep.evaluate(jep.parse(expression));
    }

    /**
     * Test with no argument
     */
    @Test(expected = ParseException.class)
    public void testNoPatternOrArgumentFormat() throws ParseException
    {
        evaluateExpression("formatString()");
    }

    /**
     * Test with no argument
     */
    @Test(expected = ParseException.class)
    public void testNoArgumentFormat() throws ParseException
    {
        evaluateExpression("formatString(\"key=%s\")");
    }

    /**
     * Test with valid pattern and one string argument
     */
    @Test
    public void testValidPatternAndOneStringArgument() throws ParseException
    {
        assertThat(evaluateExpression("formatString(\"key=%s\", \"test\")"), is("key=test"));
    }

    /**
     * Test with valid pattern and two string arguments
     */
    @Test
    public void testValidPatternAndTwoStringArguments() throws ParseException
    {
        assertThat(evaluateExpression(
                "formatString(\"$..[?(@.from_user=='%s' && @.iso_language_code=='%s')].text\", \"anna_gatling\", \"en\")"),
                is("$..[?(@.from_user=='anna_gatling' && @.iso_language_code=='en')].text"));
    }

    /**
     * Test with valid pattern and two arguments being one numeric
     */
    @Test
    public void testValidPatternAndTwoArgumentsBeingOneNumberic() throws ParseException
    {
        assertThat(evaluateExpression("formatString(\"%s=%.0f\", \"test\", 10.1)"), is("test=10"));
    }
}
