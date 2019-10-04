package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function command that concatenates elements.
 *
 * @author oswaldo.bapvic.jr
 */
@Function({"concat", "join"})
public class Concat extends PostfixMathCommand
{
    /**
     * Builds this function with any number of parameters
     */
    public Concat()
    {
        numberOfParameters = -1;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);

        // Extracts all objects from the stack, limited to the curNumberOfParameters
        Object[] arguments = new Object[curNumberOfParameters];
        for (int i = curNumberOfParameters - 1; i >= 0; i--)
        {
            arguments[i] = String.valueOf(stack.pop());
        }

        stack.push(StringUtils.join(arguments));
    }

}
