package net.obvj.jep.functions;

import java.util.Stack;

/**
 * Common methods for unit test of JEP functions
 * 
 * @author oswaldo.bapvic.jr
 */
public class JepFunctionTest
{
    /**
     * Produces a Stack to be processed by JEP functions.
     * 
     * @param parameters the parameters to populate the stack
     * @return a Stack with the given parameters.
     */
    public Stack<Object> newParametersStack(Object... parameters)
    {
        Stack<Object> stack = new Stack<>();
        for (Object param : parameters)
        {
            stack.push(param);
        }
        return stack;
    }
}
