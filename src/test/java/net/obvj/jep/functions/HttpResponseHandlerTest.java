package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Stack;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.HttpResponseHandler.Strategy;
import net.obvj.jep.http.WebServiceResponse;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link HttpResponseHandler} functions
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpResponseHandlerTest
{
    private static final int STATUS_CODE_OK = 200;
    private static final String CONTENT_JSON = "{\"city\":\"SP\";\"temp\":\"30\"}";

    // Test subjects
    private static HttpResponseHandler httpStatusCodeFunction = new HttpResponseHandler(Strategy.GET_STATUS_CODE);
    private static HttpResponseHandler httpResponseFunction = new HttpResponseHandler(Strategy.GET_RESPONSE);

    @Mock
    private Response clientResponse;

    /**
     * Utility method to mock the Client response with a given HTTP status and body
     *
     * @param statusCode       the HTTP status code to be set
     * @param expectedResponse the response body to be set
     */
    private void mockClientResponse(int statusCode, String expectedResponse)
    {
        when(clientResponse.getStatus()).thenReturn(statusCode);
        when(clientResponse.getStatusInfo()).thenReturn(Status.fromStatusCode(statusCode));
        when(clientResponse.readEntity(String.class)).thenReturn(expectedResponse);
    }

    /**
     * Tests the status code retrieval for a ClientResponse object
     */
    @Test
    public void testStatusCodeWithClientResponse() throws ParseException
    {
        mockClientResponse(STATUS_CODE_OK, CONTENT_JSON);
        Stack<Object> parameters = CollectionsUtils
                .newParametersStack(WebServiceResponse.fromClientResponse(clientResponse));
        httpStatusCodeFunction.run(parameters);
        assertEquals(STATUS_CODE_OK, parameters.pop());
    }

    /**
     * Tests the status code retrieval for a ClientResponse object
     */
    @Test
    public void testResponseWithClientResponse() throws ParseException
    {
        mockClientResponse(STATUS_CODE_OK, CONTENT_JSON);
        Stack<Object> parameters = CollectionsUtils
                .newParametersStack(WebServiceResponse.fromClientResponse(clientResponse));
        httpResponseFunction.run(parameters);
        assertEquals(CONTENT_JSON, parameters.pop());
    }

    /**
     * Tests the status code retrieval for an invalid ClientResponse parameter
     */
    @Test(expected = ParseException.class)
    public void testWithInvalidClientResponse() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("test");
        httpStatusCodeFunction.run(parameters);
    }

}
