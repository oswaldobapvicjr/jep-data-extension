package net.obvj.jep.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

/**
 * Unit tests for the {@link WebServiceUtils} class
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Client.class)
public class WebServiceResponseTest
{
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
     * Tests the status code and description retrieved from a ClientResponse object
     */
    @Test
    public void testStatusCodeAndDesciptionFromClientResponse()
    {
        mockClientResponseStatusCode(Status.UNAUTHORIZED);
        WebServiceResponse response = WebServiceResponse.fromClientResponse(clientResponse);
        assertEquals(401, response.getStatusCode());
        assertEquals("Unauthorized", response.getStatusDescription());
    }

    /**
     * Tests that the status code 200 (OK) is classified as successful
     */
    @Test
    public void testIsSuccessfulStatusCodeWithOK()
    {
        mockClientResponseStatusCode(Status.OK);
        assertTrue("Status code 200 should return true",
                WebServiceResponse.fromClientResponse(clientResponse).isSuccessful());
    }

    /**
     * Tests that the status code 404 (not found) is classified as not successful
     */
    @Test
    public void testIsSuccessfulStatusWithNotFound()
    {
        mockClientResponseStatusCode(Status.NOT_FOUND);
        assertFalse("Status code 404 should return false",
                WebServiceResponse.fromClientResponse(clientResponse).isSuccessful());
    }

    /**
     * Tests the toString method contains expected data
     */
    @Test
    public void testToStringWithStatusCodeOK()
    {
        mockClientResponseStatusCode(Status.OK);
        String string = WebServiceResponse.fromClientResponse(clientResponse).toString();
        assertTrue("Status code (200) not found", string.contains(String.valueOf(Status.OK.getStatusCode())));
        assertTrue("Reason phrase (OK) but not found", string.contains(Status.OK.getReasonPhrase()));
    }

}
