package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class FileUtilsTest
{
    /**
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test
    public void testNoInstancesAllowed() throws Exception
    {
        UtilitiesCommons.testNoInstancesAllowed(FileUtils.class, IllegalStateException.class, "Utility class");
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
