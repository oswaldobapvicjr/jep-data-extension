package net.obvj.jep.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import net.obvj.performetrics.Counter.Type;
import net.obvj.performetrics.Stopwatch;

/**
 * A utility class for loading text files.
 *
 * @author oswaldo.bapvic.jr
 */
public class FileUtils
{
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private static final Logger LOG = Logger.getLogger("jep-data-extension");

    private FileUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Loads the content of a file in the class path into a String.
     *
     * @param fileName the name of the file to be loaded
     * @return a String with the content of the specified file name
     * @throws IllegalArgumentException it the file was not found in the class path
     * @throws IOException if an I/O error occurs reading the file from the stream
     */
    public static String readFromClasspath(String fileName) throws IOException
    {
        URL url = FileUtils.class.getClassLoader().getResource(fileName);
        if (url == null)
        {
            throw new IllegalArgumentException("File not found in class path: " + fileName);
        }
        try
        {
            Path path = Paths.get(url.toURI());
            return readFromPath(path);
        }
        catch (URISyntaxException uriSyntaxException)
        {
            throw new IllegalArgumentException(uriSyntaxException);
        }
    }

    /**
     * Loads the content of a file in the class path into a string. Returns an empty string if
     * any exception occurs. Useful for JUnit testing.
     *
     * @param fileName the name of the file to be loaded
     * @return a String with the content of the specified file name, or an empty string in
     * case of exceptions
     */
    public static String readQuietlyFromClasspath(String fileName)
    {
        try
        {
            return readFromClasspath(fileName);
        }
        catch (Exception exception)
        {
            return StringUtils.EMPTY;
        }
    }

    /**
     * Loads the content of the file identified by the given path in the file system.
     *
     * @param path the path to the file
     * @return a String with the content read from the file in UTF-8
     * @throws IOException if an I/O error occurs reading the file from the stream
     */
    public static String readFromFileSystem(String path) throws IOException
    {
        return readFromPath(Paths.get(path));
    }

    /**
     * Loads the content of the file identified by the given path in the file system.
     *
     * @param path the path to the file
     * @return a String with the content read from the file in UTF-8
     * @throws IOException if an I/O error occurs reading the file from the stream
     */
    protected static String readFromPath(Path path) throws IOException
    {
        LOG.log(Level.INFO, "Reading file: {0}", path);
        Stopwatch stopwatch = Stopwatch.createStarted(Type.WALL_CLOCK_TIME);
        try
        {
            return new String(Files.readAllBytes(path), DEFAULT_CHARSET);
        }
        finally
        {
            LOG.log(Level.INFO, "Operation finished in {0} milliseconds",
                    stopwatch.elapsedTime(TimeUnit.MILLISECONDS));
        }
    }
}
