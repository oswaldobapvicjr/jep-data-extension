/**
 * <p>
 * Provides functions for the Java Expression Parser.
 * </p>
 * <p>
 * A function is an object that extends JEP's {@code PostfixMathCommand}. This class uses
 * a stack for passing the arguments via the {@code run()} method.
 * </p>
 * <p>
 * Each function can also receive a custom annotation {@code @Function}, which basically
 * tells the {@code JEPContextFactory} the function name and alias to be registered.
 * </p>
 *
 * @since 1.0.0
 */
package net.obvj.jep.functions;
