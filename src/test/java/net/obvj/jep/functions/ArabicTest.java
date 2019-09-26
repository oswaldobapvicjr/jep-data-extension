package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Arabic} class.
 * 
 * @author oswaldo.bapvic.jr
 */
public class ArabicTest
{
    private Arabic function = new Arabic();

    @Test
    public void testRomanToArabic() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack("MCMXCIX");
        function.run(parameters);
        assertEquals(1999, parameters.pop());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWithANumberInsteadOfString() throws ParseException
    {
        function.run(CollectionsUtils.newParametersStack(1999.0));
    }

}
