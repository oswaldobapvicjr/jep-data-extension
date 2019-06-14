package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;

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
        String[] arguments = CollectionsUtils.getStringVarArgs(stack);
        stack.push(StringUtils.join(arguments));
    }

}
