package net.obvj.jep.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility methods for working with encryptions.
 *
 * @author oswaldo.bapvic.jr
 */
public class EncryptionUtils
{
    private EncryptionUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converts a byte array into a hexadecimal lower case string.
     * 
     * @param array the bytes to convert
     * @return a string with the bytes converted into hexadecimal
     */
    private static String toHexadecimalString(byte[] array)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i)
        {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    /**
     * Computes the hash for the given string with a given algorithm and transforms the binary
     * result into a hexadecimal lower case string.
     *
     * @param algorithm the name of the algorithm requested
     * @param content   a string input to encrypt
     * @return the MD5 hash transformed into a hexadecimal lower case string.
     */
    protected static String hashWith(String algorithm, String content)
    {
        try
        {
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance(algorithm);
            byte[] digest = messageDigest.digest(content.getBytes("UTF-8"));
            return toHexadecimalString(digest);
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            throw new IllegalStateException(e);
        }
    }
 
    /**
     * Computes the MD5 hash for the given string and transforms the binary result into a
     * hexadecimal lower case string.
     *
     * @param content a string input to encrypt
     * @return the MD5 hash transformed into a hexadecimal lower case string.
     */
    public static String md5(String content)
    {
        return hashWith("MD5", content);
    }

    /**
     * Computes the SHA1 hash for the given string and transforms the binary result into a
     * hexadecimal lower case string.
     *
     * @param content a string input to encrypt
     * @return the SHA1 hash transformed into a hexadecimal lower case string.
     */
    public static String sha1(String content)
    {
        return hashWith("SHA1", content);
    }

}
