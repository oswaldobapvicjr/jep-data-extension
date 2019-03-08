package net.obvj.jep;

import java.util.Map;
import java.util.Map.Entry;

import org.nfunk.jep.JEP;
import org.nfunk.jep.OperatorSet;
import org.nfunk.jep.function.Comparative;
import org.nfunk.jep.function.PostfixMathCommand;
import org.nfunk.jep.type.NumberFactory;

import net.obvj.jep.functions.*;
import net.obvj.jep.functions.FindMatches.ReturnStrategy;

/**
 * A factory that creates JEP Contexts with extended functions.
 *
 * @author oswaldo.bapvic.jr
 */
public class JEPContextFactory
{
    private JEPContextFactory()
    {
        throw new IllegalStateException("No instances allowed");
    }

    /**
     * @return a new {@code JEP} object with custom functions and operators registered and
     *         default behavior
     */
    public static JEP newContext()
    {
        return newContext(null, false, true, false, null);
    }

    /**
     * @param contextMap a map of variables to be used by the evaluation context
     * @return a new {@code JEP} object with initial variables in the context and custom
     *         functions and operators registered and default behavior
     */
    public static JEP newContext(Map<String, Object> contextMap)
    {
        return newContext(contextMap, false, true, false, null);
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
        return newContext(null, traverse, allowUndeclared, implicitMultiplication, numberFactory);
    }

    /**
     * @param contextMap             a map of variables to be used by the evaluation context
     * @param traverse               an optional to print expression trees (useful for debug)
     * @param allowUndeclared        the "allow undeclared variables" option
     * @param implicitMultiplication the "implicit multiplication" option
     * @param numberFactory          the number factory to be used
     *
     * @return a new {@code JEP} object with custom functions and operators registered
     */
    public static JEP newContext(Map<String, Object> contextMap, boolean traverse, boolean allowUndeclared,
            boolean implicitMultiplication, NumberFactory numberFactory)
    {
        JEP jep = new JEP(traverse, allowUndeclared, implicitMultiplication, numberFactory);

        jep.addStandardFunctions();
        addCustomFunctions(jep);

        if (contextMap != null) addVariables(jep, contextMap);

        return jep;
    }

    /**
     * Registers custom functions and operators into JEP
     *
     * @param jep the JEP object where functions will be registered
     */
    public static void addCustomFunctions(JEP jep)
    {
        // String functions
        jep.addFunction("concat", new Concat());
        jep.addFunction("findMatch", new FindMatches(ReturnStrategy.FIRST_MATCH));
        jep.addFunction("findMatches", new FindMatches(ReturnStrategy.ALL_MATCHES));
        jep.addFunction("lower", new Lower());
        jep.addFunction("normalizeString", new NormalizeString());
        jep.addFunction("replace", new Replace());
        jep.addFunction("trim", new Trim());
        jep.addFunction("upper", new Upper());

        // Date functions
        jep.addFunction("now", new Now());
        jep.addFunction("date2str", new DateToString());
        jep.addFunction("str2date", new StringToDate());
        jep.addFunction("daysBetween", new DaysBetween());
        jep.addFunction("isLeapYear", new IsLeapYear());

        // Data manipulation functions
        jep.addFunction("xpath", new XPath());
        jep.addFunction("jsonpath", new JsonPath());

        // Statistical functions
        jep.addFunction("count", new Count());

        // Random functions
        jep.addFunction("uuid", new UUID());

        // Utility functions
        jep.addFunction("getSystemProperty", new SystemPropertyReader());
        jep.addFunction("getEnv", new EnvironmentVariableReader());
        jep.addFunction("isEmpty", new IsEmpty());

        // Operators
        OperatorSet operators = jep.getOperatorSet();
        operators.getLT().setPFMC(new DateAwareComparative(Comparative.LT));
        operators.getGT().setPFMC(new DateAwareComparative(Comparative.GT));
        operators.getLE().setPFMC(new DateAwareComparative(Comparative.LE));
        operators.getGE().setPFMC(new DateAwareComparative(Comparative.GE));
        operators.getNE().setPFMC(new DateAwareComparative(Comparative.NE));
        operators.getEQ().setPFMC(new DateAwareComparative(Comparative.EQ));

        // Element function and operator
        PostfixMathCommand elementCommand = new Element();
        jep.addFunction("getElement", elementCommand);
        operators.getElement().setPFMC(elementCommand);
    }

    /**
     * Adds all entries from the {@code contextMap} as variables in JEP context
     *
     * @param jep        the JEP object to be filled with variables
     * @param contextMap a map of variables to be used by the evaluation context
     */
    public static void addVariables(JEP jep, Map<String, Object> contextMap)
    {
        if (jep == null) throw new IllegalArgumentException("A null JEP object was received");

        if (contextMap != null)
        {
            for (Entry<String, Object> entry : contextMap.entrySet())
            {
                jep.addVariable(entry.getKey(), entry.getValue());
            }
        }
    }
}
