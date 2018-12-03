package com.ericsson.cerm.junit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

/**
 * An utility class for loading of text files in JUnit tests.
 *
 * @author Oswaldo Junior
 */
public class TextFileReader
{

    /**
     * Load the content of a file in the class path into a String.
     *
     * @param fileName the name of the file to be loaded.
     * @return a String with the content of the specified file name
     */
    public static String readFile(String fileName)
    {
        String content = StringUtils.EMPTY;
        try
        {
            URL fileUrl = TextFileReader.class.getClassLoader().getResource(fileName);
            if (fileUrl != null)
            {
                Path filePath = Paths.get(fileUrl.toURI());
                content = new String(Files.readAllBytes(filePath));
            }
            else
            {
                throw new IllegalArgumentException("File not found in class path: " + fileName);
            }
        }
        catch (URISyntaxException | IOException e)
        {
            throw new IllegalArgumentException(String
                    .format("Error loading test file '%s' from class path: Message: %s", fileName, e.getMessage()), e);
        }
        return content;
    }
}
