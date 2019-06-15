package net.obvj.jep.functions;

/**
 * A common interface for objects that implement multiple strategies
 *
 * @author oswaldo.bapvic.jr
 */
public interface MultiStrategyCommand
{
    /**
     * @return the strategy assigned to a command instance, for testing purposes
     */
    Object getStrategy();
}
