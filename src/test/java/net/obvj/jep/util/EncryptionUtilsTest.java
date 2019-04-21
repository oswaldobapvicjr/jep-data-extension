package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class EncryptionUtilsTest
{
    /**
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     *                   private constructor via Reflection
     */
    @Test(expected = InvocationTargetException.class)
    public void testNoInstancesAllowed() throws Exception
    {
        try
        {
            Constructor<EncryptionUtils> constructor = EncryptionUtils.class.getDeclaredConstructor();
            assertTrue("Constructor is not private", Modifier.isPrivate(constructor.getModifiers()));

            constructor.setAccessible(true);
            constructor.newInstance();
        }
        catch (InvocationTargetException ite)
        {
            Throwable cause = ite.getCause();
            assertEquals(IllegalStateException.class, cause.getClass());
            assertEquals("Utility class", cause.getMessage());
            throw ite;
        }
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
}
