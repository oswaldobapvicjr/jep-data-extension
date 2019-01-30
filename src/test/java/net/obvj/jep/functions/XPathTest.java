package net.obvj.jep.functions;

import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nfunk.jep.ParseException;

import net.obvj.jep.util.CollectionsUtils;
import net.obvj.jep.util.TextFileReader;

public class XPathTest
{
    private static final String STR_XML_BOOKS = TextFileReader.readFile("books.xml");

    private static final String XPATH_ALL_BOOK_TITLES = "/bookstore/book/title/text()";

    private static final String STR_EMPTY = StringUtils.EMPTY;
    private static final String STR_TEST = "test";

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

}
