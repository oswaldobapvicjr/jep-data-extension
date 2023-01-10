package net.obvj.jep.util;

import static net.obvj.junit.utils.matchers.InstantiationNotAllowedMatcher.instantiationNotAllowed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * Unit tests for the {@link EncryptionUtils} class.
 *
 * @author oswaldo.bapvic.jr
 */
public class EncryptionUtilsTest
{
    /**
     * Tests that no instances of this utility class are created
     */
    @Test
    public void testNoInstancesAllowed()
    {
        assertThat(EncryptionUtils.class, instantiationNotAllowed());
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
