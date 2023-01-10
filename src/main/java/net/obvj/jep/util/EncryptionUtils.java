package net.obvj.jep.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility methods for working with encryption.
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
    private static String bytesToHex(byte[] array)
    {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < array.length; ++i)
        {
            hexString.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return hexString.toString();
    }

    /**
     * Computes the hash for the given string with a given algorithm and transforms the binary
     * result into a hexadecimal lower case string.
     *
     * @param algorithm the name of the algorithm requested
     * @param content   a string input to encrypt
     * @return the MD5 hash transformed into a hexadecimal lower case string.
     */
    public static String hashWith(String algorithm, String content)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] encodedHash = messageDigest.digest(content.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Computes the SHA-256 hash for the given string and transforms the binary result into a
     * hexadecimal lower case string.
     *
     * @param content a string input to encrypt
     * @return the SHA-256 hash transformed into a hexadecimal lower case string.
     */
    public static String sha256(String content)
    {
        return hashWith("SHA-256", content);
    }

    /**
     * Encodes the specified String using the Base64 encoding scheme.
     *
     * @param content the String to be encoded
     * @return the Base64 encoded string
     */
    public static String toBase64(String content)
    {
        return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes a Base64 encoded string.
     *
     * @param content a String encoded using the Base64 encoding scheme
     * @return the decoded string
     */
    public static String fromBase64(String content)
    {
        return new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8);
    }

}
