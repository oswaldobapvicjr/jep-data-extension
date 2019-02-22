package net.obvj.jep.functions;

import java.util.Date;
import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.Comparative;

import net.obvj.jep.util.DateUtils;
import net.obvj.jep.util.NumberUtils;

/**
 * An extension of JEP's Comparative command that can also handle strings and dates.
 *
 * @author oswaldo.bapvic.jr
 */
public class DateAwareComparative extends Comparative
{
    private static final Double DOUBLE_FALSE = Double.valueOf(0);
    private static final Double DOUBLE_TRUE = Double.valueOf(1);

    /**
     * Builds this Comparative command
     *
     * @param pComparativeId the identifier of the comparative operator
     */
    public DateAwareComparative(int pComparativeId)
    {
        super(pComparativeId);
    }

    /**
     * @see org.nfunk.jep.function.Comparative#run(java.util.Stack)
     */
    @Override
    public void run(Stack pStack) throws ParseException
    {
        checkStack(pStack);
        Object parameter2 = pStack.pop();
        Object parameter1 = pStack.pop();

        try
        {
            Date date1 = DateUtils.parseDate(parameter1);
            Date date2 = DateUtils.parseDate(parameter2);

            boolean result = compareDates(date1, date2);
            pStack.push(result ? DOUBLE_TRUE : DOUBLE_FALSE);
        }
        catch (IllegalArgumentException exception)
        {
            // OK. It's not a Date. If it's a String, convert it to Double and let the super
            // class run default logic.
            pStack.push(parameter1 instanceof String ? NumberUtils.parseDouble(parameter1) : parameter1);
            pStack.push(parameter2 instanceof String ? NumberUtils.parseDouble(parameter2) : parameter2);
            super.run(pStack);
        }
    }

    private boolean compareDates(Date date1, Date date2)
    {
        switch (id)
        {
        case LT:
            return date1.before(date2);
        case GT:
            return date1.after(date2);
        case LE:
            return date1.before(date2) || date1.equals(date2);
        case GE:
            return date1.after(date2) || date1.equals(date2);
        case NE:
            return !date1.equals(date2);
        case EQ:
            return date1.equals(date2);
        default:
            return false;
        }
    }
}
