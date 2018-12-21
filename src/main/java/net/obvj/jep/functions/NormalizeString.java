package net.obvj.jep.functions;

import java.text.Normalizer;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function that normalizes Unicode strings, replacing accents
 * and other diacritics with ASCII characters.
 *
 * @author oswaldo.bapvic.jr
 */
public class NormalizeString extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of one parameter
     */
    public NormalizeString()
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
        Object argument = stack.pop();

        if (argument == null || StringUtils.isEmpty(argument.toString()))
        {
            stack.push(argument);
            return;
        }

        stack.push(normalizeString(argument.toString()));
    }

    /**
     * Normalizes a Unicode string, replacing accents and other diacritics with ASCII
     * characters.
     *
     * @param string the string to be normalized
     * @return a normalized string
     */
    public static String normalizeString(String string)
    {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        return string;
    }
}
