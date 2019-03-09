package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.functions.Replace.SearchStrategy;
import net.obvj.jep.util.CollectionsUtils;

/**
 * Unit tests for the {@link Replace} function
 *
 * @author oswaldo.bapvic.jr
 */
public class ReplaceTest
{
    private static final String FEE_BEE = "fee-bee";
    private static final String FOO_BOO = "foo-boo";
    private static final String F_B = "f-b";
    private static final String EE = "ee";
    private static final String OO = "oo";
    
    private static final String FILE1_JSON = "file1.json";
    private static final String FILE1_XML = "file1.xml";
    private static final String FILE1 = "file1";
    private static final String REGEX_EXTRACT_FILE_EXTENSION = "(\\.\\w+$)";
    
    private static Replace replaceNormal = new Replace(SearchStrategy.NORMAL);
    private static Replace replaceRegex = new Replace(SearchStrategy.REGEX);

    /**
     * Tests the replacement function with a valid string and two replacements
     */
    @Test
    public void testReplaceNormalWithTwoReplacements() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FEE_BEE, EE, OO);
        replaceNormal.run(parameters);
        assertEquals(FOO_BOO, parameters.pop());
    }

    /**
     * Tests the replacement function with a null string
     */
    @Test
    public void testReplaceNormalWithNullString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, EE, OO);
        replaceNormal.run(parameters);
        assertNull(parameters.pop());
    }

    /**
     * Tests the replacement function with an empty string
     */
    @Test
    public void testReplaceNormalWithEmptyString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(StringUtils.EMPTY, EE, OO);
        replaceNormal.run(parameters);
        assertEquals(StringUtils.EMPTY, parameters.pop());
    }

    /**
     * Tests the replacement function with a null search criteria
     */
    @Test
    public void testReplaceNormalWithNullSearchCriteria() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FEE_BEE, null, OO);
        replaceNormal.run(parameters);
        assertEquals(FEE_BEE, parameters.pop());
    }

    /**
     * Tests the replacement function with an empty search criteria
     */
    @Test
    public void testReplaceNormalWithEmptySearchCriteria() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FEE_BEE, StringUtils.EMPTY, OO);
        replaceNormal.run(parameters);
        assertEquals(FEE_BEE, parameters.pop());
    }

    /**
     * Tests the replacement function with an null replacement
     */
    @Test
    public void testReplaceNormalWithNullReplacement() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FEE_BEE, EE, null);
        replaceNormal.run(parameters);
        assertEquals(F_B, parameters.pop());
    }

    /**
     * Tests the replacement function with an empty replacement
     */
    @Test
    public void testReplaceNormalWithEmptyReplacement() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FEE_BEE, EE, StringUtils.EMPTY);
        replaceNormal.run(parameters);
        assertEquals(F_B, parameters.pop());
    }
    
    /**
     * Tests the replacement regex function with a valid string and a replacement
     */
    @Test
    public void testReplaceRegexNormalWithReplacement() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_EXTRACT_FILE_EXTENSION, ".xml");
        replaceRegex.run(parameters);
        assertEquals(FILE1_XML, parameters.pop());
    }

    /**
     * Tests the replacement regex function with an null replacement
     */
    @Test
    public void testReplaceRegexWithNullReplacement() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_EXTRACT_FILE_EXTENSION, null);
        replaceRegex.run(parameters);
        assertEquals(FILE1, parameters.pop());
    }

    /**
     * Tests the replacement regex function with an empty replacement
     */
    @Test
    public void testReplaceRegexWithEmptyReplacement() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(FILE1_JSON, REGEX_EXTRACT_FILE_EXTENSION, StringUtils.EMPTY);
        replaceRegex.run(parameters);
        assertEquals(FILE1, parameters.pop());
    }
}
