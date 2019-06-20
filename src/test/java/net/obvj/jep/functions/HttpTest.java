package net.obvj.jep.functions;

import static net.obvj.jep.util.CollectionsUtils.newParametersStack;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.nfunk.jep.ParseException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import net.obvj.jep.http.WebServiceUtils;

/**
 * Unit tests for the {@link Http} function
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Client.class, WebServiceUtils.class })
public class HttpTest
{
    private static final String EMPLOYEE_URL = "http://dummy.restapiexample.com/api/v1/employee";
    private static final String EMPLOYEE_REQUEST_BODY = "{\"employee_name\":\"John\",\"employee_age\":23}";
    private static final String EMPLOYEE_RESPONSE_BODY = "{\"id\":9}";;
    private static final String POST = "POST";
    private static final String APPLICATION_JSON = "application/json";

    // Test subject
    private static Http function = new Http();

    @Mock
    private ClientResponse clientResponse;
    @Mock
    private WebResource webResource;
    @Mock
    private Client client;

    /**
     * Runs the function with the given parameters
     */
    private void run(Stack<Object> stack) throws ParseException
    {
        function.setCurNumberOfParameters(stack.size());
        function.run(stack);
    }

    /**
     * Tests the execution of the POST method with support of mocks. Successful scenario with
     * three parameters: method, URL and request body.
     */
    @Test
    public void testPostWithRequestBody() throws org.nfunk.jep.ParseException, IOException
    {
        // Mock Jersey objects
        PowerMockito.mockStatic(Client.class);
        PowerMockito.when(Client.create()).thenReturn(client);
        Mockito.when(client.resource(EMPLOYEE_URL)).thenReturn(webResource);

        // Mock HTTP method invocation
        when(webResource.method(POST, ClientResponse.class, EMPLOYEE_REQUEST_BODY)).thenReturn(clientResponse);

        // Mock ClientResponse
        when(clientResponse.getClientResponseStatus()).thenReturn(Status.CREATED);
        when(clientResponse.getEntity(String.class)).thenReturn(EMPLOYEE_RESPONSE_BODY);

        // Test
        Stack<Object> parameters = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY);
        run(parameters);

        ClientResponse response = (ClientResponse) parameters.pop();
        assertEquals(Status.CREATED, response.getClientResponseStatus());
        assertEquals(EMPLOYEE_RESPONSE_BODY, response.getEntity(String.class));
    }

    /**
     * Checks that the function does not accept less than 3 parameters
     */
    @Test(expected = ParseException.class)
    public void testWithTwoParameters() throws org.nfunk.jep.ParseException, IOException
    {
        Stack<Object> parameters = newParametersStack(POST, EMPLOYEE_URL);
        run(parameters);
    }

    /**
     * Tests the correct method was called when three parameters are passed
     */
    @Test
    public void testWithThreeParameters() throws org.nfunk.jep.ParseException, IOException
    {
        // Mock WebServiceUtils
        PowerMockito.mockStatic(WebServiceUtils.class);
        PowerMockito.when(WebServiceUtils.invoke(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY)).thenReturn(clientResponse);

        // Test
        Stack<Object> parameters = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY);
        run(parameters);

        // Verify the correct method was called
        PowerMockito.verifyStatic(WebServiceUtils.class, times(1));
        WebServiceUtils.invoke(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY);
    }

    /**
     * Tests the correct method was called when four parameters are passed
     */
    @Test
    public void testWithFourParameters() throws org.nfunk.jep.ParseException, IOException
    {
        // Mock WebServiceUtils
        PowerMockito.mockStatic(WebServiceUtils.class);
        PowerMockito.when(WebServiceUtils.invoke(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, APPLICATION_JSON))
                .thenReturn(clientResponse);

        // Test
        Stack<Object> parameters = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, APPLICATION_JSON);
        run(parameters);

        // Verify the correct method was called
        PowerMockito.verifyStatic(WebServiceUtils.class, times(1));
        WebServiceUtils.invoke(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, APPLICATION_JSON);
    }

    /**
     * Checks that the function does not accept more than 4 parameters
     */
    @Test(expected = ParseException.class)
    public void testWithFiveParameters() throws org.nfunk.jep.ParseException, IOException
    {
        Stack<Object> stack = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, APPLICATION_JSON, "test");
        run(stack);
    }

}
