package net.obvj.jep.functions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Roman} class.
 * 
 * @author oswaldo.bapvic.jr
 */
public class RomanTest
{
    private Roman function = new Roman();

    @Test
    public void testArabicToRoman() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(1999.0);
        function.run(parameters);
        assertEquals("MCMXCIX", parameters.pop());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidParameter() throws ParseException
    {
        function.run(CollectionsUtils.newParametersStack("L"));
    }

}
