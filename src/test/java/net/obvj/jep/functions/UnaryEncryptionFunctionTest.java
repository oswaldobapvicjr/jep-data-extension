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
    private static UnaryEncryptionFunction sha256 = new UnaryEncryptionFunction(EncryptionAlgorithm.SHA256);
    private static UnaryEncryptionFunction toBase64 = new UnaryEncryptionFunction(EncryptionAlgorithm.TO_BASE64);
    private static UnaryEncryptionFunction fromBase64 = new UnaryEncryptionFunction(EncryptionAlgorithm.FROM_BASE64);

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
    
    /**
     * Tests the toBase64 function with a valid string
     */
    @Test
    public void testToBase64WithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("myMessage");
        toBase64.run(parameters);
        assertEquals("bXlNZXNzYWdl", parameters.pop());
    }
    
    /**
     * Tests the fromBase64 function with a valid string
     */
    @Test
    public void testFromBase64WithValidString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("bXlNZXNzYWdl");
        fromBase64.run(parameters);
        assertEquals("myMessage", parameters.pop());
    }
}
