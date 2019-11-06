package net.obvj.jep.http;

import javax.ws.rs.core.Response.Status.Family;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;

/**
 * An object that contains data from Web a Service response
 *
 * @author oswaldo.bapvic.jr
 */
public class WebServiceResponse
{
    private final int statusCode;
    private final String statusDescription;
    private final String body;

    /**
     * @param statusCode        the response status code to set
     * @param statusDescription the response status description to set
     * @param body              the response body to set
     */
    public WebServiceResponse(int statusCode, String statusDescription, String body)
    {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.body = body;
    }

    /**
     * Creates a new {@link WebServiceResponse} from a {@link ClientResponse}.
     * 
     * @param response the client response to retrieve data
     * @return a {@link WebServiceResponse}.
     */
    public static WebServiceResponse fromClientResponse(ClientResponse response)
    {
        return new WebServiceResponse(response.getClientResponseStatus().getStatusCode(),
                response.getClientResponseStatus().getReasonPhrase(), response.getEntity(String.class));
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getStatusDescription()
    {
        return statusDescription;
    }

    public String getBody()
    {
        return body;
    }

    /**
     * Returns true if the status code from an HTTP client response is one of the family 2xx
     * (success)
     *
     * @return {@code true} if the status code belongs to the "successful" family 2xx;
     *         otherwise, {@code false}
     */
    public boolean isSuccessful()
    {
        return Family.SUCCESSFUL == Status.fromStatusCode(statusCode).getFamily();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("WebServiceResponse: ").append(statusCode).append(" (").append(statusDescription).append(")");
        return sb.toString();
    }

}
