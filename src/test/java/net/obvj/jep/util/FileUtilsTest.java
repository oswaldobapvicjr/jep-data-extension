package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import net.obvj.junit.utils.TestUtils;

/**
 * Unit tests for the {@link FileUtils} class.
 * 
 * @author oswaldo.bapvic.jr
 */
public class FileUtilsTest
{
    /**
     * Tests that no instances of this utility class are created
     *
     * @throws ReflectiveOperationException in case of errors getting constructor metadata or
     *                                      instantiating the private constructor
     */
    @Test
    public void testNoInstancesAllowed() throws ReflectiveOperationException
    {
        TestUtils.assertNoInstancesAllowed(FileUtils.class, IllegalStateException.class, "Utility class");
    }

    /**
     * Tests that an exception is not thrown, but an empty string is returned by the
     * readQuietlyFromClasspath method for and unknown file name
     */
    @Test
    public void testReadQuietlyFromClasspathNullFileName()
    {
        assertEquals(StringUtils.EMPTY, FileUtils.readQuietlyFromClasspath("unknownFile.xml/"));
    }
}
