package net.obvj.jep.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function command that concatenates elements.
 *
 * @author oswaldo.bapvic.jr
 */
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

        List<Object> arguments = new ArrayList<>();
        while (!stack.isEmpty())
        {
            arguments.add(stack.pop());
        }
        Collections.reverse(arguments);

        stack.push(StringUtils.join(arguments, null));
    }

}
