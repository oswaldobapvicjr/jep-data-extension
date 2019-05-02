package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncryptionUtilsTest
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
        UtilitiesCommons.testNoInstancesAllowed(EncryptionUtils.class, IllegalStateException.class, "Utility class");
    }

    /**
     * Tests the successful encryption of a string message with MD5
     */
    @Test
    public void testMd5()
    {
        String message = "asd";
        String md5 = EncryptionUtils.md5(message);
        assertEquals("7815696ecbf1c96e6894b779456d330e", md5);
    }

    /**
     * Tests the successful encryption of a string message with SHA1
     */
    @Test
    public void testSha1()
    {
        String message = "dsasd";
        String sha1 = EncryptionUtils.sha1(message);
        assertEquals("2fa183839c954e6366c206367c9be5864e4f4a65", sha1);
    }

    /**
     * Tests the successful encryption of a string message with SHA-256
     */
    @Test
    public void testSha256()
    {
        String message = "hello";
        String sha256 = EncryptionUtils.sha256(message);
        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", sha256);
    }
}
