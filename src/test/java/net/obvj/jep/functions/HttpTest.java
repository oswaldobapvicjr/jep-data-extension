package net.obvj.jep.functions;

import static net.obvj.jep.util.CollectionsUtils.newParametersStack;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.nfunk.jep.ParseException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.obvj.jep.http.WebServiceResponse;
import net.obvj.jep.http.WebServiceUtils;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Http} function
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(WebServiceUtils.class)
public class HttpTest
{
    private static final String EMPLOYEE_URL = "http://dummy.restapiexample.com/api/v1/employee";
    private static final String EMPLOYEE_REQUEST_BODY = "{\"employee_name\":\"John\",\"employee_age\":23}";
    private static final String POST = "POST";
    private static final String ACCEPT_APPLICATION_JSON = "Accept=application/json";
    private static final Map<String, String> HEADERS = CollectionsUtils.asMap(ACCEPT_APPLICATION_JSON);

    // Test subject
    private static Http function = new Http();

    @Mock
    private WebServiceResponse response;

    @Before
    public void setup()
    {
        PowerMockito.mockStatic(WebServiceUtils.class);
    }

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
        PowerMockito.when(WebServiceUtils.invoke(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, null)).thenReturn(response);

        Stack<Object> parameters = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY);
        run(parameters);

        assertEquals(response, parameters.pop());
    }

    /**
     * Checks that the function does not accept less than 2 parameters
     */
    @Test(expected = ParseException.class)
    public void testWithOneParameter() throws org.nfunk.jep.ParseException, IOException
    {
        Stack<Object> parameters = newParametersStack(POST);
        run(parameters);
    }

    /**
     * Tests the correct method was called when three parameters are passed
     */
    @Test
    public void testWithThreeParameters() throws org.nfunk.jep.ParseException, IOException
    {
        // Test
        Stack<Object> parameters = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY);
        run(parameters);

        // Verify the correct method was called
        PowerMockito.verifyStatic(WebServiceUtils.class, times(1));
        WebServiceUtils.invoke(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, null);
    }

    /**
     * Tests the correct method was called when four parameters are passed
     */
    @Test
    public void testWithFourParameters() throws org.nfunk.jep.ParseException, IOException
    {
        // Test
        Stack<Object> parameters = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, HEADERS);
        run(parameters);

        // Verify the correct method was called
        PowerMockito.verifyStatic(WebServiceUtils.class, times(1));
        WebServiceUtils.invoke(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, HEADERS);
    }

    /**
     * Checks that the function does not accept more than 4 parameters
     */
    @Test(expected = ParseException.class)
    public void testWithFiveParameters() throws org.nfunk.jep.ParseException, IOException
    {
        Stack<Object> stack = newParametersStack(POST, EMPLOYEE_URL, EMPLOYEE_REQUEST_BODY, null, "test");
        run(stack);
    }

}
