package net.obvj.jep.functions;

import java.util.List;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * A command that returns elements from Java Lists and JSONArrays given the index. The
 * first element is referenced with the index 1
 *
 * @author oswaldo.bapvic.jr
 */
@Function("get")
public class Element extends PostfixMathCommand
{
    /**
     * Builds this custom command with a fixed number of 2 parameters
     */
    public Element()
    {
        numberOfParameters = 2;
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack stack) throws ParseException
    {
        checkStack(stack);
        Object rightSide = stack.pop();
        Object leftSide = stack.pop();

        int index = getIndexParameter(rightSide);
        stack.push(getElement(leftSide, index));
    }

    /**
     * Extracts the index parameter from JEP's evaluation vector.
     *
     * @param object the object containing the index parameter for the element operation
     * @return the index to be searched in the left side of the operator
     */
    private int getIndexParameter(Object object)
    {
        List<?> list = CollectionsUtils.asList(object);
        if (list.isEmpty())
        {
            throw new IllegalArgumentException("No index specified");
        }
        if (list.size() > 1)
        {
            throw new IllegalArgumentException("Only single-dimension arrays/collections supported");
        }
        return NumberUtils.parseInt(list.get(0));
    }

    private Object getElement(Object leftSide, int index)
    {
        List<?> list = CollectionsUtils.asList(leftSide);
        return list.isEmpty() ? null : list.get(index - 1);
    }

}
