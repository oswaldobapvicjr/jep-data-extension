package net.obvj.jep.functions;

import java.util.List;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * This class implements a function that returns the average of collection of elements.
 * The function supports arrays, JSONArrays and Iterables containing Numbers.
 *
 * @author oswaldo.bapvic.jr
 */
public class Average extends PostfixMathCommand
{
    /**
     * Builds this function with a fixed number of parameters
     */
    public Average()
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
        pStack.push(average(list));
    }

    private Object average(List<?> pList)
    {
        return pList.stream().mapToDouble(NumberUtils::parseDouble).average().orElse(Double.NaN);
    }

}
