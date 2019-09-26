package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.RomanNumeralUtils;

/**
 * A function command that converts a Roman numeral to Arabic.
 *
 * @author oswaldo.bapvic.jr
 */
public class Arabic extends PostfixMathCommand
{
    /**
     * Builds this function without parameters
     */
    public Arabic()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        String romanNumeral = String.valueOf(stack.pop());
        int arabicNumeral = RomanNumeralUtils.romanToArabic(romanNumeral);
        stack.push(arabicNumeral);
    }

}
