package net.obvj.jep.functions;

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
        if (stack.size() > 2)
        {
            throw new ParseException("This funcion accepts only one or two arguments");
        }

        String mediaType = stack.size() == 2 ? stack.pop().toString() : null;
        String url = stack.pop().toString();

        String response = mediaType == null ? WebServiceUtils.getAsString(url)
                : WebServiceUtils.getAsString(url, mediaType);
        stack.push(response);
    }

}
