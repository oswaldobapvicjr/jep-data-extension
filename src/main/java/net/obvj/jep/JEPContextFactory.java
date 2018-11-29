package net.obvj.jep;

import org.nfunk.jep.JEP;
import org.nfunk.jep.type.NumberFactory;

import net.obvj.jep.functions.*;

/**
 * A factory that creates JEP Contexts with extended functions.
 *
 * @author oswaldo.bapvic.jr
 */
public class JEPContextFactory
{
    private JEPContextFactory()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @return a new {@code JEP} object with custom functions and operators registered and
     *         default, preferred behavior
     */
    public static JEP newContext()
    {
        return newContext(false, true, false, null);
    }

    /**
     * @param traverse               an optional to print expression trees (useful for debug)
     * @param allowUndeclared        the "allow undeclared variables" option
     * @param implicitMultiplication the "implicit multiplication" option
     * @param numberFactory          the number factory to be used
     *
     * @return a new {@code JEP} object with custom functions and operators registered
     */
    public static JEP newContext(boolean traverse, boolean allowUndeclared, boolean implicitMultiplication,
            NumberFactory numberFactory)
    {
        JEP context = new JEP(traverse, allowUndeclared, implicitMultiplication, numberFactory);

        context.addStandardFunctions();

        // String functions
        context.addFunction("concat", new Concat());
        context.addFunction("lower", new Lower());
        context.addFunction("trim", new Trim());
        context.addFunction("upper", new Upper());

        // Date functions
        context.addFunction("now", new Now());

        // Statistical functions
        context.addFunction("count", new Count());

        return context;
    }
}
