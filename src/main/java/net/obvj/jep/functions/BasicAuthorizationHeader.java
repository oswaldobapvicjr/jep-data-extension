package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.http.WebServiceUtils;

/**
 * This class implements a JEP function that generates a basic authorization header from
 * given credentials (username and password).
 *
 * @author oswaldo.bapvic.jr
 */
@Function("basicAuthorizationHeader")
public class BasicAuthorizationHeader extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of two parameters
     */
    public BasicAuthorizationHeader()
    {
        numberOfParameters = 2;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        String password = stack.pop().toString();
        String username = stack.pop().toString();
        String generatedString = WebServiceUtils.generateBasicAuthorizationHeader(username, password);
        stack.push(generatedString);
    }

}
