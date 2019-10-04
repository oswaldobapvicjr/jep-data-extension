package net.obvj.jep.functions;

import java.util.Stack;

import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a JEP function that produces a UUID.
 *
 * @author oswaldo.bapvic.jr
 */
@Function("uuid")
public class UUID extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of one parameter
     */
    public UUID()
    {
        numberOfParameters = 0;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack)
    {
        stack.push(java.util.UUID.randomUUID().toString());
    }

}
