package net.obvj.jep.functions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.FileUtils;

public class XPathTest
{
    private static final String STR_XML_BOOKS = FileUtils.readQuietlyFromClasspath("books.xml");

    private static final String XPATH_ALL_BOOK_TITLES = "/bookstore/book/title/text()";
    private static final String XPATH_ALL_BOOK_TITLES_NOT_COMPILABLE = "/bookstore/book/title/text(";

    private static final String STR_EMPTY = StringUtils.EMPTY;
    private static final String STR_TEST = "test";

    private static final List<String> ALL_BOOKS = Arrays.asList("Everyday Italian", "Harry Potter", "XQuery Kick Start", "Learning XML");

    private static XPath function = new XPath();

    @Test(expected = IllegalArgumentException.class)
    public void testWithEmptyXMString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_EMPTY, XPATH_ALL_BOOK_TITLES);
        function.run(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullXML() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(null, XPATH_ALL_BOOK_TITLES);
        function.run(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidXMLString() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_TEST, XPATH_ALL_BOOK_TITLES);
        function.run(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidXPath() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_XML_BOOKS, XPATH_ALL_BOOK_TITLES_NOT_COMPILABLE);
        function.run(parameters);
    }

    @Test
    public void testWithValidXPathReturningAllBookTitles() throws ParseException
    {
        Stack<Object> parameters = CollectionsUtils.newParametersStack(STR_XML_BOOKS, XPATH_ALL_BOOK_TITLES);
        function.run(parameters);
        List<Object> result = CollectionsUtils.asList(parameters.pop());
        assertEquals(ALL_BOOKS.size(), result.size());
        assertTrue("Expected output for XPath was not returned", result.containsAll(ALL_BOOKS));
    }
}
