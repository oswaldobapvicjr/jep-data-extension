package net.obvj.jep.functions;

import java.util.Map;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.http.WebServiceResponse;
import net.obvj.jep.http.WebServiceUtils;

/**
 * A function that invokes a method from a Web Server, given a method, a URL, a request
 * body, and an optional headers, and returns a client response object.
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
        if (curNumberOfParameters < 2 || curNumberOfParameters > 4)
        {
            throw new ParseException("Invalid number of arguments. Usages: \n"
                    + " http(method, url)\n"
                    + " http(method, url, requestBody)\n"
                    + " http(method, url, requestBody, headers)");
        }

        Map<String, String> headers = stack.size() == 4 ? (Map) stack.pop() : null;
        Object requestBody = stack.size() == 3 ? stack.pop() : null;
        String url = stack.pop().toString();
        String method = stack.pop().toString();

        WebServiceResponse response = WebServiceUtils.invoke(method, url, requestBody, headers);
        stack.push(response);
    }

}
