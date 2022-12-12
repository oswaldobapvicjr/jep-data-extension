package net.obvj.jep.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests for the {@link WebServiceResponse} class.
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(MockitoJUnitRunner.class)
public class WebServiceResponseTest
{
    @Mock
    private Response clientResponse;

    /**
     * Utility method to mock the response with a given HTTP status
     *
     * @param status the HTTP status to be set
     */
    private void mockClientResponseStatusCode(Status status)
    {
        when(clientResponse.getStatusInfo()).thenReturn(status);
        when(clientResponse.getStatus()).thenReturn(status.getStatusCode());
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
