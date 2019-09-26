package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.NumberUtils;
import net.obvj.jep.util.RomanNumeralUtils;

/**
 * A function command that converts an Arabic numeral to Roman.
 *
 * @author oswaldo.bapvic.jr
 */
public class Roman extends PostfixMathCommand
{
    /**
     * Builds this function without parameters
     */
    public Roman()
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
        Object param = stack.pop();
        int arabicNumeral = NumberUtils.parseInt(param);
        String romanNumeral = RomanNumeralUtils.arabicToRoman(arabicNumeral);
        stack.push(romanNumeral);
    }

}
