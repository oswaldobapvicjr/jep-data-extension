package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.http.WebServiceResponse;
import net.obvj.jep.http.WebServiceUtils;

/**
 * A function that invokes a method from a Web Server, given a method, a URL, a request
 * body, and an optional media type, and returns a client response object.
 *
 * @author oswaldo.bapvic.jr
 */
@Function("http")
public class Http extends PostfixMathCommand
{

    /**
     * Builds this custom command with a variable number of parameters
     */
    public Http()
    {
        numberOfParameters = -1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        if (curNumberOfParameters < 3 || curNumberOfParameters > 4)
        {
            throw new ParseException("This funcion accepts three or four arguments");
        }

        String mediaType = curNumberOfParameters == 4 ? stack.pop().toString() : null;
        Object requestBody = stack.pop();
        String url = stack.pop().toString();
        String method = stack.pop().toString();

        WebServiceResponse response = mediaType == null ? WebServiceUtils.invoke(method, url, requestBody)
                : WebServiceUtils.invoke(method, url, requestBody, mediaType);
        stack.push(response);
    }

}
