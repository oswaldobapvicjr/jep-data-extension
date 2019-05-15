package net.obvj.jep.http;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status.Family;

import org.apache.commons.lang3.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This class contains methods for consuming simple Web Services.
 *
 * @author oswaldo.bapvic.jr
 */
public class WebServiceUtils
{
    // --------------------------------------------------
    // HINT: Some dummy RESTful APIs online:
    // --------------------------------------------------
    // - http://dummy.restapiexample.com/api/v1/employees
    // - http://date.jsontest.com/
    // ---------------------------------------------------

    private static Logger log = Logger.getLogger("jep-data-extension");

    private WebServiceUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Executes HTTP GET on given URL. Returns the HTTP content as a string, if the status
     * code received from the Web Server is one of the class 2xx (success).
     *
     * @param url the URL of the resource
     * @return a string with the content received from the Web server, if the request was
     * successful; an empty string, otherwise
     */
    public static String getAsString(String url)
    {
        return getAsString(url, null);
    }

    /**
     * Executes HTTP GET on given URL with a preferable media type to be accepted. Returns the
     * HTTP content as a string, if the status code received from the Web Server is one of the
     * class 2xx (success).
     *
     * @param url the URL of the resource
     * @return a string with the content received from the Web server, if the request was
     * successful; an empty string, otherwise
     */
    public static String getAsString(String url, String mediaType)
    {
        ClientResponse response = get(url, mediaType);
        return isSuccessful(response) ? response.getEntity(String.class) : StringUtils.EMPTY;
    }

    /**
     * Executes HTTP GET on given URL
     *
     * @param url the URL of the resource
     * @return a {@link ClientResponse} object, which may contain the HTTP status code and the
     * content returned from the Web Service
     */
    public static ClientResponse get(String url)
    {
        return get(url, null);
    }

    /**
     * Executes HTTP GET on given URL, with a preferable media type to be acceptable.
     *
     * @param url the URL of the resource
     * @return a {@link ClientResponse} object, which may contain the HTTP status code and the
     * content returned from the Web Service
     */
    public static ClientResponse get(String url, String mediaType)
    {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        webResource.accept(mediaType);

        log.log(Level.INFO, "Invoking HTTP GET on URL: {0}", url);

        ClientResponse response = webResource.get(ClientResponse.class);

        log.log(Level.INFO, "HTTP status code: {0} ({1})",
                new Object[] { response.getClientResponseStatus().getStatusCode(),
                        response.getClientResponseStatus().getReasonPhrase() });
        log.log(Level.INFO, "HTTP response content type: {0}", response.getType());

        return response;
    }

    /**
     * Returns true if the status code from the HTTP client is one of the family 2xx (success)
     *
     * @param clientResponse the HTTP client response whose status code is to be evaluated
     * @return {@code true} if the status code belongs to the "successful" family 2xx;
     * otherwise, {@code false}
     */
    public static boolean isSuccessful(ClientResponse clientResponse)
    {
        return Family.SUCCESSFUL == clientResponse.getClientResponseStatus().getFamily();
    }

}
