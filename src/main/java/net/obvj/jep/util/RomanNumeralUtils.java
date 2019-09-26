package net.obvj.jep.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that contains methods for conversions between Roman and Arabic numerals.
 * 
 * @author oswaldo.bapvic.jr
 */
public class RomanNumeralUtils
{
    private static final List<RomanNumeral> REVERSE_SORTED_ROMAN_SYMBOLS = RomanNumeral.getReverseSortedValues();

    public enum RomanNumeral
    {
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100), CD(400), D(500), CM(900), M(1000);

        private final int value;

        private RomanNumeral(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

        /**
         * @return a List containing all Roman Numerals sorted by value in reverse order
         */
        public static List<RomanNumeral> getReverseSortedValues()
        {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral numeral) -> numeral.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Converts a Roman number to an Arabic number.
     * 
     * @param input the Roman number to be converted
     * @return the Arabic number corresponding to the given inout
     */
    public static int romanToArabic(String input)
    {
        if (input.isEmpty()) return 0;

        String romanNumeral = input.toUpperCase();
        int result = 0;
        int i = 0;
        while (!romanNumeral.isEmpty() && i < REVERSE_SORTED_ROMAN_SYMBOLS.size())
        {
            RomanNumeral currentSymbol = REVERSE_SORTED_ROMAN_SYMBOLS.get(i);
            if (romanNumeral.startsWith(currentSymbol.name()))
            {
                result += currentSymbol.value;
                romanNumeral = romanNumeral.substring(currentSymbol.name().length());
            }
            else
            {
                i++;
            }
        }

        if (!romanNumeral.isEmpty())
        {
            throw new IllegalArgumentException(input + " is not a valid Roman numeral");
        }

        return result;
    }

    /**
     * Converts an Arabic number to a Roman number.
     * 
     * @param number the Arabic number to be converted (a value from 1 to 3999)
     * @return a String containing the corresponding Roman number. An input of 0 (zero) will
     *         be converted to an empty string.
     * @throws IllegalArgumentException if the given input is not in range (0,3999)
     */
    public static String arabicToRoman(int number)
    {
        if (number < 0 || number > 3999) throw new IllegalArgumentException(number + " is not in range (0, 3999)");

        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (number > 0 && i < REVERSE_SORTED_ROMAN_SYMBOLS.size())
        {
            RomanNumeral currentSymbol = REVERSE_SORTED_ROMAN_SYMBOLS.get(i);
            if (currentSymbol.value <= number)
            {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            }
            else
            {
                i++;
            }
        }

        return sb.toString();
    }

}
