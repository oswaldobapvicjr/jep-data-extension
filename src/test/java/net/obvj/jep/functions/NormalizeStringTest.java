package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.StackUtils;

/**
 * Unit tests for the {@link NormalizeString} function
 *
 * @author oswaldo.bapvic.jr
 */
public class NormalizeStringTest
{
    private static final String STRING_UNICODE_PORTUGUESE_CHARACTERS = "àêíõüç";
    private static final String STRING_ASCII_PORTUGUESE = "aeiouc";

    private static NormalizeString function = new NormalizeString();

    /**
     * Tests characters replacement for common Portuguese diacritics
     *
     * @throws ParseException
     */
    @Test
    public void testNormalizePortugueseCharacters() throws ParseException
    {
        Stack<Object> parameters = StackUtils.newParametersStack(STRING_UNICODE_PORTUGUESE_CHARACTERS);
        function.run(parameters);
        assertEquals(STRING_ASCII_PORTUGUESE, parameters.pop());
    }

}
