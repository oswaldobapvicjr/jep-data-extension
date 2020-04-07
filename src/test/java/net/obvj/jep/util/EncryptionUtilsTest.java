package net.obvj.jep.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.obvj.junit.utils.TestUtils;

/**
 * Unit tests for the {@link EncryptionUtils} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class EncryptionUtilsTest
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
        TestUtils.assertNoInstancesAllowed(EncryptionUtils.class, IllegalStateException.class, "Utility class");
    }

    /**
     * Tests the successful encryption of a string message with MD5
     */
    @Test
    public void testMd5()
    {
        assertThat(EncryptionUtils.md5("asd"), is("7815696ecbf1c96e6894b779456d330e"));
    }

    /**
     * Tests the successful encryption of a string message with SHA1
     */
    @Test
    public void testSha1()
    {
        assertThat(EncryptionUtils.sha1("dsasd"), is("2fa183839c954e6366c206367c9be5864e4f4a65"));
    }

    /**
     * Tests the successful encryption of a string message with SHA-256
     */
    @Test
    public void testSha256()
    {
        assertThat(EncryptionUtils.sha256("hello"),
                is("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"));
    }

    /**
     * Tests hashWith with an unknown algorithm
     */
    @Test(expected = IllegalArgumentException.class)
    public void testHashWithUnknown()
    {
        EncryptionUtils.hashWith("unknown", "message");
    }

    /**
     * Tests toBase64 with a valid string
     */
    @Test
    public void testToBase64()
    {
        assertThat(EncryptionUtils.toBase64("myMessage"), is("bXlNZXNzYWdl"));
    }

    /**
     * Tests fromBase64 with a valid Base64 encoded string
     */
    @Test
    public void testFromBase64()
    {
        assertThat(EncryptionUtils.fromBase64("bXlNZXNzYWdl"), is("myMessage"));
    }
}
