package net.obvj.jep;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.nfunk.jep.JEP;
import org.nfunk.jep.OperatorSet;
import org.nfunk.jep.function.Comparative;
import org.nfunk.jep.function.PostfixMathCommand;
import org.nfunk.jep.type.NumberFactory;

import net.obvj.jep.functions.*;
import net.obvj.jep.functions.BinaryBooleanFunction.Strategy;
import net.obvj.jep.functions.DateFieldGetter.DateField;
import net.obvj.jep.functions.UnaryEncryptionFunction.EncryptionAlgorithm;

/**
 * A factory that creates JEP Contexts with extended functions.
 *
 * @author oswaldo.bapvic.jr
 */
public class JEPContextFactory
{
    private static final String MSG_ANNOTATION_NOT_FOUND_IN_CLASS = "@Function annotation not found in class: %s";
    private static final String MSG_ANNOTATION_NOT_FOUND_IN_STRATEGY = "@Function annotation not found in strategy enum type: %s";

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

        jep.setAllowAssignment(true);
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
        addAnnotatedFunction(jep, new UnaryStringFunction(UnaryStringFunction.Strategy.CAMEL));
        addAnnotatedFunction(jep, new Concat());
        addAnnotatedFunction(jep, new BinaryBooleanFunction(Strategy.STRING_ENDS_WITH));
        addAnnotatedFunction(jep, new BinaryStringFunction(BinaryStringFunction.Strategy.FIRST_MATCH));
        addAnnotatedFunction(jep, new BinaryStringFunction(BinaryStringFunction.Strategy.ALL_MATCHES));
        addAnnotatedFunction(jep, new FormatString());
        addAnnotatedFunction(jep, new StringPaddingFunction(StringPaddingFunction.Strategy.LEFT_PAD));
        addAnnotatedFunction(jep, new UnaryStringFunction(UnaryStringFunction.Strategy.LOWER));
        addAnnotatedFunction(jep, new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_MATCHES));
        addAnnotatedFunction(jep, new NormalizeString());
        addAnnotatedFunction(jep, new UnaryStringFunction(UnaryStringFunction.Strategy.PROPER));
        addAnnotatedFunction(jep, new Replace(Replace.Strategy.NORMAL));
        addAnnotatedFunction(jep, new Replace(Replace.Strategy.REGEX));
        addAnnotatedFunction(jep, new StringPaddingFunction(StringPaddingFunction.Strategy.RIGHT_PAD));
        addAnnotatedFunction(jep, new BinaryStringFunction(BinaryStringFunction.Strategy.SPLIT));
        addAnnotatedFunction(jep, new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_STARTS_WITH));
        addAnnotatedFunction(jep, new UnaryStringFunction(UnaryStringFunction.Strategy.TRIM));
        addAnnotatedFunction(jep, new UnaryStringFunction(UnaryStringFunction.Strategy.UPPER));

        // Date functions
        addAnnotatedFunction(jep, new Now());
        addAnnotatedFunction(jep, new DateToString());
        addAnnotatedFunction(jep, new StringToDate());
        addAnnotatedFunction(jep, new DaysBetween());
        addAnnotatedFunction(jep, new EndOfMonth());
        addAnnotatedFunction(jep, new IsLeapYear());
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.YEAR));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.QUARTER));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.MONTH));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.ISO_WEEK_NUMBER));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.WEEK_DAY));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.DAY));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.HOUR));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.MINUTE));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.SECOND));
        addAnnotatedFunction(jep, new DateFieldGetter(DateField.MILLISECOND));

        // Data manipulation functions
        addAnnotatedFunction(jep, new XPath());
        addAnnotatedFunction(jep, new JsonPath());

        // Statistical functions
        addAnnotatedFunction(jep, new Average());
        addAnnotatedFunction(jep, new Count());
        addAnnotatedFunction(jep, new Max());
        addAnnotatedFunction(jep, new Min());

        // Random functions
        addAnnotatedFunction(jep, new UUID());

        // Utility functions
        addAnnotatedFunction(jep, new UnarySystemFunction(UnarySystemFunction.Strategy.GET_SYSTEM_PROPERTY));
        addAnnotatedFunction(jep, new UnarySystemFunction(UnarySystemFunction.Strategy.GET_ENV));
        addAnnotatedFunction(jep, new IsEmpty());
        addAnnotatedFunction(jep, new ReadFile());
        addAnnotatedFunction(jep, new TypeOf());

        // Cryptography functions
        addAnnotatedFunction(jep, new UnaryEncryptionFunction(EncryptionAlgorithm.MD5));
        addAnnotatedFunction(jep, new UnaryEncryptionFunction(EncryptionAlgorithm.SHA1));
        addAnnotatedFunction(jep, new UnaryEncryptionFunction(EncryptionAlgorithm.SHA256));

        // Web Services functions
        addAnnotatedFunction(jep, new HttpGet());
        addAnnotatedFunction(jep, new Http());
        addAnnotatedFunction(jep, new HttpResponseHandler(HttpResponseHandler.Strategy.GET_STATUS_CODE));
        addAnnotatedFunction(jep, new HttpResponseHandler(HttpResponseHandler.Strategy.GET_RESPONSE));

        // Math functions
        addAnnotatedFunction(jep, new Arabic());
        addAnnotatedFunction(jep, new Roman());

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
        addAnnotatedFunction(jep, elementCommand);
        operators.getElement().setPFMC(elementCommand);
    }

    /**
     * Add a custom function, annotated with @Function, to the given JEP context.
     * 
     * @param jep      the JEP object where functions will be registered
     * @param function the function to be registered
     * @throws IllegalStateException if the given function is not annotated
     */
    protected static void addAnnotatedFunction(JEP jep, PostfixMathCommand function)
    {
        Optional<Function> annotation = getAnnotation(function);
        if (!annotation.isPresent())
        {
            throw new IllegalStateException(String.format(MSG_ANNOTATION_NOT_FOUND_IN_CLASS, function));
        }
        Arrays.stream(annotation.get().value()).forEach(alias -> jep.addFunction(alias, function));
    }

    private static Optional<Function> getAnnotation(PostfixMathCommand function)
    {
        return function instanceof MultiStrategyCommand ? getAnnotation((MultiStrategyCommand) function)
                : Optional.ofNullable(function.getClass().getAnnotation(Function.class));
    }

    private static Optional<Function> getAnnotation(MultiStrategyCommand function)
    {
        Object strategy = function.getStrategy();

        // Get all types annotated with @Function inside the enumeration
        List<Field> annotations = FieldUtils.getFieldsListWithAnnotation(strategy.getClass(), Function.class);

        // Get the field for the strategy in particular
        Field strategyField = annotations.stream().filter(field -> field.getName().equals(strategy.toString()))
                .findAny().orElseThrow(() -> new IllegalStateException(
                        String.format(MSG_ANNOTATION_NOT_FOUND_IN_STRATEGY, strategy.toString())));

        // Finally, get the @Function annotation
        return Optional.ofNullable(strategyField.getAnnotation(Function.class));
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
            jep.getSymbolTable().putAll(contextMap);
        }
    }
}
