package net.obvj.jep.util;

import java.util.Stack;

/**
 * Common methods for working with stacks
 *
 * @author oswaldo.bapvic.jr
 */
public class StackUtils
{
    private StackUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Produces a Stack to be processed by JEP functions.
     *
     * @param parameters the parameters to populate the stack
     * @return a Stack with the given parameters.
     */
    public static Stack<Object> newParametersStack(Object... parameters)
    {
        Stack<Object> stack = new Stack<>();
        for (Object param : parameters)
        {
            stack.push(param);
        }
        return stack;
    }
}
