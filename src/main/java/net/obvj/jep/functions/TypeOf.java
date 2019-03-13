package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * A function that returns the Java type associated with the given parameter
 *
 * @author oswaldo.bapvic.jr
 */
public class TypeOf extends PostfixMathCommand
{
    /**
     * Builds this custom command with a fixed number of 1 parameter
     */
    public TypeOf()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object parameter = stack.pop();
        String result = parameter == null ? "null" : parameter.getClass().getName();
        stack.push(result);
    }

}
