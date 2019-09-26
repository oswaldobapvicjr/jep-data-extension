package net.obvj.jep.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for the {@link RomanNumeralUtils} class.
 * 
 * @author oswaldo.bapvic.jr
 */
public class RomanNumeralUtilsTest
{

    @Test
    public void testRomanToArabic()
    {
        assertEquals(0, RomanNumeralUtils.romanToArabic(""));
        assertEquals(1, RomanNumeralUtils.romanToArabic("I"));
        assertEquals(23, RomanNumeralUtils.romanToArabic("XXIII"));
        assertEquals(87, RomanNumeralUtils.romanToArabic("LXXXVII"));
        assertEquals(1999, RomanNumeralUtils.romanToArabic("MCMXCIX"));
        assertEquals(3999, RomanNumeralUtils.romanToArabic("MMMCMXCIX"));
    }

    @Test
    public void testArabicToRoman()
    {
        assertEquals("", RomanNumeralUtils.arabicToRoman(0));
        assertEquals("I", RomanNumeralUtils.arabicToRoman(1));
        assertEquals("XXIII", RomanNumeralUtils.arabicToRoman(23));
        assertEquals("LXXXVII", RomanNumeralUtils.arabicToRoman(87));
        assertEquals("MCMXCIX", RomanNumeralUtils.arabicToRoman(1999));
        assertEquals("MMMCMXCIX", RomanNumeralUtils.arabicToRoman(3999));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRomanToArabicInvalid()
    {
        RomanNumeralUtils.romanToArabic("MIM");   
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testToArabicToRomanInvalid()
    {
        RomanNumeralUtils.arabicToRoman(-1);
        RomanNumeralUtils.arabicToRoman(4000);
    }

}
