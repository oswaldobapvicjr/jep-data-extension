package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.http.WebServiceUtils;

/**
 * A function that reads content from a Web Server, given a URL.
 *
 * @author oswaldo.bapvic.jr
 */
public class HttpGet extends PostfixMathCommand
{

    /**
     * Builds this custom command with a fixed number of 1 parameter
     */
    public HttpGet()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        String url = stack.pop().toString();
        stack.push(WebServiceUtils.getAsString(url));
    }

}
