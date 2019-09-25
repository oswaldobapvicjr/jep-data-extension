package net.obvj.jep.http;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * This class contains methods for working with simple Web Services.
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

    private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON;

    private static Logger log = Logger.getLogger("jep-data-extension");

    private WebServiceUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Executes HTTP GET on given URL. Returns the HTTP content as a string, if the status
     * code received from the Web Server is one of the class 2xx (success).
     * <p>
     * The preferable media type "application/json" will be accepted by default, if supported
     * by the Web server.
     *
     * @param url the URL of the resource
     * @return a string with the content received from the Web server, if the request was
     *         successful; an empty string, otherwise
     */
    public static String getAsString(String url)
    {
        return getAsString(url, DEFAULT_MEDIA_TYPE);
    }

    /**
     * Executes HTTP GET on a given URL with a preferable media type to be accepted. Returns
     * the HTTP content as a string, if the status code received from the Web Server is one of
     * the class 2xx (success).
     *
     * @param url       the URL of the resource
     * @param mediaType a preferable media type to be accepted
     * @return a string with the content received from the Web server, if the request was
     *         successful; an empty string, otherwise
     */
    public static String getAsString(String url, String mediaType)
    {
        WebServiceResponse response = get(url, mediaType);
        return response.isSuccessful() ? response.getBody() : StringUtils.EMPTY;
    }

    /**
     * Returns the HTTP response body, as string, given an HTTP client response.
     *
     * @param webServiceResponse the HTTP client response
     * @return the response body, as string
     */
    public static String getResponseAsString(WebServiceResponse webServiceResponse)
    {
        return webServiceResponse.getBody();
    }

    /**
     * Executes HTTP GET on a given URL.
     * <p>
     * The preferable media type "application/json" will be accepted by default, if supported
     * by the Web server.
     *
     * @param url the URL of the resource
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     */
    public static WebServiceResponse get(String url)
    {
        return get(url, DEFAULT_MEDIA_TYPE);
    }

    /**
     * Executes HTTP GET on a given URL, with a preferable media type to be acceptable.
     *
     * @param url       the URL of the resource
     * @param mediaType a preferable media type to be accepted
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     */
    public static WebServiceResponse get(String url, String mediaType)
    {
        Client client = Client.create();
        WebResource webResource = client.resource(url);

        webResource.accept(mediaType);
        log.log(Level.INFO, "Acceptable media type requested: {0}", mediaType);

        log.log(Level.INFO, "Invoking GET on URL {0}", url);
        ClientResponse response = webResource.get(ClientResponse.class);

        log.log(Level.INFO, "HTTP status code: {0} ({1})",
                new Object[] { response.getClientResponseStatus().getStatusCode(),
                        response.getClientResponseStatus().getReasonPhrase() });
        log.log(Level.INFO, "HTTP response content type: {0}", response.getType());

        return WebServiceResponse.fromClientResponse(response);
    }

    /**
     * Executes the requested HTTP method on a given URL.
     * <p>
     * The preferable media type "application/json" will be accepted by default, if supported
     * by the Web server.
     *
     * @param method        the HTTP method to be invoked
     * @param url           the URL of the resource
     * @param requestEntity the request body entity/payload to be sent
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     */
    public static WebServiceResponse invoke(String method, String url, Object requestEntity)
    {
        return invoke(method, url, requestEntity, DEFAULT_MEDIA_TYPE);
    }

    /**
     * Executes the requested HTTP method on a given URL, with a preferable media type to be
     * acceptable.
     *
     * @param method        the HTTP method to be invoked
     * @param url           the URL of the resource
     * @param requestEntity the request body entity/payload to be sent
     * @param mediaType     a preferable media type to be accepted
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     */
    public static WebServiceResponse invoke(String method, String url, Object requestEntity, String mediaType)
    {
        Client client = Client.create();
        WebResource webResource = client.resource(url);

        webResource.accept(mediaType);
        log.log(Level.INFO, "Acceptable media type requested: {0}", mediaType);

        log.log(Level.INFO, "Invoking {0} on URL {1}", new Object[] { method, url });
        ClientResponse response = webResource.method(method, ClientResponse.class, requestEntity);

        log.log(Level.INFO, "HTTP status code: {0} ({1})",
                new Object[] { response.getClientResponseStatus().getStatusCode(),
                        response.getClientResponseStatus().getReasonPhrase() });
        log.log(Level.INFO, "HTTP response content type: {0}", response.getType());

        return WebServiceResponse.fromClientResponse(response);
    }

    /**
     * Returns the HTTP status code, given an HTTP response.
     *
     * @param webServiceResponse the HTTP client response
     * @return the status code
     */
    public static int getStatusCode(WebServiceResponse webServiceResponse)
    {
        return webServiceResponse.getStatusCode();
    }

}
