package net.obvj.jep.functions;

import java.util.Stack;
import java.util.function.Function;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import com.sun.jersey.api.client.ClientResponse;

import net.obvj.jep.http.WebServiceUtils;

/**
 * A JEP function that accepts a ClientResponse object.
 *
 * @author oswaldo.bapvic.jr
 */
public class HttpResponseHandler extends PostfixMathCommand
{

    public enum HttpResponseHandlerStrategy
    {
        /**
         * A function that returns the HTTP status code, given a {@link ClientResponse} object
         * returned by the {@code http()} function
         */
        GET_STATUS_CODE(WebServiceUtils::getStatusCode),

        /**
         * A function that returns the HTTP response body as string, given a
         * {@link ClientResponse} object returned by the {@code http()} function
         */
        GET_RESPONSE(WebServiceUtils::getResponseAsString);

        private Function<ClientResponse, Object> function;

        HttpResponseHandlerStrategy(Function<ClientResponse, Object> returnStrategy)
        {
            this.function = returnStrategy;
        }

        public Object execute(ClientResponse clientResponse)
        {
            return function.apply(clientResponse);
        }
    }

    private final HttpResponseHandlerStrategy strategy;

    /**
     * Builds this function with a fixed number of one parameter
     */
    public HttpResponseHandler(HttpResponseHandlerStrategy strategy)
    {
        numberOfParameters = 1;
        this.strategy = strategy;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object parameter = stack.pop();

        if (parameter instanceof ClientResponse)
        {
            ClientResponse clientResponse = (ClientResponse) parameter;
            stack.push(strategy.execute(clientResponse));
        }
        else
        {
            throw new ParseException("Not a ClientResponse object");
        }
    }

    public HttpResponseHandlerStrategy getStrategy()
    {
        return strategy;
    }

}
