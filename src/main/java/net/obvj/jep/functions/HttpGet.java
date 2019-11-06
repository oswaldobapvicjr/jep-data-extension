package net.obvj.jep.functions;

import java.util.Map;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.http.WebServiceUtils;

/**
 * A function that reads content from a Web Server, given a URL and an optional media
 * type, and returns server response as string
 *
 * @author oswaldo.bapvic.jr
 */
@Function("httpGet")
public class HttpGet extends PostfixMathCommand
{

    /**
     * Builds this custom command with a variable number of parameters
     */
    public HttpGet()
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
        if (curNumberOfParameters > 2)
        {
            throw new ParseException("Invalid number of arguments. Usages: \n"
                    + " httpGet(url)\n"
                    + " httpGet(url, headers)");
        }

        Map<String, String> headers = stack.size() == 2 ? (Map) stack.pop() : null;
        String url = stack.pop().toString();

        String response = headers == null ? WebServiceUtils.getAsString(url)
                : WebServiceUtils.getAsString(url, headers);
        stack.push(response);
    }

}
