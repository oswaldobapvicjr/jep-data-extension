package net.obvj.jep.functions;

import java.util.List;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * A command that returns elements from Java Lists and JSONArrays
 *
 * @author oswaldo.bapvic.jr
 */
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
    public void run(Stack pStack) throws ParseException
    {
        checkStack(pStack);
        Object rightSide = pStack.pop();
        Object leftSide = pStack.pop();

        int index = getIndexParameter(rightSide);
        pStack.push(getElement(leftSide, index));
    }

    /**
     * Extracts the index parameter from JEP's evaluation vector
     *
     * @param pObject the object containing the index parameter for the element operation
     * @return the index to be searched in the left side of the operator
     */
    protected int getIndexParameter(Object pObject)
    {
        if (pObject instanceof List<?>)
        {
            List<?> list = (List<?>) pObject;
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
        return NumberUtils.parseInt(pObject);
    }

    private Object getElement(Object pLeftSide, int pIndex)
    {
        List<?> list = CollectionsUtils.asList(pLeftSide);
        return list.get(pIndex);
    }

}
