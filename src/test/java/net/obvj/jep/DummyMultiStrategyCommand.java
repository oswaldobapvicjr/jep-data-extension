package net.obvj.jep;

import org.nfunk.jep.function.PostfixMathCommand;

import net.obvj.jep.functions.MultiStrategyCommand;

/**
 * A dummy {@link MultiStrategyCommand} that defines no @Function annotation, for testing
 * purposes.
 */
public class DummyMultiStrategyCommand extends PostfixMathCommand implements MultiStrategyCommand
{
    public enum Strategy
    {
        TYPE1, TYPE2;
    }

    private Strategy strategy;

    public DummyMultiStrategyCommand(Strategy strategy)
    {
        this.strategy = strategy;
    }

    @Override
    public Object getStrategy()
    {
        return strategy;
    }
}
