package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;

/**
 * A command that formats strings based on a pattern and variable arguments
 *
 * @author oswaldo.bapvic.jr
 */
public class FormatString extends PostfixMathCommand
{
    /**
     * Builds this function with unlimited number of parameters
     */
    public FormatString()
    {
        numberOfParameters = -1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        if (stack.isEmpty()) throw new ParseException("No parameter received");

        Object[] varArgs = CollectionsUtils.getVarArgsExceptFirst(stack);
        String format = stack.pop().toString();

        stack.push(String.format(format, varArgs));
    }

}
