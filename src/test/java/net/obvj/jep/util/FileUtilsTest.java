package net.obvj.jep.util;

import static net.obvj.junit.utils.matchers.InstantiationNotAllowedMatcher.instantiationNotAllowed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Unit tests for the {@link FileUtils} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class FileUtilsTest
{
    /**
     * Tests that no instances of this utility class are created
     */
    @Test
    public void testNoInstancesAllowed()
    {
        assertThat(FileUtils.class, instantiationNotAllowed());
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
