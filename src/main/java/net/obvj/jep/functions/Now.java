package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

/**
 * This class implements a function command that returns system's current date and time.
 *
 * @author oswaldo.bapvic.jr
 */
public class Now extends PostfixMathCommand
{
    /**
     * Builds this function without parameters
     */
    public Now()
    {
        numberOfParameters = 0;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack pStack) throws ParseException
    {
        pStack.push(new Date());
    }

}
