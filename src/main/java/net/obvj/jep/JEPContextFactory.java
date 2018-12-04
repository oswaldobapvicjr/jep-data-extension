package net.obvj.jep;

import org.nfunk.jep.JEP;
import org.nfunk.jep.OperatorSet;
import org.nfunk.jep.function.Comparative;
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
        context.addFunction("replace", new Replace());
        context.addFunction("trim", new Trim());
        context.addFunction("upper", new Upper());

        // Date functions
        context.addFunction("now", new Now());

        // Data manipulation functions
        context.addFunction("jsonpath", new JsonPath());

        // Statistical functions
        context.addFunction("count", new Count());

        // Random functions
        context.addFunction("uuid", new UUID());

        // Utility functions
        context.addFunction("getSystemProperty", new SystemPropertyReader());
        context.addFunction("getEnv", new EnvironmentVariableReader());

        // Operators
        OperatorSet operators = context.getOperatorSet();
        operators.getLT().setPFMC(new DateAwareComparative(Comparative.LT));
        operators.getGT().setPFMC(new DateAwareComparative(Comparative.GT));
        operators.getLE().setPFMC(new DateAwareComparative(Comparative.LE));
        operators.getGE().setPFMC(new DateAwareComparative(Comparative.GE));
        operators.getNE().setPFMC(new DateAwareComparative(Comparative.NE));
        operators.getEQ().setPFMC(new DateAwareComparative(Comparative.EQ));

        return context;
    }
}
