package net.obvj.jep;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

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

    private static final List<Supplier<PostfixMathCommandI>> CUSTOM_FUNCTIONS = new ArrayList<>();
    static
    {
        // String functions
        CUSTOM_FUNCTIONS.add(Concat::new);
        CUSTOM_FUNCTIONS.add(FormatString::new);
        CUSTOM_FUNCTIONS.add(NormalizeString::new);
        CUSTOM_FUNCTIONS.add(() -> new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_ENDS_WITH));
        CUSTOM_FUNCTIONS.add(() -> new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_MATCHES));
        CUSTOM_FUNCTIONS.add(() -> new BinaryBooleanFunction(BinaryBooleanFunction.Strategy.STRING_STARTS_WITH));
        CUSTOM_FUNCTIONS.add(() -> new BinaryStringFunction(BinaryStringFunction.Strategy.ALL_MATCHES));
        CUSTOM_FUNCTIONS.add(() -> new BinaryStringFunction(BinaryStringFunction.Strategy.FIRST_MATCH));
        CUSTOM_FUNCTIONS.add(() -> new BinaryStringFunction(BinaryStringFunction.Strategy.SPLIT));
        CUSTOM_FUNCTIONS.add(() -> new Replace(Replace.Strategy.NORMAL));
        CUSTOM_FUNCTIONS.add(() -> new Replace(Replace.Strategy.REGEX));
        CUSTOM_FUNCTIONS.add(() -> new StringPaddingFunction(StringPaddingFunction.Strategy.LEFT_PAD));
        CUSTOM_FUNCTIONS.add(() -> new StringPaddingFunction(StringPaddingFunction.Strategy.RIGHT_PAD));
        CUSTOM_FUNCTIONS.add(() -> new UnaryStringFunction(UnaryStringFunction.Strategy.CAMEL));
        CUSTOM_FUNCTIONS.add(() -> new UnaryStringFunction(UnaryStringFunction.Strategy.LOWER));
        CUSTOM_FUNCTIONS.add(() -> new UnaryStringFunction(UnaryStringFunction.Strategy.PROPER));
        CUSTOM_FUNCTIONS.add(() -> new UnaryStringFunction(UnaryStringFunction.Strategy.TRIM));
        CUSTOM_FUNCTIONS.add(() -> new UnaryStringFunction(UnaryStringFunction.Strategy.UPPER));

        // Date functions
        CUSTOM_FUNCTIONS.add(DateToString::new);
        CUSTOM_FUNCTIONS.add(DaysBetween::new);
        CUSTOM_FUNCTIONS.add(EndOfMonth::new);
        CUSTOM_FUNCTIONS.add(IsLeapYear::new);
        CUSTOM_FUNCTIONS.add(Now::new);
        CUSTOM_FUNCTIONS.add(StringToDate::new);
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.YEAR));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.QUARTER));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.MONTH));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.ISO_WEEK_NUMBER));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.WEEK_DAY));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.DAY));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.HOUR));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.MINUTE));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.SECOND));
        CUSTOM_FUNCTIONS.add(() -> new DateFieldGetter(DateField.MILLISECOND));
        CUSTOM_FUNCTIONS.add(() -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_YEARS));
        CUSTOM_FUNCTIONS.add(() -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_MONTHS));
        CUSTOM_FUNCTIONS.add(() -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_DAYS));
        CUSTOM_FUNCTIONS.add(() -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_HOURS));
        CUSTOM_FUNCTIONS.add(() -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_MINUTES));
        CUSTOM_FUNCTIONS.add(() -> new BinaryDateFunction(BinaryDateFunction.Strategy.ADD_SECONDS));

        // Data manipulation functions
        CUSTOM_FUNCTIONS.add(JsonPath::new);
        CUSTOM_FUNCTIONS.add(XPath::new);

        // Statistical functions
        CUSTOM_FUNCTIONS.add(Average::new);
        CUSTOM_FUNCTIONS.add(Count::new);
        CUSTOM_FUNCTIONS.add(Max::new);
        CUSTOM_FUNCTIONS.add(Min::new);

        // Random functions
        CUSTOM_FUNCTIONS.add(UUID::new);

        // Utility functions
        CUSTOM_FUNCTIONS.add(Distinct::new);
        CUSTOM_FUNCTIONS.add(IsEmpty::new);
        CUSTOM_FUNCTIONS.add(ReadFile::new);
        CUSTOM_FUNCTIONS.add(TypeOf::new);
        CUSTOM_FUNCTIONS.add(() -> new UnaryBooleanFunction(UnaryBooleanFunction.Strategy.IS_DECIMAL));
        CUSTOM_FUNCTIONS.add(() -> new UnaryBooleanFunction(UnaryBooleanFunction.Strategy.IS_INTEGER));
        CUSTOM_FUNCTIONS.add(() -> new UnarySystemFunction(UnarySystemFunction.Strategy.GET_ENV));
        CUSTOM_FUNCTIONS.add(() -> new UnarySystemFunction(UnarySystemFunction.Strategy.GET_SYSTEM_PROPERTY));

        // Cryptography functions
        CUSTOM_FUNCTIONS.add(() -> new UnaryEncryptionFunction(EncryptionAlgorithm.MD5));
        CUSTOM_FUNCTIONS.add(() -> new UnaryEncryptionFunction(EncryptionAlgorithm.SHA1));
        CUSTOM_FUNCTIONS.add(() -> new UnaryEncryptionFunction(EncryptionAlgorithm.SHA256));
        CUSTOM_FUNCTIONS.add(() -> new UnaryEncryptionFunction(EncryptionAlgorithm.TO_BASE64));
        CUSTOM_FUNCTIONS.add(() -> new UnaryEncryptionFunction(EncryptionAlgorithm.FROM_BASE64));

        // Web Services functions
        CUSTOM_FUNCTIONS.add(BasicAuthorizationHeader::new);
        CUSTOM_FUNCTIONS.add(Http::new);
        CUSTOM_FUNCTIONS.add(HttpGet::new);
        CUSTOM_FUNCTIONS.add(HttpHeader::new);
        CUSTOM_FUNCTIONS.add(() -> new HttpResponseHandler(HttpResponseHandler.Strategy.GET_RESPONSE));
        CUSTOM_FUNCTIONS.add(() -> new HttpResponseHandler(HttpResponseHandler.Strategy.GET_STATUS_CODE));

        // Math functions
        CUSTOM_FUNCTIONS.add(Arabic::new);
        CUSTOM_FUNCTIONS.add(Roman::new);
    }

    private JEPContextFactory()
    {
        throw new IllegalStateException("No instances allowed");
    }

    /**
     * Creates a new {@link JEP} object with all custom functions and operators available.
     *
     * @return a {@code JEP} object
     */
    public static JEP newContext()
    {
        return newContext(null, false, true, false, null);
    }

    /**
     * Creates a new {@link JEP} object with all custom functions and operators available, and
     * a collection of initial variables.
     *
     * @param contextMap a map of variables to be used by the evaluation context; can be null
     * @return a {@code JEP} object with a collection of initial variables available
     */
    public static JEP newContext(Map<String, Object> contextMap)
    {
        return newContext(contextMap, false, true, false, null);
    }

    /**
     * Creates a new {@link JEP} object with all custom functions and operators available, a
     * collection of initial variables, and custom parameters.
     *
     * @param contextMap             a map of variables to be used by the evaluation context;
     *                               can be null
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
     * Registers custom functions and operators into a preset JEP object.
     *
     * @param jep the JEP object to which custom functions and operations will be registered
     */
    public static void addCustomFunctions(JEP jep)
    {
        CUSTOM_FUNCTIONS.stream().map(Supplier::get).forEach(function -> addAnnotatedFunction(jep, function));

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
