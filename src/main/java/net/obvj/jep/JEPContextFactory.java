package net.obvj.jep;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.nfunk.jep.JEP;
import org.nfunk.jep.OperatorSet;
import org.nfunk.jep.function.Comparative;
import org.nfunk.jep.function.PostfixMathCommand;
import org.nfunk.jep.function.PostfixMathCommandI;
import org.nfunk.jep.type.NumberFactory;

import net.obvj.jep.functions.*;
import net.obvj.jep.functions.DateFieldGetter.DateField;
import net.obvj.jep.functions.UUID;
import net.obvj.jep.functions.UnaryEncryptionFunction.EncryptionAlgorithm;

/**
 * A factory that creates JEP objects with extended functions and operators.
 *
 * @author oswaldo.bapvic.jr
 */
public class JEPContextFactory
{
    private static final String MSG_ANNOTATION_NOT_FOUND_IN_CLASS = "@Function annotation not found in class: %s";
    private static final String MSG_ANNOTATION_NOT_FOUND_IN_STRATEGY = "@Function annotation not found in strategy enum type: %s";

    private static final Map<NamedPackage, List<Supplier<PostfixMathCommandI>>> FUNCTION_FACTORY_BY_PACKAGE = new EnumMap<>(NamedPackage.class);

    static
    {
        // String functions
        registerFunction(NamedPackage.STRING, Concat::new);
        registerFunction(NamedPackage.STRING, FormatString::new);
        registerFunction(NamedPackage.STRING, NormalizeString::new);
        registerFunction(NamedPackage.STRING, () -> new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_ENDS_WITH));
        registerFunction(NamedPackage.STRING, () -> new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_MATCHES));
        registerFunction(NamedPackage.STRING, () -> new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_STARTS_WITH));
        registerFunction(NamedPackage.STRING, () -> new BinaryStringFunction(BinaryStringFunction.Strategy.ALL_MATCHES));
        registerFunction(NamedPackage.STRING, () -> new BinaryStringFunction(BinaryStringFunction.Strategy.FIRST_MATCH));
        registerFunction(NamedPackage.STRING, () -> new BinaryStringFunction(BinaryStringFunction.Strategy.SPLIT));
        registerFunction(NamedPackage.STRING, () -> new Replace(Replace.Strategy.NORMAL));
        registerFunction(NamedPackage.STRING, () -> new Replace(Replace.Strategy.REGEX));
        registerFunction(NamedPackage.STRING, () -> new StringPaddingFunction(StringPaddingFunction.Strategy.LEFT_PAD));
        registerFunction(NamedPackage.STRING, () -> new StringPaddingFunction(StringPaddingFunction.Strategy.RIGHT_PAD));
        registerFunction(NamedPackage.STRING, () -> new UnaryStringFunction(UnaryStringFunction.Strategy.CAMEL));
        registerFunction(NamedPackage.STRING, () -> new UnaryStringFunction(UnaryStringFunction.Strategy.LOWER));
        registerFunction(NamedPackage.STRING, () -> new UnaryStringFunction(UnaryStringFunction.Strategy.PROPER));
        registerFunction(NamedPackage.STRING, () -> new UnaryStringFunction(UnaryStringFunction.Strategy.TRIM));
        registerFunction(NamedPackage.STRING, () -> new UnaryStringFunction(UnaryStringFunction.Strategy.UPPER));

        // Date functions
        registerFunction(NamedPackage.DATE, DateToString::new);
        registerFunction(NamedPackage.DATE, DaysBetween::new);
        registerFunction(NamedPackage.DATE, EndOfMonth::new);
        registerFunction(NamedPackage.DATE, IsLeapYear::new);
        registerFunction(NamedPackage.DATE, Now::new);
        registerFunction(NamedPackage.DATE, StringToDate::new);
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.YEAR));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.QUARTER));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.MONTH));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.ISO_WEEK_NUMBER));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.WEEK_DAY));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.DAY));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.HOUR));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.MINUTE));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.SECOND));
        registerFunction(NamedPackage.DATE, () -> new DateFieldGetter(DateField.MILLISECOND));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_YEARS));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_QUARTERS));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_MONTHS));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_WEEKS));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_DAYS));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_HOURS));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_MINUTES));
        registerFunction(NamedPackage.DATE, () -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_SECONDS));

        // Data manipulation functions
        registerFunction(NamedPackage.DATA_MANIPULATION, JsonPath::new);
        registerFunction(NamedPackage.DATA_MANIPULATION, XPath::new);

        // Statistical functions
        registerFunction(NamedPackage.STATISTICS, Average::new);
        registerFunction(NamedPackage.STATISTICS, Count::new);
        registerFunction(NamedPackage.STATISTICS, Max::new);
        registerFunction(NamedPackage.STATISTICS, Min::new);

        // Random functions
        registerFunction(NamedPackage.RANDOM, UUID::new);

        // Utility functions
        registerFunction(NamedPackage.UTIL, Distinct::new);
        registerFunction(NamedPackage.UTIL, IsEmpty::new);
        registerFunction(NamedPackage.UTIL, ReadFile::new);
        registerFunction(NamedPackage.UTIL, TypeOf::new);
        registerFunction(NamedPackage.UTIL, () -> new UnaryBooleanFunction(UnaryBooleanFunction.Strategy.IS_DECIMAL));
        registerFunction(NamedPackage.UTIL, () -> new UnaryBooleanFunction(UnaryBooleanFunction.Strategy.IS_INTEGER));
        registerFunction(NamedPackage.UTIL, () -> new UnarySystemFunction(UnarySystemFunction.Strategy.GET_ENV));
        registerFunction(NamedPackage.UTIL, () -> new UnarySystemFunction(UnarySystemFunction.Strategy.GET_SYSTEM_PROPERTY));

        // Cryptography functions
        registerFunction(NamedPackage.CRYPTO, () -> new UnaryEncryptionFunction(EncryptionAlgorithm.SHA256));
        registerFunction(NamedPackage.CRYPTO, () -> new UnaryEncryptionFunction(EncryptionAlgorithm.TO_BASE64));
        registerFunction(NamedPackage.CRYPTO, () -> new UnaryEncryptionFunction(EncryptionAlgorithm.FROM_BASE64));

        // Web Services functions
        registerFunction(NamedPackage.WEB, BasicAuthorizationHeader::new);
        registerFunction(NamedPackage.WEB, Http::new);
        registerFunction(NamedPackage.WEB, HttpGet::new);
        registerFunction(NamedPackage.WEB, HttpHeader::new);
        registerFunction(NamedPackage.WEB, () -> new HttpResponseHandler(HttpResponseHandler.Strategy.GET_RESPONSE));
        registerFunction(NamedPackage.WEB, () -> new HttpResponseHandler(HttpResponseHandler.Strategy.GET_STATUS_CODE));

        // Math functions
        registerFunction(NamedPackage.MATH, Arabic::new);
        registerFunction(NamedPackage.MATH, Roman::new);
    }

    private static void registerFunction(NamedPackage namedPackage, Supplier<PostfixMathCommandI> supplier)
    {
        FUNCTION_FACTORY_BY_PACKAGE.computeIfAbsent(namedPackage, k -> new ArrayList<>()).add(supplier);
    }

    private JEPContextFactory()
    {
        throw new IllegalStateException("No instances allowed");
    }

    /**
     * Creates a new {@link JEP} object with custom functions and operators available.
     * <p>
     * For <strong>all functions available</strong>, simply pass no argument:
     * </p>
     *
     * <pre>
     * JEP jep = JEPContextFactory.newContext();
     * </pre>
     *
     * <p>
     * For the <strong>standard JEP functions</strong> only, specify the core package as
     * parameter:
     * </p>
     *
     * <pre>
     * JEP jep = JEPContextFactory.newContext(NamedPackage.CORE);
     * </pre>
     *
     * <p>
     * For specific custom packages (in addition to the standard JEP functions), specify one
     * or more custom package(s) as parameter:
     * </p>
     *
     * <pre>
     * JEP jep = JEPContextFactory.newContext(NamedPackage.STRING, NamedPackage.WEB);
     * </pre>
     *
     * @param namedPackages one or more packages which functions shall be added to the JEP
     *                      context; if not specified, then all functions will be available
     * @return a {@code JEP} object
     */
    public static JEP newContext(NamedPackage...namedPackages)
    {
        return newContext(null, false, true, false, null, namedPackages);
    }

    /**
     * Creates a new {@link JEP} object with custom functions and operators available, and a
     * collection of initial variables.
     *
     *
     * @param contextMap    a map of variables to be used by the evaluation context; can be
     *                      null
     * @param namedPackages one or more packages which functions shall be added to the JEP
     *                      context; if not specified, then all functions will be available
     * @return a {@code JEP} object with a collection of initial variables available
     *
     * @see JEPContextFactory#newContext(NamedPackage...)
     */
    public static JEP newContext(Map<String, Object> contextMap, NamedPackage...namedPackages)
    {
        return newContext(contextMap, false, true, false, null, namedPackages);
    }

    /**
     * Creates a new {@link JEP} object with custom functions and operators available, a
     * collection of initial variables, and custom parameters.
     *
     * @param contextMap             a map of variables to be used by the evaluation context;
     *                               can be null
     * @param traverse               an optional to print expression trees (useful for debug)
     * @param allowUndeclared        the "allow undeclared variables" option
     * @param implicitMultiplication the "implicit multiplication" option
     * @param numberFactory          the number factory to be used
     * @param namedPackages          one or more packages which functions shall be added to
     *                               the JEP context; if not specified, then all functions
     *                               will be available
     *
     * @return a new {@code JEP} object with custom functions and operators registered
     *
     * @see JEPContextFactory#newContext(NamedPackage...)
     */
    public static JEP newContext(Map<String, Object> contextMap, boolean traverse, boolean allowUndeclared,
            boolean implicitMultiplication, NumberFactory numberFactory, NamedPackage...namedPackages)
    {
        JEP jep = new JEP(traverse, allowUndeclared, implicitMultiplication, numberFactory);

        jep.setAllowAssignment(true);
        jep.addStandardFunctions();
        addCustomFunctions(jep, namedPackages);

        if (contextMap != null) addVariables(jep, contextMap);

        return jep;
    }

    /**
     * Registers custom functions and operators into a given JEP object.
     * <p>
     * For <strong>all custom functions available</strong>, simply pass no package as
     * argument:
     * </p>
     *
     * <pre>
     * JEPContextFactory.addCustomFunctions(jep);
     * </pre>
     *
     * <p>
     * For specific custom packages, specify one or more custom package(s) as parameter:
     * </p>
     *
     * <pre>
     * JEPContextFactory.addCustomFunctions(jep, NamedPackage.STRING, NamedPackage.WEB);
     * </pre>
     *
     * <p>
     * Although it is assumed that standard JEP functions are already available in the
     * specified {@link JEP} object, this method performs no action or validation on these
     * features.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> This method updates JEP comparative operators regardless of the
     * specified package. In other words, the comparative operators will always be enhanced
     * with {@code Date} object capabilities after this method call.
     * </p>
     *
     * @param jep           the JEP object to which custom functions and operations will be
     *                      registered
     * @param namedPackages one or more packages which functions shall be added to the JEP
     *                      context; if not specified, then all functions will be available
     */
    public static void addCustomFunctions(JEP jep, NamedPackage...namedPackages)
    {
        getFunctions(namedPackages).map(Supplier::get).forEach(function -> addAnnotatedFunction(jep, function));

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
     * @return a {@link Stream} of suppliers for instantiation of all custom functions
     * @since 1.0.5
     */
    private static Stream<Supplier<PostfixMathCommandI>> getAllFunctions()
    {
        return FUNCTION_FACTORY_BY_PACKAGE.values().stream().flatMap(List::stream);
    }

    /**
     * @param namedPackages one or more packages which functions shall be returned; if not
     *                      specified, then all functions will be available
     * @return a {@link Stream} of suppliers for instantiation of custom functions
     * @since 1.0.5
     */
    private static Stream<Supplier<PostfixMathCommandI>> getFunctions(NamedPackage...namedPackages)
    {
        if (namedPackages == null || namedPackages.length == 0)
        {
            return getAllFunctions();
        }
        if (namedPackages.length == 1)
        {
            return FUNCTION_FACTORY_BY_PACKAGE.getOrDefault(namedPackages[0], Collections.emptyList()).stream();
        }
        // Get values from multiple keys from the map
        return Arrays.stream(namedPackages).map(FUNCTION_FACTORY_BY_PACKAGE::get).filter(Objects::nonNull)
                .flatMap(List::stream);
    }

    /**
     * Add a custom function, annotated with @Function, to the given JEP context.
     *
     * @param jep      the JEP object to which the function will be registered
     * @param function the function to be registered
     *
     * @throws IllegalArgumentException if the given function is not annotated
     */
    protected static void addAnnotatedFunction(JEP jep, PostfixMathCommandI function)
    {
        Optional<Function> annotation = getAnnotation(function);
        if (!annotation.isPresent())
        {
            throw new IllegalStateException(String.format(MSG_ANNOTATION_NOT_FOUND_IN_CLASS, function));
        }
        Arrays.stream(annotation.get().value()).forEach(alias -> jep.addFunction(alias, function));
    }

    private static Optional<Function> getAnnotation(PostfixMathCommandI function)
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
     * Adds all entries from the {@code contextMap} as variables to the JEP context.
     *
     * @param jep        the JEP object to be filled with variables, not null
     * @param contextMap a map of variables to be used by the evaluation context
     *
     * @throws IllegalArgumentException if the specified JEP object is null
     */
    public static void addVariables(JEP jep, Map<String, Object> contextMap)
    {
        if (jep == null)
        {
            throw new IllegalArgumentException("A null JEP object was received");
        }

        if (contextMap != null)
        {
            jep.getSymbolTable().putAll(contextMap);
        }
    }
}
