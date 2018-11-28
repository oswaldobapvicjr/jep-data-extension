package net.obvj.jep;

import org.nfunk.jep.JEP;
import org.nfunk.jep.type.NumberFactory;

import net.obvj.jep.functions.Concat;
import net.obvj.jep.functions.Count;
import net.obvj.jep.functions.Now;

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
        context.addFunction("concat", new Concat());
        context.addFunction("count", new Count());
        context.addFunction("now", new Now());

        return context;
    }
}
