package net.obvj.jep.util;

import static net.obvj.junit.utils.matchers.InstantiationNotAllowedMatcher.instantiationNotAllowed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
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
     * @throws ReflectiveOperationException in case of errors getting constructor metadata or
     *                                      instantiating the private constructor
     */
    @Test
    public void testNoInstancesAllowed() throws ReflectiveOperationException
    {
        assertThat(XmlUtils.class, instantiationNotAllowed());
    }

    @Test
    public void testConvertStringToXmlDocument() throws ParserConfigurationException, SAXException, IOException
    {
        Document xml = XmlUtils.convertToXML(STRING_XML_BOOKS);
        assertEquals("bookstore", xml.getDocumentElement().getNodeName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertInvalidObjectToXmlDocument() throws ParserConfigurationException, SAXException, IOException
    {
        XmlUtils.convertToXML(1);
    }

    @Test
    public void testConvertDocumentToXmlDocument() throws ParserConfigurationException, SAXException, IOException
    {
        Document xml1 = XmlUtils.convertToXML(STRING_XML_BOOKS);
        Document xml2 = XmlUtils.convertToXML(xml1);
        assertEquals(xml1, xml2);
    }

    @Test
    public void testCompileXPathWithValidExpression() throws XPathExpressionException
    {
        assertNotNull(XmlUtils.compileXPath(XPATH_ALL_BOOK_TITLES));
    }

    @Test(expected = XPathExpressionException.class)
    public void testCompileXPathWithInValidExpression() throws XPathExpressionException
    {
        XmlUtils.compileXPath(XPATH_ALL_BOOK_TITLES_INVALID);
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
