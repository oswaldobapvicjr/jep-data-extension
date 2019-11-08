package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link BasicAuthorizationHeader} function
 *
 * @author oswaldo.bapvic.jr
 */
public class BasicAuthorizationHeaderTest
{
    private static final String USERNAME = "Aladdin";
    private static final String PASSWORD = "OpenSesame";
    private static final String EXPECTED_AUTHORIZATION_HEADER = "Basic QWxhZGRpbjpPcGVuU2VzYW1l";

    private static BasicAuthorizationHeader function = new BasicAuthorizationHeader();

    @Test
    public void testWithValidUsernameAndPassword() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(USERNAME, PASSWORD);
        function.run(parameters);
        assertEquals(EXPECTED_AUTHORIZATION_HEADER, parameters.pop());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidUsername() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("Ala:ddin", PASSWORD);
        function.run(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithEmptyUsername() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("", PASSWORD);
        function.run(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithEmptyPassorws() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(USERNAME, "");
        function.run(parameters);
    }

}
