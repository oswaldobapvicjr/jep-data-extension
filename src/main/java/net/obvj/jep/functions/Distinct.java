package net.obvj.jep.functions;

import java.util.List;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;

/**
 * This class implements a function that returns a list consisting of distinct elements of
 * a given collection. The function supports arrays, JSONArrays and Iterables.
 *
 * @author oswaldo.bapvic.jr
 */
@Function("distinct")
public class Distinct extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of parameters
     */
    public Distinct()
    {
        numberOfParameters = 1;
    }

    /**
     * @see org.nfunk.jep.function.PostfixMathCommand#run(java.util.Stack)
     */
    @Override
    public void run(Stack pStack) throws ParseException
    {
        checkStack(pStack);
        Object param = pStack.pop();
        List<Object> list = CollectionsUtils.asList(param);
        List<Object> distinctList = CollectionsUtils.distinctList(list);
        pStack.push(distinctList);
    }
}
