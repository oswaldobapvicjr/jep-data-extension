package net.obvj.jep.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import net.obvj.jep.util.UtilitiesCommons;

/**
 * Unit tests for the {@link WebServiceUtils} class
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Client.class)
public class WebServiceUtilsTest
{
    // Test data
    private static final String GET = "GET";
    private static final String URL = "http://localhost/dummy";
    private static final String MOCKED_JSON_AS_STRING = "{\"foo\":\"bar\"}";

    @Mock
    private ClientResponse clientResponse;

    @Mock
    private WebResource webResource;

    @Mock
    private Client client;

    /**
     * Utility method to mock the Client response with a given HTTP status
     *
     * @param statusToBeMocked the HTTP status to be set
     */
    private void mockClientResponseStatusCode(Status statusToBeMocked)
    {
        when(clientResponse.getClientResponseStatus()).thenReturn(statusToBeMocked);
        when(clientResponse.getStatus()).thenReturn(statusToBeMocked.getStatusCode());
    }

    /**
     * Utility method to mock the Client response with a given HTTP status and body
     *
     * @param statusToBeMocked the HTTP status to be set
     * @param expectedResponse the response body to be set
     */
    private void mockClientResponse(Status statusToBeMocked, String expectedResponse)
    {
        mockClientResponseStatusCode(statusToBeMocked);
        when(clientResponse.getEntity(String.class)).thenReturn(expectedResponse);
    }

    /**
     * Utility method to mock the response from an HTTP request with the given URL and status
     *
     * @param url the URL to be passed to the mock
     * @param status the HTTP status to be set
     * @param mediaType the media type to be set
     * @param expectedResponse the content to be set
     */
    private void mockGetAsStringResponse(String url, Status status, MediaType mediaType, String expectedResponse)
    {
        mockClientResponse(status, expectedResponse);
        mockClient(url);
        when(webResource.get(ClientResponse.class)).thenReturn(clientResponse);
        when(clientResponse.getType()).thenReturn(mediaType);
    }

    /**
     * Utility method to mock the response from an HTTP request with the given URL and status
     *
     * @param method the HTTP method
     * @param url the URL to be passed to the mock
     * @param status the HTTP status to be set
     * @param mediaType the media type to be set
     * @param expectedResponse the content to be set
     */
    private void mockInvokeMethod(String method, String url, Status status, MediaType mediaType, String requestEntity,
            String expectedResponse)
    {
        mockClientResponse(status, expectedResponse);
        mockClient(url);
        when(webResource.method(method, ClientResponse.class, requestEntity)).thenReturn(clientResponse);
        when(clientResponse.getType()).thenReturn(mediaType);
    }

    private void mockClient(String url)
    {
        PowerMockito.mockStatic(Client.class);
        PowerMockito.when(Client.create()).thenReturn(client);

        when(client.resource(url)).thenReturn(webResource);
    }

    /**
     * Tests that no instances of this utility class are created
     */
    @Test
    public void testNoInstancesAllowed() throws Exception
    {
        UtilitiesCommons.testNoInstancesAllowed(WebServiceUtils.class, IllegalStateException.class, "Utility class");
    }

    /**
     * Tests that the status code is retrieved
     */
    @Test
    public void testGetStatusCodeFromClientResponse()
    {
        mockClientResponseStatusCode(Status.NO_CONTENT);
        assertEquals(Status.NO_CONTENT.getStatusCode(), WebServiceUtils.getStatusCode(clientResponse));
    }

    /**
     * Tests that the status code 200 (OK) is classified as successful
     */
    @Test
    public void testIsSuccessfulStatusCodeWithOK()
    {
        mockClientResponseStatusCode(Status.OK);
        assertTrue("Status code 200 should return true", WebServiceUtils.isSuccessful(clientResponse));
    }

    /**
     * Tests that the status code 404 (not found) is classified as not successful
     */
    @Test
    public void testIsSuccessfulStatusWithNotFound()
    {
        mockClientResponseStatusCode(Status.NOT_FOUND);
        assertFalse("Status code 404 should return false", WebServiceUtils.isSuccessful(clientResponse));
    }

    /**
     * Tests that the response body is retrieved from a client response
     */
    @Test
    public void testGetResponseAsStringFromClientResponse()
    {
        mockClientResponse(Status.OK, MOCKED_JSON_AS_STRING);
        assertEquals(MOCKED_JSON_AS_STRING, WebServiceUtils.getResponseAsString(clientResponse));
    }

    /**
     * Tests the getAsString method with a successful HTTP response
     */
    @Test
    public void testGetJsonAsStringWithSuccess()
    {
        mockGetAsStringResponse(URL, Status.OK, MediaType.APPLICATION_JSON_TYPE, MOCKED_JSON_AS_STRING);
        assertEquals(MOCKED_JSON_AS_STRING, WebServiceUtils.getAsString(URL));
    }

    /**
     * Tests the getAsString method with an unsuccessful HTTP response. The content, even if
     * available, will be ignored and an empty string will be returned
     */
    @Test
    public void testGetJsonAsStringWithoutSuccess()
    {
        mockGetAsStringResponse(URL, Status.NOT_FOUND, MediaType.APPLICATION_JSON_TYPE, MOCKED_JSON_AS_STRING);
        assertEquals(StringUtils.EMPTY, WebServiceUtils.getAsString(URL));
    }

    /**
     * Tests the invoke method, with GET and a successful HTTP response
     */
    @Test
    public void testInvokeGetWithSuccess()
    {
        mockInvokeMethod(GET, URL, Status.OK, MediaType.APPLICATION_JSON_TYPE, null, MOCKED_JSON_AS_STRING);
        ClientResponse response = WebServiceUtils.invoke(GET, URL, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MOCKED_JSON_AS_STRING, response.getEntity(String.class));
    }

}