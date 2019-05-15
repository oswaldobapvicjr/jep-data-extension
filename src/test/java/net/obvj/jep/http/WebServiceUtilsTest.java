package net.obvj.jep.http;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

import net.obvj.jep.util.UtilitiesCommons;

/**
 * Unit tests for the {@link WebServiceUtils} class
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
public class WebServiceUtilsTest
{
    @Mock
    private ClientResponse clientResponse;

    /**
     * Utility method to mock the Client response with a given HTTP status
     *
     * @param statusToBeMocked the HTTP status to be set
     */
    private void mockClientResponseStatusCode(Status statusToBeMocked)
    {
        when(clientResponse.getClientResponseStatus()).thenReturn(statusToBeMocked);
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

}
