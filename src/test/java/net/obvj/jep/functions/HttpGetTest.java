package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.nfunk.jep.ParseException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.obvj.jep.http.WebServiceUtils;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link HttpGet} function
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(WebServiceUtils.class)
public class HttpGetTest
{
    private static final String URL = "http://sampleservice.com/data/v1/wheather/sp";
    private static final String CONTENT_JSON = "{\"city\":\"SP\";\"temp\":\"30\"}";
    private static final String CONTENT_XML = "<xml><city>SP</city><temp>30</temp></xml>";
    private static final String ACCEPT_APPLICATION_XML = "Accept=application/xml";
    private static final Map<String, String> HEADERS = CollectionsUtils.asMap(ACCEPT_APPLICATION_XML);

    // Test subject
    private static HttpGet function = new HttpGet();

    /**
     * Setup test objects before each test
     */
    @Before
    public void setup()
    {
        PowerMockito.mockStatic(WebServiceUtils.class);
    }

    /**
     * Runs the function with the given parameters
     */
    private void run(Stack<Object> parameters) throws ParseException
    {
        function.setCurNumberOfParameters(parameters.size());
        function.run(parameters);
    }

    /**
     * Checks that the correct method from WebServiceUtils was called. Successful scenario
     * with only one parameter (URL)
     */
    @Test
    public void testSuccessfulScenarioWithUrlParam() throws org.nfunk.jep.ParseException, IOException
    {
        PowerMockito.when(WebServiceUtils.getAsString(URL)).thenReturn(CONTENT_JSON);

        Stack<Object> parameters = CollectionsUtils.newParametersStack(URL);
        run(parameters);

        // Check that the content from mock is returned
        assertEquals(CONTENT_JSON, parameters.pop());

        // Check that the correct method from the mock was called once
        PowerMockito.verifyStatic(WebServiceUtils.class, BDDMockito.times(1));
        WebServiceUtils.getAsString(URL);
    }

    /**
     * Checks that the correct method from WebServiceUtils was called. Successful scenario
     * with two parameters (URL and header)
     */
    @Test
    public void testSuccessfulScenarioWithUrlAndHeaderParams() throws org.nfunk.jep.ParseException, IOException
    {
        PowerMockito.when(WebServiceUtils.getAsString(URL, HEADERS)).thenReturn(CONTENT_XML);

        Stack<Object> parameters = CollectionsUtils.newParametersStack(URL, HEADERS);
        run(parameters);

        // Check that the content from mocked is returned
        assertEquals(CONTENT_XML, parameters.pop());

        // Check that the correct method from the mock was called once
        PowerMockito.verifyStatic(WebServiceUtils.class, BDDMockito.times(1));
        WebServiceUtils.getAsString(URL, HEADERS);
    }

    /**
     * Checks that the function does not accept more than 3 parameters
     */
    @Test(expected = ParseException.class)
    public void testWithFourParameters() throws org.nfunk.jep.ParseException, IOException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(URL, ACCEPT_APPLICATION_XML, null, "test");
        run(parameters);
    }

}
