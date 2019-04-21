package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.UnaryEncryptionFunction.EncryptionAlgorithm;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link UnaryEncryptionFunction} class
 *
 * @author oswaldo.bapvic.jr
 */
public class UnaryEncryptionFunctionTest
{
    private static UnaryEncryptionFunction md5 = new UnaryEncryptionFunction(EncryptionAlgorithm.MD5);
    private static UnaryEncryptionFunction sha1 = new UnaryEncryptionFunction(EncryptionAlgorithm.SHA1);
    private static UnaryEncryptionFunction sha256 = new UnaryEncryptionFunction(EncryptionAlgorithm.SHA256);

    /**
     * Tests the MD5 function with a valid string
     */
    @Test
    public void testMd5WithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("asd");
        md5.run(parameters);
        assertEquals("7815696ecbf1c96e6894b779456d330e", parameters.pop());
    }

    /**
     * Tests the SHA1 function with a valid string
     */
    @Test
    public void testSha1WithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("dsasd");
        sha1.run(parameters);
        assertEquals("2fa183839c954e6366c206367c9be5864e4f4a65", parameters.pop());
    }
    
    /**
     * Tests the SHA256 function with a valid string
     */
    @Test
    public void testSha256WithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("hello");
        sha256.run(parameters);
        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", parameters.pop());
    }
}
