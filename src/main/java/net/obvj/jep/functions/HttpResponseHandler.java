package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.http.WebServiceResponse;
import net.obvj.jep.http.WebServiceUtils;

/**
 * A JEP function that reads data from a {@link WebServiceResponse} object.
 *
 * @author oswaldo.bapvic.jr
 */
public class HttpResponseHandler extends PostfixMathCommand implements MultiStrategyCommand
{

    public enum Strategy
    {
        /**
         * A function that returns the HTTP status code, given a {@link WebServiceResponse} object
         * returned by the {@code http()} function
         */
        @Function("httpStatusCode")
        GET_STATUS_CODE(WebServiceUtils::getStatusCode),

        /**
         * A function that returns the HTTP response body as string, given a
         * {@link WebServiceResponse} object returned by the {@code http()} function
         */
        @Function("httpResponse")
        GET_RESPONSE(WebServiceUtils::getResponseAsString);

        private java.util.function.Function<WebServiceResponse, Object> function;

        Strategy(java.util.function.Function<WebServiceResponse, Object> returnStrategy)
        {
            this.function = returnStrategy;
        }

        public Object execute(WebServiceResponse webServiceResponse)
        {
            return function.apply(webServiceResponse);
        }
    }

    private final Strategy strategy;

    /**
     * Builds this function with a fixed number of one parameter.
     *
     * @param strategy the {@link Strategy} to be set
     */
    public HttpResponseHandler(Strategy strategy)
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

        if (parameter instanceof WebServiceResponse)
        {
            WebServiceResponse webServiceResponse = (WebServiceResponse) parameter;
            stack.push(strategy.execute(webServiceResponse));
        }
        else
        {
            throw new ParseException("Not a WebServiceResponse object");
        }
    }

    /**
     * @see net.obvj.jep.functions.MultiStrategyCommand#getStrategy()
     */
    @Override
    public Object getStrategy()
    {
        return strategy;
    }

}
