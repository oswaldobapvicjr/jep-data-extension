package net.obvj.jep.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlUtilsTest
{
    private static final String STRING_XML_BOOKS = FileUtils.readQuietlyFromClasspath("books.xml");

    private static final String XPATH_ALL_BOOK_TITLES = "/bookstore/book/title/text()";
    private static final String XPATH_ALL_WEB_BOOK_TITLES = "/bookstore/book[@category='web']/title/text()";
    private static final String XPATH_CHEAP_WEB_BOOK_TITLES = "/bookstore/book[@category='web'][price<40]/title/text()";
    private static final String XPATH_FREE_BOOK_TITLES = "/bookstore/book[price=0]/title/text()";
    private static final String XPATH_ALL_BOOK_CATEGORIES = "/bookstore/book/@category";

    private static final String XPATH_ALL_BOOK_TITLES_INVALID = "/bookstore/book/title/text(";

    private static final List<String> ALL_BOOKS = Arrays.asList("Everyday Italian", "Harry Potter", "XQuery Kick Start", "Learning XML");
    private static final List<String> ALL_WEB_BOOKS = Arrays.asList("XQuery Kick Start", "Learning XML");
    private static final List<String> ALL_BOOK_CATEGORIES = Arrays.asList("cooking", "children", "web");

    /**
     * Tests that no instances of this utility class are created
     *
     * @throws Exception in case of error getting constructor metadata or instantiating the
     * private constructor via Reflection
     */
    @Test
    public void testNoInstancesAllowed() throws Exception
    {
        UtilitiesCommons.testNoInstancesAllowed(XmlUtils.class, IllegalStateException.class, "Utility class");
    }

    @Test
    public void testConvertStringToXmlDocument() throws ParserConfigurationException, SAXException, IOException
    {
        Document xml = XmlUtils.convertToXML(STRING_XML_BOOKS);
        assertEquals("bookstore", xml.getDocumentElement().getNodeName());
    }

    @Test
    public void testCompileXPathWithValidExpression() throws XPathExpressionException
    {
        XPathExpression compiledXPath = XmlUtils.compileXPath(XPATH_ALL_BOOK_TITLES);
        assertNotNull(compiledXPath);
    }

    @Test(expected = XPathExpressionException.class)
    public void testCompileXPathWithInValidExpression() throws XPathExpressionException
    {
        XPathExpression compiledXPath = XmlUtils.compileXPath(XPATH_ALL_BOOK_TITLES_INVALID);
        assertNotNull(compiledXPath);
    }

    @Test
    public void testEvaluateXPathExpressionGetAllBookTitlesAsList() throws Exception
    {
        List<Object> result = XmlUtils.evaluateXPathAsObjectList(STRING_XML_BOOKS, XPATH_ALL_BOOK_TITLES);
        assertEquals(ALL_BOOKS.size(), result.size());
        assertTrue(result.containsAll(ALL_BOOKS));
    }

    @Test
    public void testEvaluateXPathExpressionGetWebBookTitlesAsList() throws Exception
    {
        List<Object> result = XmlUtils.evaluateXPathAsObjectList(STRING_XML_BOOKS, XPATH_ALL_WEB_BOOK_TITLES);
        assertEquals(ALL_WEB_BOOKS.size(), result.size());
        assertTrue(result.containsAll(ALL_WEB_BOOKS));
    }

    @Test
    public void testEvaluateXPathExpressionGetWebBookTitlesSingleResultAsList() throws Exception
    {
        List<Object> result = XmlUtils.evaluateXPathAsObjectList(STRING_XML_BOOKS, XPATH_CHEAP_WEB_BOOK_TITLES);
        assertEquals(1, result.size());
        assertTrue(result.contains("Learning XML"));
    }

    @Test
    public void testEvaluateXPathExpressionGetAllBookCategories() throws Exception
    {
        List<Object> result = XmlUtils.evaluateXPathAsObjectList(STRING_XML_BOOKS, XPATH_ALL_BOOK_CATEGORIES);
        assertTrue(result.containsAll(ALL_BOOK_CATEGORIES));
    }

    @Test
    public void testEvaluateXPathExpressionNoMatch() throws Exception
    {
        List<Object> result = XmlUtils.evaluateXPathAsObjectList(STRING_XML_BOOKS, XPATH_FREE_BOOK_TITLES);
        assertEquals(0, result.size());
    }
}
