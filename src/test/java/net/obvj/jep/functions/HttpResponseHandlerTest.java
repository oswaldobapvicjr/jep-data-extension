package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.nfunk.jep.ParseException;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jersey.api.client.ClientResponse;

import net.obvj.jep.functions.HttpResponseHandler.HttpResponseHandlerStrategy;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link HttpResponseHandler} functions
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
public class HttpResponseHandlerTest
{
    private static final int STATUS_CODE_OK = 200;
    private static final String CONTENT_JSON = "{\"city\":\"SP\";\"temp\":\"30\"}";

    // Test subjects
    private static HttpResponseHandler httpStatusCodeFunction = new HttpResponseHandler(
            HttpResponseHandlerStrategy.GET_STATUS_CODE);
    private static HttpResponseHandler httpResponseFunction = new HttpResponseHandler(
            HttpResponseHandlerStrategy.GET_RESPONSE);

    @Mock
    private ClientResponse clientResponse;

    /**
     * Utility method to mock the Client response with a given HTTP status and body
     *
     * @param statusCode the HTTP status code to be set
     * @param expectedResponse the response body to be set
     */
    private void mockClientResponse(int statusCode, String expectedResponse)
    {
        when(clientResponse.getStatus()).thenReturn(statusCode);
        when(clientResponse.getEntity(String.class)).thenReturn(expectedResponse);
    }

    /**
     * Tests the status code retrieval for a ClientResponse object
     */
    @Test
    public void testStatusCodeWithClientResponse() throws org.nfunk.jep.ParseException, IOException
    {
        mockClientResponse(STATUS_CODE_OK, CONTENT_JSON);
        Stack<Object> parameters = CollectionsUtils.newParametersStack(clientResponse);
        httpStatusCodeFunction.run(parameters);
        assertEquals(STATUS_CODE_OK, parameters.pop());
    }

    /**
     * Tests the status code retrieval for a ClientResponse object
     */
    @Test
    public void testResponseWithClientResponse() throws org.nfunk.jep.ParseException, IOException
    {
        mockClientResponse(STATUS_CODE_OK, CONTENT_JSON);
        Stack<Object> parameters = CollectionsUtils.newParametersStack(clientResponse);
        httpResponseFunction.run(parameters);
        assertEquals(CONTENT_JSON, parameters.pop());
    }

    /**
     * Tests the status code retrieval for an invalid ClientResponse parameter
     */
    @Test(expected = ParseException.class)
    public void testWithInvalidClientResponse() throws org.nfunk.jep.ParseException, IOException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("test");
        httpStatusCodeFunction.run(parameters);
    }

}
