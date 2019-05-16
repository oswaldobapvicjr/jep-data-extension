package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
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
    private static final String CONTENT = "{\"city\":\"SP\";\"temp\":\"30\"}";

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
     * Checks that the correct method from WebServiceUtils was called. Successful scenario
     */
    @Test
    public void testSuccessfulScenario() throws org.nfunk.jep.ParseException, IOException
    {
        PowerMockito.when(WebServiceUtils.getAsString(URL)).thenReturn(CONTENT);

        Stack<Object> parameters = CollectionsUtils.newParametersStack(URL);
        function.run(parameters);

        // Check that the content from mocked TextFileReader is returned
        assertEquals(CONTENT, parameters.pop());

        // Check that the correct method from the mock was called once
        PowerMockito.verifyStatic(WebServiceUtils.class, BDDMockito.times(1));
        WebServiceUtils.getAsString(URL);
    }

}
