package net.obvj.jep.http;

import static net.obvj.junit.utils.matchers.InstantiationNotAllowedMatcher.instantiationNotAllowed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link WebServiceUtils} class
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ClientBuilder.class, Builder.class })
public class WebServiceUtilsTest
{
    // Test data
    private static final String GET = "GET";
    private static final String URL = "http://localhost/dummy";
    private static final String MOCKED_JSON_AS_STRING = "{\"foo\":\"bar\"}";

    @Mock
    private Response response;

    @Mock
    private WebTarget webTarget;

    @Mock
    private Builder invocationBuilder;

    @Mock
    private Client client;

    /**
     * Utility method to mock the Client response with a given HTTP status
     *
     * @param status the HTTP status to be set
     */
    private void mockClientResponseStatusCode(Status status)
    {
        when(response.getStatusInfo()).thenReturn(status);
        when(response.getStatus()).thenReturn(status.getStatusCode());
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
        when(response.readEntity(String.class)).thenReturn(expectedResponse);
    }

    /**
     * Utility method to mock the response from an HTTP request with the given URL and status
     *
     * @param url              the URL to be passed to the mock
     * @param status           the HTTP status to be set
     * @param expectedResponse the content to be set
     */
    private void mockGetAsStringResponse(String url, Status status, String expectedResponse)
    {
        mockClientResponse(status, expectedResponse);
        mockClient(url);
        when(webTarget.request()).thenReturn(invocationBuilder);
        when(invocationBuilder.get()).thenReturn(response);
    }

    /**
     * Utility method to mock the response from an HTTP request with the given URL and status
     *
     * @param method           the HTTP method
     * @param url              the URL to be passed to the mock
     * @param status           the HTTP status to be set
     * @param mediaType        the media type to be set
     * @param expectedResponse the content to be set
     */
    private void mockInvokeMethod(String method, String url, Status status, MediaType mediaType, String requestEntity,
            String expectedResponse)
    {
        mockClientResponse(status, expectedResponse);
        mockClient(url);
        when(webTarget.request()).thenReturn(invocationBuilder);
        when(invocationBuilder.method(method, Entity.text(requestEntity))).thenReturn(response);
    }

    private void mockClient(String url)
    {
        PowerMockito.mockStatic(ClientBuilder.class);
        PowerMockito.when(ClientBuilder.newClient()).thenReturn(client);

        when(client.target(url)).thenReturn(webTarget);
    }

    /**
     * Tests that no instances of this utility class are created
     */
    @Test
    public void testNoInstancesAllowed()
    {
        assertThat(WebServiceUtils.class, instantiationNotAllowed());
    }

    /**
     * Tests that the status code is retrieved
     */
    @Test
    public void testGetStatusCodeFromClientResponse()
    {
        mockClientResponseStatusCode(Status.NO_CONTENT);
        assertEquals(Status.NO_CONTENT.getStatusCode(),
                WebServiceUtils.getStatusCode(WebServiceResponse.fromClientResponse(response)));
    }

    /**
     * Tests that the response body is retrieved from a client response
     */
    @Test
    public void testGetResponseAsStringFromClientResponse()
    {
        mockClientResponse(Status.OK, MOCKED_JSON_AS_STRING);
        assertEquals(MOCKED_JSON_AS_STRING,
                WebServiceUtils.getResponseAsString(WebServiceResponse.fromClientResponse(response)));
    }

    /**
     * Tests the getAsString method with a successful HTTP response
     */
    @Test
    public void testGetJsonAsStringWithSuccess()
    {
        mockGetAsStringResponse(URL, Status.OK, MOCKED_JSON_AS_STRING);
        assertEquals(MOCKED_JSON_AS_STRING, WebServiceUtils.getAsString(URL));
    }

    /**
     * Tests the getAsString method with an unsuccessful HTTP response. The content, even if
     * available, will be ignored and an empty string will be returned
     */
    @Test
    public void testGetJsonAsStringWithoutSuccess()
    {
        mockGetAsStringResponse(URL, Status.NOT_FOUND, MOCKED_JSON_AS_STRING);
        assertEquals(StringUtils.EMPTY, WebServiceUtils.getAsString(URL));
    }

    /**
     * Tests the get method
     */
    @Test
    public void testGetWithSuccess()
    {
        mockGetAsStringResponse(URL, Status.OK, MOCKED_JSON_AS_STRING);
        WebServiceResponse response = WebServiceUtils.get(URL);
        assertEquals(Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals(MOCKED_JSON_AS_STRING, response.getBody());
        verify(invocationBuilder, never()).header("Authorization", "Basic dXNlcjpwYXNz");
        verify(invocationBuilder, never()).header("Content-Type", "application/json");
    }

    /**
     * Tests the invoke method, without a method
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvokeNullMethod()
    {
        WebServiceUtils.invoke(null, URL, null);
    }

    /**
     * Tests the invoke method, with GET and a successful HTTP response
     */
    @Test
    public void testInvokeGetWithSuccess()
    {
        mockInvokeMethod(GET, URL, Status.OK, MediaType.APPLICATION_JSON_TYPE, null, MOCKED_JSON_AS_STRING);
        WebServiceResponse response = WebServiceUtils.invoke(GET, URL, null);
        assertEquals(Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals(MOCKED_JSON_AS_STRING, response.getBody());
    }

    /**
     * Tests the invoke method, with custom headers
     */
    @Test
    public void testInvokeGetWithCustomHeadersSuccess()
    {
        mockInvokeMethod(GET, URL, Status.OK, MediaType.APPLICATION_JSON_TYPE, null, MOCKED_JSON_AS_STRING);
        Map<String, String> headers = CollectionsUtils.asMap("Authorization=Basic dXNlcjpwYXNz",
                "Content-Type=application/json");
        WebServiceUtils.invoke(GET, URL, null, headers);
        verify(invocationBuilder).header("Authorization", "Basic dXNlcjpwYXNz");
        verify(invocationBuilder).header("Content-Type", "application/json");
    }

    @Test
    public void testGenerateBasicAuthorizationHeaderWithValidCredentials()
    {
        assertEquals("Basic QWxhZGRpbjpPcGVuU2VzYW1l",
                WebServiceUtils.generateBasicAuthorizationHeader("Aladdin", "OpenSesame"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateBasicAuthorizationHeaderWithBadUsername()
    {
        WebServiceUtils.generateBasicAuthorizationHeader("Ala:ddin", "OpenSesame");
    }

}
