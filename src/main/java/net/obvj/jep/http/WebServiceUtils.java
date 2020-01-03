package net.obvj.jep.http;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import net.obvj.jep.util.EncryptionUtils;
import net.obvj.performetrics.Counter.Type;
import net.obvj.performetrics.Stopwatch;

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
    // - http://headers.jsontest.com/
    // ---------------------------------------------------

    private static Logger log = Logger.getLogger("jep-data-extension");

    private static final String BASIC_AUTHORIZATION_HEADER_PATTERN = "Basic %s";

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
     *         successful; an empty string, otherwise
     */
    public static String getAsString(String url)
    {
        return getAsString(url, null);
    }

    /**
     * Executes HTTP GET on a given URL with custom headers. Returns the HTTP content as a
     * string, if the status code received from the Web Server is one of the class 2xx
     * (success).
     *
     * @param url     the URL of the resource
     * @param headers a map of custom headers to be added to the request
     * @return a string with the content received from the Web server, if the request was
     *         successful; an empty string, otherwise
     */
    public static String getAsString(String url, Map<String, String> headers)
    {
        WebServiceResponse response = get(url, headers);
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
     *
     * @param url the URL of the resource
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     */
    public static WebServiceResponse get(String url)
    {
        return get(url, null);
    }

    /**
     * Executes HTTP GET on a given URL, with custom headers
     *
     * @param url     the URL of the resource
     * @param headers a map of custom headers to be added to the request; can be null
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     */
    public static WebServiceResponse get(String url, Map<String, String> headers)
    {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        Builder requestBuilder = webResource.getRequestBuilder();

        copyHttpHeaders(requestBuilder, headers);

        log.log(Level.INFO, "Invoking GET on URL {0}", url);
        Stopwatch stopwatch = Stopwatch.createStarted(Type.WALL_CLOCK_TIME);

        ClientResponse response = requestBuilder.get(ClientResponse.class);

        stopwatch.stop();
        log.log(Level.INFO, "Operation finished in {0} milliseconds",
                stopwatch.getCounter(Type.WALL_CLOCK_TIME).elapsedTime(TimeUnit.MILLISECONDS));

        log.log(Level.INFO, "HTTP status code: {0} ({1})",
                new Object[] { response.getClientResponseStatus().getStatusCode(),
                        response.getClientResponseStatus().getReasonPhrase() });
        log.log(Level.INFO, "HTTP response content type: {0}", response.getType());

        return WebServiceResponse.fromClientResponse(response);
    }

    /**
     * Executes the requested HTTP method on a given URL.
     *
     * @param method        the HTTP method to be invoked
     * @param url           the URL of the resource
     * @param requestEntity the request body entity/payload to be sent; can be null
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     */
    public static WebServiceResponse invoke(String method, String url, Object requestEntity)
    {
        return invoke(method, url, requestEntity, null);
    }

    /**
     * Executes the requested HTTP method on a given URL, with custom headers.
     *
     * @param method        the HTTP method to be invoked
     * @param url           the URL of the resource
     * @param requestEntity the request body entity/payload to be sent; can be null
     * @param headers       a map of custom headers to be added to the request; can be null
     * @return a {@link WebServiceResponse} object, which may contain the HTTP status code and
     *         the content returned from the Web Service
     * @throws IllegalArgumentException if the HTTP method is null or empty
     */
    public static WebServiceResponse invoke(String method, String url, Object requestEntity,
            Map<String, String> headers)
    {
        if (StringUtils.isEmpty(method))
        {
            throw new IllegalArgumentException("The HTTP method cannot be empty");
        }

        String lMethod = method.toUpperCase();
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        Builder requestBuilder = webResource.getRequestBuilder();

        copyHttpHeaders(requestBuilder, headers);

        log.log(Level.INFO, "Invoking {0} on URL {1}", new Object[] { lMethod, url });
        Stopwatch stopwatch = Stopwatch.createStarted(Type.WALL_CLOCK_TIME);

        ClientResponse response = requestBuilder.method(lMethod, ClientResponse.class, requestEntity);

        stopwatch.stop();
        log.log(Level.INFO, "Operation finished in {0} milliseconds",
                stopwatch.getCounter(Type.WALL_CLOCK_TIME).elapsedTime(TimeUnit.MILLISECONDS));

        log.log(Level.INFO, "HTTP status code: {0} ({1})",
                new Object[] { response.getClientResponseStatus().getStatusCode(),
                        response.getClientResponseStatus().getReasonPhrase() });
        log.log(Level.INFO, "HTTP response content type: {0}", response.getType());

        return WebServiceResponse.fromClientResponse(response);
    }

    /**
     * @param requestBuilder the {@link WebResource} request builder to receive the headers
     * @param headers        the headers to be copied; can be null
     */
    private static void copyHttpHeaders(WebResource.Builder requestBuilder, Map<String, String> headers)
    {
        if (headers != null)
        {
            log.log(Level.INFO, "HTTP headers: {0}", headers);
            headers.forEach(requestBuilder::header);
        }
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

    /**
     * Generates a basic authorization header from the given credentials.
     *
     * @param username the username to be encoded
     * @param password the password to be encoded
     * @return a String containing the generated the basic authorization header value
     */
    public static final String generateBasicAuthorizationHeader(String username, String password)
    {
        if (StringUtils.isEmpty(username))
        {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (StringUtils.isEmpty(password))
        {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (username.contains(":"))
        {
            throw new IllegalArgumentException("Username contains an invalid character (':')");
        }

        String encoding = EncryptionUtils.toBase64(username + ":" + password);
        return String.format(BASIC_AUTHORIZATION_HEADER_PATTERN, encoding);
    }

}
